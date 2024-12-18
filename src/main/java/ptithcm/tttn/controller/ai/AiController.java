package ptithcm.tttn.controller.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.response.GetDataAiTransaction;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/data/ai")
@RequiredArgsConstructor
public class AiController {
    private final TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<GetDataAiTransaction>> getAllDataAiTransaction(@RequestHeader("Authorization") String jwt,@RequestParam("quantity") int quantity){
        ListEntityResponse<GetDataAiTransaction> res = new ListEntityResponse<>();

        try {
            List<GetDataAiTransaction> etts = transactionService.getDataAiTransaction(quantity);

            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
}
