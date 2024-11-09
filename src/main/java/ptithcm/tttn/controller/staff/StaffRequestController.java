package ptithcm.tttn.controller.staff;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.impl.TransactionRequestServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/staff/request")
public class StaffRequestController {

    private final TransactionRequestServiceImpl requestService;

    public StaffRequestController(TransactionRequestServiceImpl requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Transaction_request>> getAllTransactionRequestHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Transaction_request> res = new ListEntityResponse<>();
        try{
            List<Transaction_request> etts = requestService.findAll();
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransactionRequestHandle(@RequestHeader("Authorization") String jwt, @RequestBody TransactionRequest rq){
        ApiResponse res = new ApiResponse();
        try {
            Transaction_request add = requestService.createRequest(rq, jwt);
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
}
