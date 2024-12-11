package ptithcm.tttn.controller.shipper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.function.TypeTrans;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.OrderService;

@RestController
@RequestMapping("/api/shipper/order")
@RequiredArgsConstructor
public class ShipperOrderController {

    private final OrderService orderService;


    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Orders>> getAllTransactionRequestImportHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("page") int page, // Accept page number as a query parameter
            @RequestParam("limit") int limit) { // Accept limit (page size) as a query parameter

        ListEntityResponse<Orders> res = new ListEntityResponse<>();
        try {
            // Create Pageable object using the page and limit parameters
            Pageable pageable = PageRequest.of(page - 1, limit); // Spring Pageable is 0-indexed, so subtract 1 from page

            Page<Orders> etts = orderService.getAllOrderDelivery(pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục

        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

}
