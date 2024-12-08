package ptithcm.tttn.service;

import ptithcm.tttn.entity.Staff;

import java.sql.SQLException;
import java.util.List;

public interface StaffService {
    Staff findByUserId(Long user_id) throws SQLException;

    Staff updateProfileStaff(String jwt, Staff staff) throws Exception;

    boolean checkEmailExist(String email);

    List<Staff> findAll();

    List<Staff> findStaffInventory();
}
