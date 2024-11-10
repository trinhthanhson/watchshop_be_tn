package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.function.RequestStatus;
import ptithcm.tttn.repository.TransactionRequestDetailRepo;
import ptithcm.tttn.repository.TransactionRequestRepo;
import ptithcm.tttn.repository.TypeRepo;
import ptithcm.tttn.request.ProductTransRequest;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.TransactionRequestService;
import ptithcm.tttn.service.UserService;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionRequestServiceImpl implements TransactionRequestService {

    private final TransactionRequestRepo requestRepo;
    private final TransactionRequestDetailRepo detailRepo;
    private final UserService userService;
    private final StaffService staffService;
    private final TypeRepo typeRepo;

    public TransactionRequestServiceImpl(TransactionRequestRepo requestRepo, TransactionRequestDetailRepo detailRepo, UserService userService, StaffService staffService, TypeRepo typeRepo) {
        this.requestRepo = requestRepo;
        this.detailRepo = detailRepo;
        this.userService = userService;
        this.staffService = staffService;
        this.typeRepo = typeRepo;
    }

    @Override
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
        if(request.isPresent()){
            return request.get();
        }
        throw new Exception("not found request by id " + id);
    }

    @Override
    @Transactional
    public Transaction_request updatestatus(Transaction_request rq, Long id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Transaction_request ett = findById(id);
        ett.setStatus(rq.getStatus());
        ett.setStaff_id_updated(staff.getStaff_id());
        return ett;
    }
}
