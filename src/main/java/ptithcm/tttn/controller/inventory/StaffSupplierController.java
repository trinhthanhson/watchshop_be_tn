package ptithcm.tttn.controller.inventory;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Supplier;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/supplier")
@AllArgsConstructor
public class StaffSupplierController {

    private final SupplierService supplierService;

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Supplier>> findAllSupplierHandle(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Supplier> res = new ListEntityResponse<>();

        try {
            List<Supplier> findAll = supplierService.findAll();
            res.setData(findAll);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setData(null);
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/add")
    public ResponseEntity<EntityResponse<Supplier>> addSupplierByManager(@RequestBody Supplier Supplier, @RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse<Supplier> res = new EntityResponse<>();
        try{
            if (Supplier.getSupplier_name().isEmpty()) {
                throw new Exception("Please enter complete information");
            }
            Supplier saveSupplier = supplierService.createSupplier(Supplier,jwt);
            res.setData(saveSupplier);
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

    @PutMapping("/{id}/update")
    public ResponseEntity<EntityResponse<Supplier>> updatedSupplierByStaff(@RequestBody Supplier Supplier, @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception{
        EntityResponse<Supplier> res = new EntityResponse<>();
        try{
            if (Supplier.getSupplier_name().isEmpty()) {
                throw new Exception("Please enter complete information");
            }
            Supplier saveSupplier = supplierService.updateSupplier(id,Supplier,jwt);
            res.setData(saveSupplier);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("Error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());

    }

    @GetMapping("/find")
    public ResponseEntity<EntityResponse<Supplier>> findSupplierByName(@RequestParam String name,@RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse<Supplier> res = new EntityResponse<>();
        Supplier Supplier = supplierService.findByName(name);
        res.setData(Supplier);
        res.setMessage("Success");
        res.setStatus(HttpStatus.CREATED);
        res.setCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(res,res.getStatus());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteSupplierByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        ApiResponse res = new ApiResponse();
        try{

            Supplier saveSupplier = supplierService.deleteSupplier(id,jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
