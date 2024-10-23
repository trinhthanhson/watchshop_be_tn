package ptithcm.tttn.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.OrderDetailService;
import ptithcm.tttn.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/customer/order")
public class CustomerOrderController {
    private final OrderService ordersService;
    private final OrderDetailService orderDetailService;

    public CustomerOrderController(OrderService ordersService, OrderDetailService orderDetailService) {
        this.ordersService = ordersService;
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/buy-cart")
    public ResponseEntity<ApiResponse> buyCartByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody OrderRequest rq){
        ApiResponse res = new ApiResponse();
        try{
            Orders orders = ordersService.orderBuyCart(rq,jwt);
            if(orders != null){
                res.setStatus(HttpStatus.CREATED);
                res.setCode(HttpStatus.CREATED.value());
                res.setMessage("create order buy now success");
            }else{
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setMessage("create order buy now fail");
            }
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/customer")
    public ResponseEntity<ListEntityResponse<Orders>> findAllOrderCustomer(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            List<Orders> orders = ordersService.findByJwtCustomer(jwt);
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
