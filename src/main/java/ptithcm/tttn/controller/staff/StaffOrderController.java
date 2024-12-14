package ptithcm.tttn.controller.staff;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.request.UpdateStatusRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.response.ValueResponse;
import ptithcm.tttn.service.OrderService;
import ptithcm.tttn.service.TransactionRequestService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/staff/order")
@AllArgsConstructor
public class StaffOrderController {

    private final OrderService orderService;

    private final TransactionRequestService requestService;


    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Orders>> getAllOrderByStaff(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            List<Orders> getAllOrder = orderService.findAll();
            res.setData(getAllOrder);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/page")
    public ResponseEntity<ListEntityResponse<Orders>> getAllOrderPage(@RequestHeader("Authorization") String jwt, @RequestParam("page") int page,
                                                                      @RequestParam("limit") int limit) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, limit); // Spring Pageable is 0-indexed, so subtract 1 from page
            Page<Orders> etts = orderService.findPageAll(pageable);
            res.setData(etts.getContent());
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục
        } catch (Exception e) {
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EntityResponse<Orders>> cancelOrderByCustomer(@RequestHeader("Authorization") String
                                                                                jwt, @PathVariable Long id, @RequestBody UpdateStatusRequest od) {
        EntityResponse<Orders> res = new EntityResponse<>();
        try {
            Orders orders = orderService.updateStatusOrderByStaff(od, id, jwt);
            res.setData(orders);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setData(null);
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/value")
    public ResponseEntity<ValueResponse> getValueOrder(@RequestHeader("Authorization") String jwt) {
        ValueResponse res = new ValueResponse();
        try {
            List<Orders> getAllOrder = orderService.findAll();
            int total = 0;
            for (Orders od : getAllOrder) {
                total += od.getTotal_price();
            }
            res.setValue(total);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setValue(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/all/shipper")
    public ResponseEntity<ListEntityResponse<Orders>> getAllOrderByStaffShipper
            (@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            List<Orders> getAllOrder = orderService.allOrderReceiveByStaff(jwt);

            res.setData(getAllOrder);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse> checkTransaction(@RequestParam("orderId") Long orderId) {
        String isCreated = orderService.isTransactionCreated(orderId);
        ApiResponse res = new ApiResponse();
        res.setCode(HttpStatus.OK.value());
        res.setMessage(isCreated);
        res.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(res, res.getStatus());
    }


    @PutMapping("/{id}/order-shipper")
    public ResponseEntity<ApiResponse> updateIsDeliveryShipper(@PathVariable Long
                                                                       id, @RequestHeader("Authorization") String jwt, @RequestBody UpdateStatusRequest od) throws Exception {
        Orders isCreated = orderService.updateOrderShipper(id, jwt, od);
        ApiResponse res = new ApiResponse();
        res.setCode(HttpStatus.OK.value());
        res.setMessage("success");
        res.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(res, res.getStatus());
    }

    // <editor-fold desc="Transaction request">
    @PostMapping("/{id}/create/request")
    public ResponseEntity<ApiResponse> createRequestExportByOder(@RequestHeader("Authorization") String
                                                                         jwt, @PathVariable Long id) {
        ApiResponse res = new ApiResponse();
        try {
            Transaction_request create = requestService.createRequestExportByOrder(id, jwt);
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

    // <editor-fold desc="find order by status id">
    @GetMapping("/find/status")
    public ResponseEntity<ListEntityResponse<Orders>> getOrderByStatusId(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            List<Orders> getAllOrder = orderService.allOrderReceiveByStaff(jwt);

            res.setData(getAllOrder);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
    // </editor-fold>

    @GetMapping("/search/status")
    public ResponseEntity<ListEntityResponse<Orders>> searchProductById(@RequestParam("status_id") Long status_id, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        Pageable pageable = PageRequest.of(page - 1, limit);

        try {
            Page<Orders> etts = orderService.findOrdersByStatus(status_id, pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/filter/date")
    public ResponseEntity<ListEntityResponse<Orders>> getOrdersByDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        Pageable pageable = PageRequest.of(page - 1, limit);

        try {
            Page<Orders> etts = orderService.getOrdersByDateRange(startDate, endDate,pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }


    @GetMapping("/filter/status/date")
    public ResponseEntity<ListEntityResponse<Orders>> getOrderByDateAndStatus(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,@RequestParam("status_id") Long status_id, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        Pageable pageable = PageRequest.of(page - 1, limit);

        try {
            Page<Orders> etts = orderService.getOrderByDateAndStatus(startDate, endDate,status_id,pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/filter/info")
    public ResponseEntity<ListEntityResponse<Orders>> getOrderByInfoCustomer(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                             @RequestParam("status_id") Long status_id, @RequestParam("recipient_name") String recipient_name,@RequestParam("recipient_phone") String recipient_phone,
                                                                             @RequestParam("page") int page, @RequestParam("limit") int limit) {
        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        Pageable pageable = PageRequest.of(page - 1, limit);

        try {
            Page<Orders> etts = orderService.getOrderByInfoCustomer(startDate, endDate,status_id,recipient_name,recipient_phone,pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

}

