package ptithcm.tttn.controller.staff;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.TypeService;

import java.util.List;

@RestController
@RequestMapping("/api/staff/type")
public class StaffTypeController {

    private final TypeService typeService;

    public StaffTypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Type>> findAllTransactionHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Type> res = new ListEntityResponse<>();
        try{
            List<Type> etts = typeService.findAll();
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
}
