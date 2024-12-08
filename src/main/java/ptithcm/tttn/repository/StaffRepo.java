package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Staff;

import java.util.List;

@Repository
public interface StaffRepo extends JpaRepository<Staff,Long> {
    @Query(value = "SELECT * FROM staff WHERE user_id = ?1  ", nativeQuery = true)
    Staff findByUserId(Long user_id);

    @Query(value = "SELECT * FROM staff WHERE email = ?1  ", nativeQuery = true)
    Staff findByEmail(String email);

    @Query(
            value = "SELECT s.* " +
                    "FROM staff s " +
                    "JOIN user u ON s.user_id = u.user_id " +
                    "JOIN role r ON r.role_id = u.role_id " +
                    "WHERE r.role_name = 'WAREHOUSE_STAFF' " +
                    "OR r.role_name = 'WAREHOUSE_MANAGER' " +
                    "OR r.role_name = 'WAREHOUSE_KEEPER'",nativeQuery = true
    )
    List<Staff> findWarehouseRelatedStaff();
}
