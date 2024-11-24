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
        ett.setTransaction_code(generateTransactionCode());
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
            detail.setQuantity_request(item.getQuantity());
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

        // Cập nhật trạng thái và nhân viên đã cập nhật
        ett.setStatus(rq.getStatus());
        ett.setStaff_id_updated(staff.getStaff_id());
        requestRepo.save(ett);

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
    private String generateTransactionCode() {
        // Lấy năm hiện tại
        String currentYear = String.valueOf(java.time.Year.now().getValue()).substring(2); // Lấy 2 số cuối của năm

        // Lấy tất cả giao dịch
        List<Transaction> transactions = transactionRepo.findAll();
        int maxId = 0;

        // Kiểm tra danh sách giao dịch
        for (Transaction p : transactions) {
            String transactionCode = p.getTransaction_code();
            if (transactionCode.startsWith("PDN" + currentYear)) { // Kiểm tra xem có cùng năm không
                String idStr = transactionCode.substring(4); // Loại bỏ "NK" + "21"
                int id = Integer.parseInt(idStr);
                if (id > maxId) {
                    maxId = id;
                }
            }
        }

        // Nếu danh sách rỗng hoặc chưa có mã nào cùng năm, tạo mã đầu tiên
        if (maxId == 0) {
            return String.format("PDN%s%06d", currentYear, 1); // Bắt đầu từ 001
        }

        // Nếu đã có mã, tăng số thứ tự lên
        return String.format("PDN%s%06d", currentYear, maxId + 1);
    }

}
