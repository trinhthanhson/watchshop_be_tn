package ptithcm.tttn.controller.staff;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.service.CategoryService;

@RestController
@RequestMapping("/api/staff/category")
@AllArgsConstructor
public class StaffCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<EntityResponse<Category>> addCategoriesByStaff(@RequestBody Category category, @RequestHeader("Authorization") String jwt) {
        EntityResponse<Category> res = new EntityResponse<>();
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        try {
            if (category.getCategory_name().isEmpty()) {
                throw new Exception("Please enter complete information");
            }
            Category saveCategory = categoryService.createCategory(category.getCategory_name(), jwt);
            res.setData(saveCategory);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error: " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, httpStatus);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updatedCategoryByStaff(@RequestBody Category category, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        ApiResponse res = new ApiResponse();
        try {
            if (category.getCategory_name().isEmpty()) {
                throw new Exception("Please enter complete information");
            }
            Category saveCategory = categoryService.updateCategory(id, category.getCategory_name(), jwt);
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

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        ApiResponse res = new ApiResponse();

        try {
            Category saveCategory = categoryService.deleteCategory(id, jwt);
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