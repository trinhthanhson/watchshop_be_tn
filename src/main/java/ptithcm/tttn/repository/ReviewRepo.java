package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Review;

import java.util.List;

@Repository
public interface  ReviewRepo extends JpaRepository<Review,Long> {
    @Query(value = "SELECT * FROM review WHERE created_by = ?1  ", nativeQuery = true)
    List<Review> findAllReviewByCustomer(Long customer_id);

    @Query(value = "SELECT r.* " +
            "FROM review r " +
            "JOIN order_detail od ON r.order_detail_id = od.order_detail_id " +
            "JOIN product p ON od.product_id = p.product_id " +
            "WHERE p.product_id = :product_id", nativeQuery = true)
    List<Review> findAllReviewByProduct(@Param("product_id") String product_id);

    @Query(value = "SELECT * FROM review WHERE order_detail_id = ?1  ", nativeQuery = true)
    Review findReviewByOrderDetail(Long order_detail_id);
}
