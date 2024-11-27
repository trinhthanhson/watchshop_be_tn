package ptithcm.tttn.controller.statistic;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.request.StatisticRequest;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.response.RevenueReportRsp;
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

    @GetMapping("/revenue/report")
    public ResponseEntity<ListEntityResponse<RevenueReportRsp>> getRevenueReportHandle(@RequestHeader("Authorization") String jwt, @RequestParam("start") String dateStart, @RequestParam("end") String dateEnd) {

        ListEntityResponse<RevenueReportRsp> res = new ListEntityResponse<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            // Parsing a String to Date
            start = dateFormatter.parse(dateStart);
            end = dateFormatter.parse(dateEnd);
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

    @GetMapping("/sales")
    public ResponseEntity<EntityResponse<List<StatisticRequest>>> getStatisticSaleOrder(@RequestHeader("Authorization") String jwt){
        EntityResponse<List<StatisticRequest>> res = new EntityResponse<>();
        try{
            List<StatisticRequest> rq = orderService.getTotalPriceByStatus();
            res.setData(rq);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e) {
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }


    @GetMapping("/product")
    public ResponseEntity<ListEntityResponse<ProductSaleRequest>> getStatisticProduct(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<ProductSaleRequest> res = new ListEntityResponse<>();
        try{
            List<ProductSaleRequest> get = productService.getProductSales();
            for(ProductSaleRequest rq: get){
                System.out.println(rq.getProduct_id());
            }
            res.setData(get);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e) {
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/year")
    public ResponseEntity<ListEntityResponse<StatisticRequest>> getStatisticOrder(@RequestHeader("Authorization") String jwt,@RequestParam String year, @RequestParam String type){
        int changeYear = Integer.valueOf(year);
        ListEntityResponse<StatisticRequest> res = new ListEntityResponse<>();
        try{
            List<StatisticRequest> get = orderService.getTotalAmountByMonth(changeYear, type);
            res.setData(get);
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

    @GetMapping("/date")
    public ResponseEntity<ListEntityResponse<ProductSaleRequest>> getStatisticOrderByDate(@RequestParam("start") String dateStart, @RequestParam("end") String dateEnd,@RequestHeader("Authorization") String jwt){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        ListEntityResponse<ProductSaleRequest> res = new ListEntityResponse<>();
        try {
            // Parsing a String to Date
            start = dateFormatter.parse(dateStart);
            end = dateFormatter.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try{
            List<ProductSaleRequest> get = orderService.getTotalAmountByDate(start, end);
            res.setData(get);
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

}
