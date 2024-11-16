package ptithcm.tttn.controller.inventory;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/transaction")
@AllArgsConstructor
public class StaffTransactionController {

    private final TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Transaction>> findAllTransactionHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Transaction> res = new ListEntityResponse<>();
        try{
            List<Transaction> etts = transactionService.findAll();
            res.setData(etts);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage(MessageSuccess.E01.getMessage());
        }
        catch (Exception e)
        {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
        }

        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("{id}/get")
    public ResponseEntity<EntityResponse<Transaction>> findOrderById(@PathVariable Long id){

        EntityResponse<Transaction> res = new EntityResponse<>();
        try{
            Transaction ett = transactionService.findById(id);
            res.setData(ett);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage(MessageSuccess.E01.getMessage());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
