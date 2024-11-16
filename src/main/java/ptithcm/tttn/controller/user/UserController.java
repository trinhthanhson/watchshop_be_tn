package ptithcm.tttn.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.function.RoleName;
import ptithcm.tttn.request.ChangePasswordRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.ReviewService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final CustomerService customerService;
    private final StaffService staffService;
    private final ReviewService reviewServicep;

    @GetMapping("/find")
    public ResponseEntity<EntityResponse<Object>> findCustomerAndStaffProfileByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        EntityResponse<Object> res = new EntityResponse<>();
        try{
            if(user.getRole_user().getRole_name().equals(RoleName.CUSTOMER.getRoleName())) {
                Customer customer = customerService.findByUserId(user.getUser_id());
                res.setData(customer);
                res.setMessage(MessageSuccess.E01.getMessage());
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
            }else {
                Staff staff = staffService.findByUserId(user.getUser_id());
                res.setData(staff);
                res.setMessage("find staff success");
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
            }
        }catch(Exception e){
            res.setData(null);
            res.setMessage("Error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/update-info")
    public ResponseEntity<ApiResponse> updateInfoByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody Customer customer){
        ApiResponse res = new ApiResponse();
        try{
            Customer saveCustomer = customerService.updateCustomer(customer,jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("Error: " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePasswordByUser(@RequestHeader("Authorization") String jwt,@RequestBody ChangePasswordRequest user){
        ApiResponse res = new ApiResponse();
        try{
            User update = userService.changePassword(jwt, user);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("Error: " +e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res,res.getStatus());

    }
}
