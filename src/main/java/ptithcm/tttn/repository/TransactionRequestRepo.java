package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Request_detail;
import ptithcm.tttn.entity.Transaction_request;

@Repository
public interface TransactionRequestRepo extends JpaRepository<Transaction_request,Long> {
}
