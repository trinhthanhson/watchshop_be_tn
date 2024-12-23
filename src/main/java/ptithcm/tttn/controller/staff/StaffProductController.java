package ptithcm.tttn.controller.staff;


import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Update_price;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.UpdatePriceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/staff/product")
@AllArgsConstructor
public class StaffProductController {

    private final ProductService productService;
    private final UpdatePriceService updatePriceService;

    @PostMapping("/add")
    public ResponseEntity<EntityResponse<Product>> addProductByStaff(@RequestBody ProductRequest rq, @RequestHeader("Authorization") String jwt) {
        EntityResponse<Product> res = new EntityResponse<>();
        try {
            Product saveProduct = productService.createProduct(rq, jwt);
            res.setData(saveProduct);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createProductsBatch(@RequestBody List<ProductRequest> products, @RequestHeader("Authorization") String jwt) {
        try {
            List<Product> savedProducts = new ArrayList<>();
            for (ProductRequest product : products) {
                Product savedProduct = productService.createProduct(product, jwt);
                savedProducts.add(savedProduct);
            }
            return ResponseEntity.ok(savedProducts);
        } catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EntityResponse<Product>> updateProductByStaff(@RequestBody ProductRequest productRequest, @RequestHeader("Authorization") String jwt, @PathVariable String id) {
        EntityResponse<Product> res = new EntityResponse<>();
        try {
            Product saveProduct = productService.updateProduct(id, productRequest, jwt);
            res.setData(saveProduct);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/update/price")
    public ResponseEntity<EntityResponse<Update_price>> updatePriceProductByStaff(@RequestBody @DateTimeFormat(pattern = "yyyy-MM-dd") Update_price rq, @RequestHeader("Authorization") String jwt) {
        EntityResponse<Update_price> res = new EntityResponse<>();
        try {
            Update_price savePrice = updatePriceService.updatePriceProduct(rq, jwt);
            res.setData(savePrice);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductByStaff(@RequestHeader("Authorization") String jwt, @PathVariable String productId) {
        ApiResponse res = new ApiResponse();
        try {
            Product delete = productService.deleteProduct(productId, jwt);
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
