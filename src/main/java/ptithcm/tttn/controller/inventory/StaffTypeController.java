package ptithcm.tttn.controller.inventory;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Brand;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.TypeService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/type")
@AllArgsConstructor
public class StaffTypeController {

    private final TypeService typeService;

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Type>> findAllTransactionHandle(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Type> res = new ListEntityResponse<>();
        try{
            List<Type> ett = typeService.findAll();
            res.setData(ett);
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

    @PostMapping("/add")
    public ResponseEntity<EntityResponse<Type>> addBrandByManager(@RequestBody Type type, @RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse<Type> res = new EntityResponse<>();
        try{
            if (type.getType_name().isEmpty()) {
                throw new Exception("Please enter complete information");
            }
            Type saveType = typeService.createType(type);
            res.setData(saveType);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("Error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
