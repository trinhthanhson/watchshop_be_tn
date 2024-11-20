package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.OrderStatus;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.OrderStatusRepo;
import ptithcm.tttn.service.OrderStatusService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepo statusRepo;
    private final UserService userService;
    private final StaffService staffService;

    @Override
    public List<OrderStatus> findAll() {
        return statusRepo.findAll();
    }

    @Override
    public OrderStatus createStatus(OrderStatus status, String jwt) throws Exception {
        OrderStatus ett = new OrderStatus();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        ett.setCreated_at(LocalDateTime.now());
        ett.setStatus_index(status.getStatus_index());
        ett.setStatus_name(status.getStatus_name());
        ett.setStaff_id(staff.getStaff_id());
        return statusRepo.save(ett);
    }

    @Override
    public OrderStatus updateStatus(Long id, OrderStatus status, String jwt) throws Exception {
        OrderStatus find = findById(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        find.setUpdated_at(LocalDateTime.now());
        find.setStatus_name(status.getStatus_name());
        find.setStatus_index(status.getStatus_index());
        find.setUpdated_by(staff.getStaff_id());
        return statusRepo.save(find);
    }

    private OrderStatus findById(Long id) throws Exception {
        Optional<OrderStatus> find = statusRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("Not found status by id " + id);
    }
}
