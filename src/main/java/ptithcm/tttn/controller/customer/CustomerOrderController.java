package ptithcm.tttn.controller.customer;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.OrderStatus;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.function.OrderStatusDF;
import ptithcm.tttn.function.TypeTrans;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.request.UpdateStatusRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.OrderService;
import ptithcm.tttn.service.OrderStatusService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/customer/order")
@AllArgsConstructor
public class CustomerOrderController {
    private final OrderService ordersService;
    private final OrderStatusService orderStatusService;

    @PostMapping("/buy-cart")
    public ResponseEntity<ApiResponse> buyCartByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody OrderRequest rq) {
        ApiResponse res = new ApiResponse();
        try {
            Orders orders = ordersService.orderBuyCart(rq, jwt);
            if (orders != null) {
                res.setStatus(HttpStatus.CREATED);
                res.setCode(HttpStatus.CREATED.value());
                res.setMessage("create order buy now success");
            } else {
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setMessage("create order buy now fail");
            }
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/buy-now")
    public ResponseEntity<ApiResponse> buyNowByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody OrderRequest rq) {
        ApiResponse res = new ApiResponse();
        try {
            Orders orders = ordersService.orderBuyNow(rq, jwt);
            if (orders != null) {
                res.setStatus(HttpStatus.CREATED);
                res.setCode(HttpStatus.CREATED.value());
                res.setMessage("create order buy now success");
            } else {
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setMessage("create order buy now fail");
            }
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/customer")
    public ResponseEntity<ListEntityResponse<Orders>> findAllOrderCustomer(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            List<Orders> orders = ordersService.findByJwtCustomer(jwt);
            res.setData(orders);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/customer/page")
    public ResponseEntity<ListEntityResponse<Orders>> getAllOrderCustomerPageHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "sortField", defaultValue = "created_at") String sortField, // Trường mặc định
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) { // Hướng mặc định

        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            Page<Orders> etts = ordersService.findByCustomerIdPage(jwt, page, limit, sortField, sortDirection);

            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent());
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            res.setTotalPages(etts.getTotalPages());
            res.setTotalElements(etts.getTotalElements());

        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/status/customer")
    public ResponseEntity<ListEntityResponse<Orders>> getAllOrderStatusCustomerPageHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "sortField", defaultValue = "created_at") String sortField, // Trường mặc định
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection, @RequestBody Orders orders) { // Hướng mặc định

        ListEntityResponse<Orders> res = new ListEntityResponse<>();

        try {
            Page<Orders> etts = ordersService.findOrderByCustomerAndStatus(jwt, orders, page, limit, sortField, sortDirection);

            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent());
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            res.setTotalPages(etts.getTotalPages());
            res.setTotalElements(etts.getTotalElements());

        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/search/date")
    public ResponseEntity<ListEntityResponse<Orders>> searchOrderCustomerByDatePageHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "sortField", defaultValue = "created_at") String sortField, // Trường mặc định
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) { // Hướng mặc định

        ListEntityResponse<Orders> res = new ListEntityResponse<>();

        try {
            Page<Orders> etts = ordersService.searchOrderByDatePage(jwt, startDate,endDate, page, limit, sortField, sortDirection);

            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent());
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            res.setTotalPages(etts.getTotalPages());
            res.setTotalElements(etts.getTotalElements());

        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse> cancelOrderByCustomer(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody UpdateStatusRequest od) {
        ApiResponse res = new ApiResponse();
        try {
            Orders orders = ordersService.updateStatus(od, id);
            res.setStatus(HttpStatus.OK);
            res.setMessage("Success");
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("Error: " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/status/all")
    public ResponseEntity<ListEntityResponse<OrderStatus>> findAllOrderStatus(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<OrderStatus> res = new ListEntityResponse<>();
        try {
            List<OrderStatus> orders = orderStatusService.findAll();
            res.setData(orders);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
}
