package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    @Query(value = "SELECT * FROM role WHERE role_name = ?1  ", nativeQuery = true)
    Role findByName(String role_name);
}
