package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.OrderDetailService;
import ptithcm.tttn.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/user/order")
public class OrderController {

    private final OrderService ordersService;
    private final OrderDetailService orderDetailService;

    public OrderController(OrderService ordersService, OrderDetailService orderDetailService) {
        this.ordersService = ordersService;
        this.orderDetailService = orderDetailService;
    }

    @RequestMapping("{id}/get")
    public ResponseEntity<EntityResponse<Orders>> findOrderById(@PathVariable Long id){

        EntityResponse<Orders> res = new EntityResponse<>();
        try{
            Orders orders = ordersService.findById(id);
            res.setData(orders);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
