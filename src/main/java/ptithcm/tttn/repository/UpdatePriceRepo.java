package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Update_price;

import java.util.List;

@Repository
public interface UpdatePriceRepo extends JpaRepository<Update_price,Long> {

    @Query(value = "SELECT * FROM update_price WHERE product_id = ?1  ", nativeQuery = true)
    Update_price findByProductId(String product_id);

    @Query(value =  "SELECT up.* " +
            "FROM update_price up " +
            "JOIN product p ON up.product_id = p.product_id " +
            "WHERE up.updated_at = ( " +
            "    SELECT MAX(up2.updated_at) " +
            "    FROM update_price up2 " +
            "    WHERE up2.product_id = up.product_id AND up2.updated_at < NOW() " +
            ")  ",nativeQuery = true)
    List<Update_price> getPriceProduct();

    @Query(value =  "SELECT up.* " +
            "FROM update_price up " +
            "JOIN product p ON up.product_id = p.product_id " +
            "WHERE up.product_id = :product_id  " +
            "  AND up.updated_at = ( " +
            "    SELECT MAX(up2.updated_at) " +
            "    FROM update_price up2 " +
            "    WHERE up2.product_id = up.product_id " +
            "      AND up2.updated_at < NOW() " +
            "  );",nativeQuery = true)
    Update_price getPriceProductById(@Param("product_id") String product_id);
}
