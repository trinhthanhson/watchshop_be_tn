package ptithcm.tttn.controller.manager;

import lombok.AllArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.OrderStatus;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.OrderStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/manager/order-status")
@AllArgsConstructor
public class ManagerOrderStatusController {

    private final OrderStatusService statusService;

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<OrderStatus>> findAllOrderStatusHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<OrderStatus> res = new ListEntityResponse<>();
        List<OrderStatus> all = statusService.findAll();
        res.setStatus(HttpStatus.OK);
        res.setData(all);
        res.setCode(HttpStatus.OK.value());
        res.setMessage(MessageSuccess.E01.getMessage());
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addOrderStatusHandle(@RequestHeader("Authorization") String jwt,@RequestBody OrderStatus rq) throws Exception {
        ApiResponse res = new ApiResponse();
        OrderStatus add = statusService.createStatus(rq,jwt);
        res.setStatus(HttpStatus.OK);
        res.setCode(HttpStatus.OK.value());
        res.setMessage(MessageSuccess.E01.getMessage());
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateOrderStatusHandle(@RequestHeader("Authorization") String jwt, @RequestBody List<OrderStatus> requestList) throws Exception {
        ApiResponse res = new ApiResponse();
        List<OrderStatus> update = statusService.updateStatus(requestList,jwt);
        res.setStatus(HttpStatus.OK);
        res.setCode(HttpStatus.OK.value());
        res.setMessage(MessageSuccess.E01.getMessage());
        return new ResponseEntity<>(res,res.getStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteOrderStatusHandle(@RequestHeader("Authorization") String jwt,@RequestBody OrderStatus rq) throws Exception {
        ApiResponse res = new ApiResponse();
        statusService.deleteStatus(rq.getStatus_id());
        res.setStatus(HttpStatus.OK);
        res.setCode(HttpStatus.OK.value());
        res.setMessage(MessageSuccess.E01.getMessage());
        return new ResponseEntity<>(res,res.getStatus());
    }


}
