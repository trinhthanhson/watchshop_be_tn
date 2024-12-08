package ptithcm.tttn.controller.inventory.director;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.service.TransactionRequestService;

@RestController
@RequestMapping("/api/director/request")
@AllArgsConstructor
public class DirectorRequestController {
    private final TransactionRequestService transactionRequestService;

    @PutMapping("{id}/update")
    public ResponseEntity<ApiResponse> updateStatusTransactionRequest(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
        ApiResponse res = new ApiResponse();
        try {
            transactionRequestService.updateByDirector(jwt, id);
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
