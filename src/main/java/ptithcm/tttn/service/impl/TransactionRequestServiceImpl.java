package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.function.RequestStatus;
import ptithcm.tttn.function.TypeTrans;
import ptithcm.tttn.repository.*;
import ptithcm.tttn.request.ProductTransRequest;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.service.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TransactionRequestServiceImpl implements TransactionRequestService {

    private final TransactionRequestRepo requestRepo;
    private final TransactionRequestDetailRepo detailRepo;
    private final UserService userService;
    private final StaffService staffService;
    private final TypeRepo typeRepo;
    private final TransactionRepo transactionRepo;
    private final TransactionDetailRepo transactionDetailRepo;
    private final SupplierService supplierService;
    private final OrderService orderService;
    private final ProductRepo productRepo;

    @Override
    @Transactional
    public Transaction_request createRequest(TransactionRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Type type = typeRepo.findTypeByName(rq.getType_name());
        Transaction_request ett = new Transaction_request();
        ett.setContent(rq.getContent());
        ett.setCreated_at(LocalDateTime.now());
        ett.setType_id(type.getType_id());
        ett.setStaff_id_created(staff.getStaff_id());
        ett.setTotal_quantity(rq.getTotal_quantity());
        ett.setTotal_price(rq.getTotal_price());
        ett.setStatus(RequestStatus.WAITING.getStatus());
        Transaction_request save = requestRepo.save(ett);
        for (ProductTransRequest item : rq.getProducts()) {
            Request_detail detail = new Request_detail();
            detail.setRequest_id(save.getRequest_id());
            detail.setProduct_id(item.getProductId());
            detail.setPrice(item.getUnitPrice());
            detail.setQuantity_request(item.getQuantity_request());
            detail.setQuantity(item.getQuantity());
            detail.setNote(item.getNote());
            detailRepo.save(detail);
        }
        return save;
    }

    @Override
    public List<Transaction_request> findAll() {
        return requestRepo.findAll();
    }

    @Override
    public Transaction_request findById(Long id) throws Exception {
        Optional<Transaction_request> request = requestRepo.findById(id);
        if (request.isPresent()) {
            return request.get();
        }
        throw new Exception("not found request by id " + id);
    }

    @Override
    @Transactional
    public Transaction_request updateStatus(Transaction_request rq, Long id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Transaction_request ett = findById(id);
        Transaction ettTrans = new Transaction();

        // Cập nhật trạng thái và nhân viên đã cập nhật
        ett.setStatus(rq.getStatus());
        ett.setStaff_id_updated(staff.getStaff_id());
        requestRepo.save(ett);

        // Nếu trạng thái là CONFIRM, thiết lập thêm thông tin giao dịch
        if (rq.getStatus().equals(RequestStatus.CONFIRM.getStatus())) {
            ettTrans.setContent(ett.getContent());
            ettTrans.setCreated_at(LocalDateTime.now());
            ettTrans.setTotal_quantity(ett.getTotal_quantity());
            ettTrans.setTotal_price(ett.getTotal_price());
            ettTrans.setType_id(ett.getType_id());
            ettTrans.setStaff_id(staff.getStaff_id());
            ettTrans.setStatus(RequestStatus.WAITING.getStatus());

            try {
                // Lưu thông tin giao dịch
                Transaction savedTransaction = transactionRepo.save(ettTrans);
                Orders orders = orderService.findById(ett.getOrder_id());
                //orders.setStatus(OrderStatus.Shipping.getOrderStatus());
                // Duyệt qua từng Request_detail để tạo Transaction_detail
                for (Request_detail item : ett.getRequestDetails()) {
                    Transaction_detail detail = new Transaction_detail();
                    detail.setPrice(item.getPrice());
                    detail.setTransaction_id(savedTransaction.getTransaction_id());
                    detail.setQuantity(item.getQuantity());
                    detail.setProduct_id(item.getProduct_id());
                    transactionDetailRepo.save(detail);
                }
            } catch (Exception e) {
                // Xử lý lỗi nếu xảy ra khi lưu transaction hoặc chi tiết giao dịch
                throw new RuntimeException("Error occurred while saving transaction details: " + e.getMessage());
            }
        }

        return ett;
    }

    @Override
    public Transaction_request createRequestExportByOrder(Long id, String jwt) throws Exception {
        Orders orders = orderService.findById(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Type type = typeRepo.findTypeByName(TypeTrans.EXPORT.getTypeName());
        Transaction_request request = new Transaction_request();
        request.setTotal_price(orders.getTotal_price());
        request.setCreated_at(LocalDateTime.now());
        request.setStaff_id_created(staff.getStaff_id());
        request.setType_id(type.getType_id());
        request.setContent("Xuất kho");
        request.setOrder_id(orders.getOrder_id());
        request.setTotal_quantity(orders.getTotal_quantity());
        Transaction_request save = requestRepo.save(request);
        for (Order_detail item : orders.getOrderDetails()) {
            Request_detail detail = new Request_detail();
            detail.setPrice(item.getPrice());
            detail.setRequest_id(save.getRequest_id());
            detail.setQuantity(item.getQuantity());
            detail.setProduct_id(item.getProduct_id());
            detailRepo.save(detail);
        }
        return save;
    }

    @Override
    public void updateRequestDetail(Long id, List<Request_detail> rq) throws Exception {
        Transaction_request request = findById(id);
        List<Request_detail> currentDetails = detailRepo.findByRequestId(request.getRequest_id());

        for (Request_detail detail : rq) {
            Optional<Request_detail> existingDetail = currentDetails.stream()
                    .filter(d -> d.getProduct_id().equals(detail.getProduct_request().getProduct_id()))
                    .findFirst();

            if (existingDetail.isPresent()) {
                // Nếu đã tồn tại, cập nhật thông tin của Request_detail
                Request_detail currentDetail = existingDetail.get();
                currentDetail.setQuantity(detail.getQuantity());
                currentDetail.setQuantity_request(detail.getQuantity_request());
                currentDetail.setNote(detail.getNote());
                currentDetail.setPrice(detail.getPrice());
                detailRepo.save(currentDetail);
            }
            else
            {
                detail.setRequest_id(request.getRequest_id());
                detail.setProduct_id(detail.getProduct_request().getProduct_id());
                detail.setQuantity_request(detail.getQuantity_request());
                detail.setQuantity(detail.getQuantity());
                detail.setNote(detail.getNote());
                detailRepo.save(detail);
            }
        }

        for (Request_detail existingDetail : currentDetails) {
            boolean existsInNewList = rq.stream()
                    .anyMatch(d -> d.getProduct_request().getProduct_id().equals(existingDetail.getProduct_id()));

            if (!existsInNewList)
            {
                detailRepo.delete(existingDetail);
            }
        }
    }
}
