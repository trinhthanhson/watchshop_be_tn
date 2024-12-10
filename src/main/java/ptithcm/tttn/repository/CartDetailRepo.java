package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Cart_detail;
import ptithcm.tttn.response.GetAllProductCouponRsp;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartDetailRepo extends JpaRepository<Cart_detail, Long> {

    @Query(value = "SELECT * FROM cart_detail WHERE customer_id = ?1", nativeQuery = true)
    List<Cart_detail> findAllByCustomerId(Long customer_id);

    @Query(value = "SELECT * FROM cart_detail WHERE customer_id = ?1 and product_id = ?2", nativeQuery = true)
    Cart_detail findPosCart(Long customer_id, String product_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_detail WHERE customer_id = ?1", nativeQuery = true)
    void deleteCartByCustomerId(Long customer_id);

    @Query(value = "SELECT " +
            "    cd.product_id, " +
            "    p.product_name, " +
            "    p.image, " +
            "    p.quantity, " +
            "    cd.quantity as quantityProduct, " +
            "    COALESCE( " +
            "        ROUND( " +
            "            GREATEST( " +
            "                COALESCE(CAST(up.price_new AS DOUBLE), 0.0) * (1 - LEAST(SUM(c.percent) / 100, 0.2)), " + // Giới hạn giảm giá tối đa 20%
            "                 0.0 " + // Đảm bảo giá trị không âm
            "            ), 2 " +
            "        ), " +
            "        CAST(up.price_new AS DOUBLE) " + // Nếu không có giảm giá, lấy giá gốc
            "    ) AS discounted_price, " +
            "    COALESCE(CAST(up.price_new AS DOUBLE), 0.0) AS price_new " + // Giá gốc
            "FROM " +
            "    cart_detail cd " +
            "LEFT JOIN product p ON cd.product_id = p.product_id " +
            "LEFT JOIN ( " +
            "    SELECT " +
            "        product_id, " +
            "        price_new " +
            "    FROM " +
            "        update_price up1 " +
            "    WHERE " +
            "        updated_at = ( " +
            "            SELECT MAX(updated_at) " +
            "            FROM update_price up2 " +
            "            WHERE up1.product_id = up2.product_id " +
            "        ) " +
            ") up ON p.product_id = up.product_id " +
            "LEFT JOIN coupon_detail cd2 ON p.product_id = cd2.product_id " +
            "LEFT JOIN coupon c ON cd2.coupon_id = c.coupon_id " +
            "WHERE " +
            "    cd.customer_id = :customer_id " + // Sử dụng tham số có tên
            "    AND p.status = 'ACTIVE' " + // Chỉ lấy sản phẩm ACTIVE
            "    AND (c.status = 'ACTIVE' OR c.status IS NULL) " + // Coupon ACTIVE hoặc không có coupon
            "GROUP BY " +
            "    cd.product_id, " +
            "    p.product_name, " +
            "    up.price_new, " +
            "    p.quantity, " +
            "    cd.quantity, " +
            "    p.image", nativeQuery = true)
    List<Object[]> getProductCouponInCartDetail(@Param("customer_id") Long customer_id);

}
