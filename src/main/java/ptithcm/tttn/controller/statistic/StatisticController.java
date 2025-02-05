package ptithcm.tttn.controller.statistic;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.request.StatisticRequest;
import ptithcm.tttn.response.*;
import ptithcm.tttn.service.OrderService;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.TransactionService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@AllArgsConstructor
public class StatisticController {
    private final TransactionService transactionService;
    private final ProductService productService;
    private final OrderService orderService;

    // <editor-fold desc="revenue report (báo cáo doanh thu)">
    @GetMapping("/revenue/report")
    public ResponseEntity<ListEntityResponse<RevenueReportRsp>> getRevenueReportHandle(@RequestHeader("Authorization") String jwt, @RequestParam(value = "start", required = false) String dateStart, @RequestParam(value = "end", required = false) String dateEnd) {

        ListEntityResponse<RevenueReportRsp> res = new ListEntityResponse<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;

        try {
            if (dateStart != null && !dateStart.isEmpty()) {
                start = dateFormatter.parse(dateStart);
            }
            if (dateEnd != null && !dateEnd.isEmpty()) {
                end = dateFormatter.parse(dateEnd);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {

            List<RevenueReportRsp> get = transactionService.getRevenueReport(start, end);
            res.setData(get);
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

    @GetMapping("/sales")
    public ResponseEntity<EntityResponse<List<StatisticRequest>>> getStatisticSaleOrder(@RequestHeader("Authorization") String jwt) {
        EntityResponse<List<StatisticRequest>> res = new EntityResponse<>();
        try {
            List<StatisticRequest> rq = orderService.getTotalPriceByStatus();
            res.setData(rq);
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

    // <editor-fold desc="Top 5 product sale">
    @GetMapping("/product/top")
    public ResponseEntity<ListEntityResponse<ProductSaleRequest>> getStatisticProduct(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<ProductSaleRequest> res = new ListEntityResponse<>();
        try {
            List<ProductSaleRequest> get = productService.getProductSales();
            for (ProductSaleRequest rq : get) {
                System.out.println(rq.getProduct_id());
            }
            res.setData(get);
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

    // <editor-fold desc="Report product sale">
    @GetMapping("/product/report")
    public ResponseEntity<ListEntityResponse<ProductSaleRequest>> getStatisticProductReport(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<ProductSaleRequest> res = new ListEntityResponse<>();
        try {
            List<ProductSaleRequest> get = productService.getProductSalesReport();
            for (ProductSaleRequest rq : get) {
                System.out.println(rq.getProduct_id());
            }
            res.setData(get);
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

    // <editor-fold desc="Statistic by year">
    @GetMapping("/year")
    public ResponseEntity<ListEntityResponse<StatisticRequest>> getStatisticOrder(@RequestHeader("Authorization") String jwt, @RequestParam String year) {
        int changeYear = Integer.parseInt(year);
        ListEntityResponse<StatisticRequest> res = new ListEntityResponse<>();
        try {
            List<StatisticRequest> get = orderService.getTotalAmountByMonth(changeYear);
            res.setData(get);
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

    // <editor-fold desc="Statistic by date">
    @GetMapping("/date")
    public ResponseEntity<ListEntityResponse<ProductSaleRequest>> getStatisticOrderByDate(@RequestParam("start") String dateStart, @RequestParam("end") String dateEnd, @RequestHeader("Authorization") String jwt) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        ListEntityResponse<ProductSaleRequest> res = new ListEntityResponse<>();
        try {
            // Parsing a String to Date

            start = dateFormatter.parse(dateStart);
            end = dateFormatter.parse(dateEnd);

        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        try {
            List<ProductSaleRequest> get = orderService.getTotalAmountByDate(start, end);
            res.setData(get);
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

    // <editor-fold desc="Quantity product report">
    @GetMapping("/quantity/report")
    public ResponseEntity<ListEntityResponse<QuantityInventoryRsp>> getQuantityProductReportHandle(
            @RequestHeader("Authorization") String jwt, @RequestParam(required = false) String filter,
            @RequestParam(value = "start", required = false) String dateStart, @RequestParam(value = "end", required = false) String dateEnd,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "sortField", defaultValue = "created_at") String sortField, // Trường mặc định
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
        ListEntityResponse<QuantityInventoryRsp> res = new ListEntityResponse<>();
        Date start = null;
        Date end = null;
        System.err.println(filter + dateEnd + dateStart);
        if (dateEnd != null || dateStart != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                // Parsing a String to Date
                start = dateFormatter.parse(dateStart);
                end = dateFormatter.parse(dateEnd);

            } catch (ParseException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            Page<QuantityInventoryRsp> get = productService.getQuantityProductReport(filter, start, end, page, limit, sortField, sortDirection);
            res.setData(get.getContent());
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
            res.setTotalPages(get.getTotalPages());
            res.setTotalElements(get.getTotalElements());
        } catch (Exception e) {
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setTotalPages(0);
            res.setTotalElements(0);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
    // </editor-fold>

    // <editor-fold desc="Revenue product report">
    @GetMapping("/revenue/product")
    public ResponseEntity<ListEntityResponse<RevenueProductReportRsp>> getRevenueProductReportHandle
    (@RequestHeader("Authorization") String jwt, @RequestParam(required = false) String
            filter, @RequestParam(value = "start", required = false) String
             dateStart, @RequestParam(value = "end", required = false) String dateEnd) {
        ListEntityResponse<RevenueProductReportRsp> res = new ListEntityResponse<>();
        Date start = null;
        Date end = null;
        System.err.println(filter + dateEnd + dateStart);
        if (dateEnd != null || dateStart != null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                // Parsing a String to Date
                start = dateFormatter.parse(dateStart);
                end = dateFormatter.parse(dateEnd);

            } catch (ParseException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            List<RevenueProductReportRsp> get = productService.getRevenueProductReport(filter, start, end);
            res.setData(get);
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
}
