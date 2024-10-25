package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Order_detail;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<Order_detail,Long> {
    @Query("SELECT SUM(quantity*price) FROM Order_detail WHERE order_id = ?1")
    int totalPriceByOrderId(Long cart_id);

    @Query("SELECT SUM(quantity) FROM Order_detail WHERE order_id = ?1")
    int totalQuantityByOrderId(Long cart_id);

    @Query("SELECT o FROM Order_detail o WHERE o.order_id = ?1")
    List<Order_detail> findOrderDetailByOrderId(Long order_id);

}
