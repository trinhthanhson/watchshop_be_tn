package ptithcm.tttn.controller.statistic;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.response.TransactionStatisticRsp;
import ptithcm.tttn.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transaction/statistic")
@AllArgsConstructor
public class TransactionStatisticController {

    private final TransactionService transactionService;

    @GetMapping("/type")
    public ResponseEntity<ListEntityResponse<TransactionStatisticRsp>> getStatisticByTypeHandle(@RequestHeader("Authorization") String jwt, @RequestBody Type type){
        ListEntityResponse<TransactionStatisticRsp> res = new ListEntityResponse<>();
        try {
            List<TransactionStatisticRsp> statistic = transactionService.getStatisticTransaction(type.getType_name());
            res.setData(statistic);
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
