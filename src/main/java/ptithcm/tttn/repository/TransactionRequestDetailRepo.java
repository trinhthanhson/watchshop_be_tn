package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.entity.Request_detail;
import ptithcm.tttn.entity.Transaction_request;

import java.util.List;

@Repository
public interface TransactionRequestDetailRepo extends JpaRepository<Request_detail,Long> {
    @Query(value = "SELECT * FROM request_detail WHERE request_id = ?1  ", nativeQuery = true)
    List<Request_detail> findByRequestId(Long request_id);
}
