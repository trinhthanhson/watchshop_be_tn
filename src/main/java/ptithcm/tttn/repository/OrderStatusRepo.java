package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.OrderStatus;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus,Long> {

    @Query(value = "SELECT * FROM order_status WHERE status_index = ?1  ", nativeQuery = true)
    OrderStatus findStatusIndex(int status_index);

}
