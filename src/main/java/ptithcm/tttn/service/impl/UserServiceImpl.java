package ptithcm.tttn.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ptithcm.tttn.config.JwtTokenProvider;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.Role;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.function.RoleName;
import ptithcm.tttn.function.Status;
import ptithcm.tttn.repository.CustomerRepo;
import ptithcm.tttn.repository.RoleRepo;
import ptithcm.tttn.repository.StaffRepo;
import ptithcm.tttn.repository.UserRepo;
import ptithcm.tttn.request.ChangePasswordRequest;
import ptithcm.tttn.request.SignUpRequest;
import ptithcm.tttn.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private JavaMailSender mailSender;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepo userRepo;
    private final StaffRepo staffRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepo customerRepo;
    private  String otpAccept;

    public UserServiceImpl(JavaMailSender mailSender, JwtTokenProvider jwtTokenProvider, UserRepo userRepo, StaffRepo staffRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, CustomerRepo customerRepo) {
        this.mailSender = mailSender;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
        this.staffRepo = staffRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional
    public User createUser(SignUpRequest rq) throws Exception {
        if(otpAccept.equals(rq.getOtp())) {
            User user = new User();
            Role role = roleRepo.findByName(rq.getRole_name());
            user.setCreated_at(LocalDateTime.now());
            user.setStatus(Status.ACTIVE.getUserStatus());
            user.setUpdated_at(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(rq.getPassword()));
            user.setRole_id(role.getRole_id());
            user.setUsername(rq.getUsername());
            User saveUser = userRepo.save(user);
            if (saveUser != null) {
                Customer customer = new Customer();
                customer.setCreated_at(LocalDateTime.now());
                customer.setEmail(rq.getEmail());
                customer.setUser_id(saveUser.getUser_id());
                customer.setFirst_name(rq.getFirstname());
                customer.setLast_name(rq.getLastname());
                customer.setUpdated_at(LocalDateTime.now());
                Customer saveCustomer = customerRepo.save(customer);
            }
            return saveUser;
        }else {
            throw new Exception("OTP không chính xác vui lòng kiểm tra lại");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public User signIn(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String username = jwtTokenProvider.getUsernameFromJwtToken(jwt);
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new Exception("user not exist with username " + username);
        }
        return user;
    }

    @Override
    public User findById(Long id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new Exception("Not found User by id " + id);
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> findAll = userRepo.findAll();
        if (findAll != null) {
            return findAll;
        }
        throw new Exception("User is empty");
    }

    @Override
    @Transactional
    public User updateStatus(Long id, String status, String jwt) throws Exception {
        User user = findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        User update = findById(id);
        User save = new User();
        if(update != null && (user.getRole_user().getRole_name().equals(RoleName.MANAGER.getRoleName()) || user.getRole_user().getRole_name().equals(RoleName.STAFF.getRoleName())) ){
            update.setStatus(status);
            update.setUpdated_by(staff.getStaff_id());
            save = userRepo.save(update);
        }else{
            throw new Exception("Can't update user of staff");
        }
        return user;
    }

    @Override
    public User changePassword(String jwt, ChangePasswordRequest rq) throws Exception {
        User find = findUserByJwt(jwt);
        if(passwordEncoder.matches(rq.getPassword(), find.getPassword())) {
            find.setPassword(passwordEncoder.encode(rq.getNewPassword()));
            find.setUpdated_at(LocalDateTime.now());
            return userRepo.save(find);
        }
        throw new Exception("Password is incorrect");
    }
    @Override
    public String sendMail(String email,String subject ,String content, String otp) throws MessagingException {

        sendMail(email, subject,content);
        otpAccept = otp;
        return otpAccept;
    }

    @Override
    @Transactional
    public User updatePassword(String passWord,String email ) throws Exception {
        Customer customer = customerRepo.findByEmail(email);
        User update = findById(customer.getUser_id());
        update.setPassword(passwordEncoder.encode(passWord));
        update.setUpdated_at(LocalDateTime.now());
        User save = userRepo.save(update);
        if(save != null){
            return save;
        }
        throw new Exception("update fail");

    }

    @Override
    public boolean checkUserNameExist(String username) {
        User user = userRepo.findByUsername(username.toLowerCase().trim());
        if(user != null){
            return true;
        }

        return false;
    }

    public void sendMail(String toEmail, String subject, String content) throws MessagingException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("sontrinh2507@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(mimeMessage);
    }

    @Override
    @Transactional
    public User createUserStaff(SignUpRequest rq) throws Exception {
        User user = new User();
        Role role = roleRepo.findByName(rq.getRole_name());
        user.setCreated_at(LocalDateTime.now());
        user.setStatus("Active");
        user.setUpdated_at(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(rq.getPassword()));
        user.setRole_id(role.getRole_id());
        user.setUsername(rq.getUsername());
        User saveUser = userRepo.save(user);
        if(saveUser != null){
            Staff staff = new Staff();
            staff.setUser_id(saveUser.getUser_id());
            staff.setCreated_at(LocalDateTime.now());
            staff.setEmail(rq.getEmail());
            staff.setFirst_name(rq.getFirstname());
            staff.setLast_name(rq.getLastname());
            Staff saveStaff = staffRepo.save(staff);
        }
        return saveUser;
    }
}
