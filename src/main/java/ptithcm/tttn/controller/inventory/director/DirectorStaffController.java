package ptithcm.tttn.controller.inventory.director;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.request.SignUpRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/director/staff")
@AllArgsConstructor
public class DirectorStaffController {
    private final StaffService staffService;
    private final UserService userService;

    @GetMapping("/inventory")
    public ResponseEntity<ListEntityResponse<Staff>> getAllStaffByDirectorHandle(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Staff> res = new ListEntityResponse<>();
        List<Staff> all = staffService.findStaffInventory();
        res.setStatus(HttpStatus.OK);
        res.setMessage(MessageSuccess.E01.getMessage());
        res.setCode(HttpStatus.OK.value());
        res.setData(all);
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUserStaffByAdmin(@RequestBody SignUpRequest rq) throws Exception {
        ApiResponse res = new ApiResponse();
        User existUsername = userService.findByUsername(rq.getUsername());
        boolean checkEmail = staffService.checkEmailExist(rq.getEmail());
        String subject = "Gửi tài khoản đến nhân viên";
        String content = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Gửi tài khoản đến nhân viên</title>"
                + "    <style>"
                + "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + "        .container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #dddddd; border-radius: 5px; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                + "        .header { text-align: center; margin-bottom: 20px; }"
                + "        .header h1 { color: #333333; font-size: 24px; margin: 0; }"
                + "        .content { font-size: 16px; color: #333333; line-height: 1.5; }"
                + "        .otp-code { display: block; font-size: 24px; font-weight: bold; color: #007bff; text-align: center; margin: 20px 0; }"
                + "        .footer { font-size: 14px; color: #888888; text-align: center; margin-top: 20px; }"
                + "        .footer a { color: #007bff; text-decoration: none; }"
                + "        .footer a:hover { text-decoration: underline; }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"container\">"
                + "        <div class=\"header\">"
                + "            <h1>Xác Minh Địa Chỉ Email</h1>"
                + "        </div>"
                + "        <div class=\"content\">"
                + "            <p>Chào <strong>" + rq.getEmail() + "</strong>,</p>"
                + "            <p>Cảm ơn bạn đã trở thành nhân viên của <strong>[WATCHSHOP]</strong>. Dưới đây là mật khẩu đăng nhập và username của bạn. Vui lòng không chia sẻ cho bất kỳ để làm ảnh hưởng đến WEBSITE..</p>"
                + "             <span class=\"otp-code\">" + rq.getUsername() + "</span>"
                + "            <span class=\"otp-code\">" + rq.getPassword() + "</span>"
                + "            <p>Nếu bạn gặp bất kỳ vấn đề nào hoặc cần hỗ trợ thêm, đừng ngần ngại liên hệ với chúng tôi qua địa chỉ email này hoặc gọi điện thoại cho chúng tôi tại <strong>[0363000451]</strong>.</p>"
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>Trân trọng,</p>"
                + "            <p><strong>WATCHSHOP</strong><br>"
                + "            <a href=\"mailto: sontrinh2507@gmail.com\">sontrinh2507@gmail.com</a><br>"
                + "            <a href=\"WATCHSHOP\"> WATCHSHOP</a></p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        if(existUsername != null){
            res.setMessage("username exist");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }else if(checkEmail){
            res.setMessage("email exist");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }else {
            try {
                User user = userService.createUserStaff(rq);
                userService.sendMail(rq.getEmail(),subject,content,rq.getPassword());
                res.setMessage("success");
                res.setStatus(HttpStatus.CREATED);
                res.setCode(HttpStatus.CREATED.value());
            } catch (Exception e) {
                res.setMessage("fail" + e.getMessage());
                res.setStatus(HttpStatus.CONFLICT);
                res.setCode(HttpStatus.CONFLICT.value());
            }
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
