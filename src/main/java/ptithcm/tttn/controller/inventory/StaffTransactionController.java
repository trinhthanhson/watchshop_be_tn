package ptithcm.tttn.controller.inventory;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.TransactionService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/inventory/transaction")
@AllArgsConstructor
public class StaffTransactionController {

    private final TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransactionImportHandle(@RequestHeader("Authorization") String jwt, @RequestBody TransactionRequest rq){
        ApiResponse res = new ApiResponse();
        try {
            Transaction add = transactionService.create(rq, jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setCode(HttpStatus.BAD_REQUEST.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }


    @PostMapping("/{id}/add")
    public ResponseEntity<ApiResponse> addTransactionExportHandle(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        ApiResponse res = new ApiResponse();
        try {
            Transaction add = transactionService.createExport(id, jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setCode(HttpStatus.BAD_REQUEST.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/{id}/check")
    public ResponseEntity<ApiResponse> existsByRequestId(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        ApiResponse res = new ApiResponse();
        try {
            BigInteger add = transactionService.existsByRequestId(id);
            res.setMessage(add.toString());
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setCode(HttpStatus.BAD_REQUEST.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/all/import")
    public ResponseEntity<ListEntityResponse<Transaction>> findAllTransactionImportHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Transaction> res = new ListEntityResponse<>();
        List<Transaction> allImport = new ArrayList<>();

        try{
            List<Transaction> etts = transactionService.findAll();

            for(Transaction item :etts){
                if(item.getType_transaction().getType_name().equals("IMPORT")){
                    allImport.add(item);
                }
            }
            res.setData(allImport);
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

    @GetMapping("/all/export")
    public ResponseEntity<ListEntityResponse<Transaction>> findAllTransactionExportHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Transaction> res = new ListEntityResponse<>();
        List<Transaction> allExport = new ArrayList<>();

        try{
            List<Transaction> etts = transactionService.findAll();

            for(Transaction item :etts){
                if(item.getType_transaction().getType_name().equals("EXPORT")){
                    allExport.add(item);
                }
            }
            res.setData(allExport);
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
