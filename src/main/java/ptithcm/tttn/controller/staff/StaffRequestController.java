package ptithcm.tttn.controller.staff;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.service.impl.TransactionRequestServiceImpl;

@RestController
@RequestMapping("/api/staff/request")
public class StaffRequestController {

    private final TransactionRequestServiceImpl requestService;

    public StaffRequestController(TransactionRequestServiceImpl requestService) {
        this.requestService = requestService;
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
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
}
