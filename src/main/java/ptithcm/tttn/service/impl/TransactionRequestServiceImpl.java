package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.function.OrderStatus;
import ptithcm.tttn.function.RequestStatus;
import ptithcm.tttn.repository.*;
import ptithcm.tttn.request.ProductTransRequest;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.service.*;

import javax.persistence.Column;
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

    @Override
    @Transactional
    public Transaction_request createRequest(TransactionRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Type type = typeRepo.findTypeByName(rq.getType_name());
        Transaction_request ett = new Transaction_request();
        ett.setNote(rq.getNote());
        ett.setContent(rq.getContent());
        ett.setCreated_at(LocalDateTime.now());
        ett.setType_id(type.getType_id());
        ett.setStaff_id_created(staff.getStaff_id());
        ett.setTotal_quantity(rq.getTotal_quantity());
        ett.setTotal_price(rq.getTotal_price());
        ett.setStatus(RequestStatus.WAITING.getStatus());
        ett.setExpected_supplier(rq.getExpected_supplier());
        ett.setSupplier_phone(rq.getPhone());
        ett.setSupplier_email(rq.getEmail());
        ett.setSupplier_address(rq.getAddress());
        Transaction_request save = requestRepo.save(ett);
        for (ProductTransRequest item : rq.getProducts()) {
            Request_detail detail = new Request_detail();
            detail.setRequest_id(save.getRequest_id());
            detail.setProduct_id(item.getProductId());
            detail.setPrice(item.getUnitPrice());
            detail.setQuantity(item.getQuantity());
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

        if (rq.getExpected_supplier() != null && !rq.getExpected_supplier().isEmpty()) {
            // Tìm supplier theo tên
            Supplier supplier = supplierService.findByName(ett.getExpected_supplier());

            // Nếu không tìm thấy supplier, tạo mới và gán lại `supplier`
            if (supplier == null) {
                Supplier newSupplier = new Supplier();
                newSupplier.setAddress(rq.getSupplier_address());
                newSupplier.setEmail(rq.getSupplier_email());
                newSupplier.setPhone(rq.getSupplier_phone());
                newSupplier.setSupplier_name(rq.getExpected_supplier());
                supplier = supplierService.createSupplier(newSupplier, jwt); // Cập nhật `supplier` sau khi tạo mới
                ettTrans.setSupplier_id(supplier.getSupplier_id()); // `supplier` không còn null ở đây
            }
        }

        // Nếu trạng thái là CONFIRM, thiết lập thêm thông tin giao dịch
        if (rq.getStatus().equals(RequestStatus.CONFIRM.getStatus())) {
            ettTrans.setNote(ett.getNote());
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
                orders.setStatus(OrderStatus.Shipping.getOrderStatus());
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

}
