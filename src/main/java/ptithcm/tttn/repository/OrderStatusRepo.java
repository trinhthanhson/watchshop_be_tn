package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.OrderStatus;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus,Long> {

}
