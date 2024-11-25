package ptithcm.tttn.controller.statistic;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.response.StatisticRsp;
import ptithcm.tttn.response.TransactionStatisticRsp;
import ptithcm.tttn.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transaction/statistic")
@AllArgsConstructor
public class TransactionStatisticController {

    private final TransactionService transactionService;

    @GetMapping("/type")
    public ResponseEntity<ListEntityResponse<TransactionStatisticRsp>> getStatisticByTypeHandle(@RequestHeader("Authorization") String jwt, @RequestBody Type type) {
        ListEntityResponse<TransactionStatisticRsp> res = new ListEntityResponse<>();
        try {
            List<TransactionStatisticRsp> statistic = transactionService.getStatisticTransaction(type.getType_name());
            res.setData(statistic);
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

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<StatisticRsp>> getStatisticHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ListEntityResponse<StatisticRsp> res = new ListEntityResponse<>();
        try {
            // Nếu startDate hoặc endDate null, set giá trị mặc định (vd: lấy tất cả)
            if (startDate == null) {
                startDate = LocalDate.of(1970, 1, 1); // Hoặc giá trị mặc định khác
            }
            if (endDate == null) {
                endDate = LocalDate.now(); // Hoặc giá trị mặc định khác
            }

            // Gửi tham số startDate và endDate vào service
            List<StatisticRsp> statistic = transactionService.getAllStatistic(startDate, endDate);

            res.setData(statistic);
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

    @GetMapping("/all/type")
    public ResponseEntity<ListEntityResponse<StatisticRsp>> getStatisticByTypeHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "type_name") String typeName) { // Sử dụng @RequestParam cho type_name
        ListEntityResponse<StatisticRsp> res = new ListEntityResponse<>();
        try {
            // Gán giá trị mặc định nếu `startDate` hoặc `endDate` bị null
            if (startDate == null) {
                startDate = LocalDate.of(1970, 1, 1); // Ngày mặc định
            }
            if (endDate == null) {
                endDate = LocalDate.now(); // Ngày hiện tại
            }

            // Gửi tham số vào service
            List<StatisticRsp> statistic = transactionService.getAllStatisticByType(startDate, endDate, typeName);

            res.setData(statistic);
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
