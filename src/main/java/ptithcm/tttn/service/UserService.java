package ptithcm.tttn.service;

import ptithcm.tttn.entity.User;
import ptithcm.tttn.request.ChangePasswordRequest;
import ptithcm.tttn.request.SignUpRequest;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {
    User createUser(SignUpRequest rq) throws Exception;

    User findByUsername(String username) throws Exception;

    User signIn(User user);

    User findUserByJwt(String jwt) throws Exception;

    User findById(Long id) throws Exception;

    List<User> findAll() throws Exception;

    User updateStatus(Long id, String status, String jwt) throws Exception;

    User changePassword(String jwt, ChangePasswordRequest user) throws Exception;

    String sendMail(String email,String subject , String content, String otp) throws MessagingException;

    User updatePassword(String passWord,String email) throws Exception;

    boolean checkUserNameExist(String username);

    User createUserStaff(SignUpRequest rq) throws Exception;

}
