package ptithcm.tttn.controller.staff;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.request.UpdateStatusRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.response.ValueResponse;
import ptithcm.tttn.service.OrderService;
import ptithcm.tttn.service.TransactionRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/staff/order")
@AllArgsConstructor
public class StaffOrderController {

    private final OrderService orderService;

    private final TransactionRequestService requestService;


    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Orders>> getAllOrderByStaff(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try{
            List<Orders> getAllOrder = orderService.findAll();
            res.setData(getAllOrder);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<EntityResponse<Orders>> cancelOrderByCustomer(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody UpdateStatusRequest od){
        EntityResponse<Orders> res = new EntityResponse<>();
        try{
           Orders orders = orderService.updateStatusOrderByStaff(od,id,jwt);
          res.setData(orders);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setData(null);
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/value")
    public ResponseEntity<ValueResponse> getValueOrder(@RequestHeader("Authorization") String jwt){
        ValueResponse res = new ValueResponse();
        try{
            List<Orders> getAllOrder = orderService.findAll();
            int total = 0;
            for(Orders od : getAllOrder){
                total+=od.getTotal_price();
            }
            res.setValue(total);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setValue(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }


    @GetMapping("/all/shipper")
    public ResponseEntity<ListEntityResponse<Orders>> getAllOrderByStaffShipper(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try{
            List<Orders> getAllOrder = orderService.allOrderReceiveByStaff(jwt);

            res.setData(getAllOrder);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    // <editor-fold desc="Transaction request">
    @PostMapping("/{id}/create/request")
    public ResponseEntity<ApiResponse> createRequestExportByOder(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        ApiResponse res = new ApiResponse();
        try {
            Transaction_request create =requestService.createRequestExportByOrder(id,jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
    // </editor-fold>
}

