package ptithcm.tttn.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.function.TypeTrans;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.GetAllProductCouponRsp;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.ProductService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/user/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Product>> findAllProduct() {

        ListEntityResponse<Product> res = new ListEntityResponse<>();
        try {
            List<Product> allProduct = productService.findAll();
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setData(allProduct);
        } catch (Exception e) {
            res.setMessage("Error: " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/page")
    public ResponseEntity<ListEntityResponse<Product>> getAllTransactionRequestImportHandle(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        ListEntityResponse<Product> res = new ListEntityResponse<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);

            Page<Product> etts = productService.findAllPageable(pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục

        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/search")
    public ResponseEntity<ListEntityResponse<Product>> searchProductById(@RequestParam("product_id") String product_id, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        ListEntityResponse<Product> res = new ListEntityResponse<>();
        Pageable pageable = PageRequest.of(page - 1, limit);

        try {
            Page<Product> etts = productService.searchProductById(product_id, pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<EntityResponse<Product>> findProductById(@PathVariable String id) {
        EntityResponse<Product> res = new EntityResponse<>();
        try {
            Product findById = productService.findById(id);
            res.setMessage("Success");
            res.setData(findById);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setMessage("Error: " + e.getMessage());
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/find")
    public ResponseEntity<ListEntityResponse<GetAllProductCouponRsp>> getAllProductByDetail(@RequestParam String keyword) {
        ListEntityResponse<GetAllProductCouponRsp> res = new ListEntityResponse<>();
        try {
            List<GetAllProductCouponRsp> find = productService.findByDetail(keyword);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setData(find);
        } catch (Exception e) {
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/all/product")
    public ResponseEntity<ListEntityResponse<GetAllProductCouponRsp>> findAllProductCoupon() {

        ListEntityResponse<GetAllProductCouponRsp> res = new ListEntityResponse<>();
        try {
            List<GetAllProductCouponRsp> allProduct = productService.getAllProductCoupon();
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setData(allProduct);
        } catch (Exception e) {
            res.setMessage("Error: " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/page/all")
    public ResponseEntity<ListEntityResponse<Product>> getAllTransactionImportHandle(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit ){ // Hướng mặc định
        Pageable pageable = PageRequest.of(page - 1, limit);
        ListEntityResponse<Product> res = new ListEntityResponse<>();
        try {
            Page<Product> etts = productService.findAllPageable(pageable);

            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent());
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            res.setTotalPages(etts.getTotalPages());
            res.setTotalElements(etts.getTotalElements());

        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage() + "" + e.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

}
