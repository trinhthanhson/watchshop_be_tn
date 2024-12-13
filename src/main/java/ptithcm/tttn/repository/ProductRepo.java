package ptithcm.tttn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.response.GetAllProductCouponRsp;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {

    @Query(value = "SELECT * FROM product WHERE product_id LIKE %:searchTerm%",
            countQuery = "SELECT COUNT(*) FROM product WHERE product_id LIKE %:searchTerm%",
            nativeQuery = true)
    Page<Product> searchProductById(@Param("searchTerm") String searchTerm, Pageable pageable);


    @Query(value = "SELECT " +
            "p.*, " +
            "b.brand_name, " +
            "c.category_name, " +
            "CONCAT(s1.first_name, ' ', s1.last_name) AS created_by_name, " +
            "CONCAT(s2.first_name, ' ', s2.last_name) AS updated_by_name, " +
            "COALESCE(up.price_new, up.price_new) AS current_price, " +
            "COALESCE( " +
            "    ROUND( " +
            "        GREATEST( " +
            "            COALESCE(up.price_new, 0) * (1 - LEAST(SUM(coupon.percent) / 100, 0.2)), " +
            "            0 " +
            "        ), 2 " +
            "    ), " +
            "    up.price_new " +
            ") AS discounted_price " +
            "FROM " +
            "product p " +
            "LEFT JOIN brand b ON p.brand_id = b.brand_id " +
            "LEFT JOIN category c ON p.category_id = c.category_id " +
            "LEFT JOIN staff s1 ON p.created_by = s1.staff_id " +
            "LEFT JOIN staff s2 ON p.updated_by = s2.staff_id " +
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
            "LEFT JOIN coupon_detail cd ON p.product_id = cd.product_id " +
            "LEFT JOIN coupon coupon ON cd.coupon_id = coupon.coupon_id " +
            "WHERE " +
            "p.status = 'ACTIVE' " +
            "AND (coupon.status = 'ACTIVE' OR coupon.status IS NULL) " +
            "AND ( " +
            "    p.product_name LIKE %:searchTerm% OR " +
            "    p.band_material LIKE %:searchTerm% OR " +
            "    p.band_width LIKE %:searchTerm% OR " +
            "    p.case_diameter LIKE %:searchTerm% OR " +
            "    p.case_material LIKE %:searchTerm% OR " +
            "    p.case_thickness LIKE %:searchTerm% OR " +
            "    p.color LIKE %:searchTerm% OR " +
            "    p.detail LIKE %:searchTerm% OR " +
            "    p.dial_type LIKE %:searchTerm% OR " +
            "    p.func LIKE %:searchTerm% OR " +
            "    p.gender LIKE %:searchTerm% OR " +
            "    p.machine_movement LIKE %:searchTerm% OR " +
            "    p.model LIKE %:searchTerm% OR " +
            "    p.series LIKE %:searchTerm% OR " +
            "    p.water_resistance LIKE %:searchTerm% " +
            ") " +
            "GROUP BY " +
            "p.product_id, " +
            "p.product_name, " +
            "p.category_id, " +
            "p.brand_id, " +
            "b.brand_name, " +
            "c.category_name, " +
            "p.status, " +
            "p.created_at, " +
            "up.price_new, " +
            "s1.first_name, " +
            "s1.last_name, " +
            "s2.first_name, " +
            "s2.last_name;",nativeQuery = true
    )
    List<Object[]> searchProducts(@Param("searchTerm") String searchTerm);

    // <editor-fold desc="Top 5 product sale report">
    @Query(value = "SELECT p.product_id, p.product_name, SUM(td.quantity * td.price) as total_sold, SUM(td.quantity) AS total_quantity " +
            "FROM Product p " +
            "JOIN transaction_detail td ON p.product_id = td.product_id " +
            "JOIN transaction t ON t.transaction_id = td.transaction_id " +
            "GROUP BY p.product_id, p.product_name " +
            "ORDER BY total_sold DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> getProductSalesTop5();
    // </editor-fold>

    // <editor-fold desc="product sale report">
    @Query(value = "SELECT p.product_id, p.product_name, SUM(td.quantity * td.price) as total_sold, SUM(td.quantity) AS total_quantity " +
            "FROM Product p " +
            "JOIN transaction_detail td ON p.product_id = td.product_id " +
            "JOIN transaction t ON t.transaction_id = td.transaction_id " +
            "GROUP BY p.product_id, p.product_name " +
            "ORDER BY total_sold DESC", nativeQuery = true)
    List<Object[]> getProductSalesReport();
    // </editor-fold>

    @Query(value = "SELECT * FROM product WHERE LOWER(product_name) = ?1  ", nativeQuery = true)
    Product findByName(String product_name);

    @Query(value = "SELECT * FROM product WHERE category_id = ?1  ", nativeQuery = true)
    List<Product> findByCategoryId(Long category_id);

    @Query(value = "SELECT * FROM product WHERE brand_id = ?1  ", nativeQuery = true)
    List<Product> findByBrandId(Long brand_id);

    // <editor-fold desc="quantity inventory report">
    @Query(value = "SELECT " +
            "    p.product_id, " +
            "    p.product_name, " +
            "    p.image, " +
            "    p.quantity, " +
            "    SUM(CASE WHEN t_type.type_name = 'IMPORT' THEN td.quantity ELSE 0 END) AS total_import, " +
            "    SUM(CASE WHEN t_type.type_name = 'EXPORT' THEN td.quantity ELSE 0 END) AS total_export, " +
            "    (SUM(CASE WHEN t_type.type_name = 'IMPORT' THEN td.quantity ELSE 0 END) - " +
            "     SUM(CASE WHEN t_type.type_name = 'EXPORT' THEN td.quantity ELSE 0 END)) AS current_stock, " +
            "    DATE(t.created_at) AS period_value, " +  // Lấy theo ngày nếu không có filter
            "    CASE " +
            "        WHEN :filter IS NULL OR :filter = '' THEN DATE(t.created_at) " +  // Hiển thị ngày cụ thể khi không có filter
            "        ELSE NULL " +
            "    END AS date_range " +
            "FROM product p " +
            "JOIN transaction_detail td ON td.product_id = p.product_id " +
            "JOIN transaction t ON t.transaction_id = td.transaction_id " +
            "JOIN type t_type ON t.type_id = t_type.type_id " +
            "WHERE " +
            "(COALESCE(:filter, '') = '' OR :filter = 'all' OR " +
            "(:filter = 'week' AND YEAR(t.created_at) = YEAR(CURRENT_DATE()) AND WEEK(t.created_at) = WEEK(CURRENT_DATE())) OR " +
            "(:filter = 'month' AND YEAR(t.created_at) = YEAR(CURRENT_DATE()) AND MONTH(t.created_at) = MONTH(CURRENT_DATE())) OR " +
            "(:filter = 'year' AND YEAR(t.created_at) = YEAR(CURRENT_DATE()))) " +
            "AND (COALESCE(:startDate, NULL) IS NULL AND COALESCE(:endDate, NULL) IS NULL OR " +
            "DATE(t.created_at) BETWEEN COALESCE(DATE(:startDate), DATE('1900-01-01')) AND COALESCE(DATE(:endDate), CURRENT_DATE())) " +
            "GROUP BY p.product_id, p.product_name, p.quantity, period_value, t.created_at " +  // Thêm period_value vào GROUP BY
            "ORDER BY period_value ASC",  // Sắp xếp theo ngày
            nativeQuery = true)
    List<Object[]> getQuantityInventoryByFilter(@Param("filter") String filter,
                                                @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);

    // </editor-fold>

    // <editor-fold desc="Revenue product report">
    @Query(value = "SELECT p.product_id, " +
            "       p.product_name, " +
            "       p.image, " +
            "       SUM(td.quantity * td.price) AS total_sold, " +
            "       SUM(td.quantity) AS total_quantity, " +
            "       CASE " +
            "           WHEN :filter = 'week' THEN WEEK(t.created_at) " +
            "           WHEN :filter = 'month' THEN MONTH(t.created_at) " +
            "           WHEN :filter = 'year' THEN YEAR(t.created_at) " +
            "           ELSE DATE(t.created_at) " +  // Nếu không có filter, trả về theo từng ngày
            "       END AS period_value, " +
            "       CASE " +
            "           WHEN :filter IS NULL OR :filter = '' THEN DATE(t.created_at) " +  // Hiển thị ngày cụ thể khi không có filter
            "           ELSE NULL " +
            "       END AS date_range " +
            "FROM Product p " +
            "JOIN transaction_detail td ON p.product_id = td.product_id " +
            "JOIN transaction t ON t.transaction_id = td.transaction_id " +
            "JOIN type tp ON tp.type_id = t.type_id " +
            "WHERE tp.type_name = 'EXPORT' " +
            "  AND (:startDate IS NULL OR :endDate IS NULL OR DATE(t.created_at) BETWEEN DATE(:startDate) AND DATE(:endDate)) " +  // Điều kiện lọc theo từ ngày đến ngày
            "GROUP BY p.product_id, p.product_name, period_value,t.created_at " +
            "ORDER BY period_value ASC, total_sold DESC",  // Sắp xếp theo ngày và doanh thu
            nativeQuery = true)
    List<Object[]> getRevenueProduct(
            @Param("filter") String filter,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    // </editor-fold>

    // <editor-fold desc="get all product or product have coupon">
    @Query(value = "SELECT " +
            "p.*, " +
            "b.brand_name, " +
            "c.category_name, " +
            "CONCAT(s1.first_name, ' ', s1.last_name) AS created_by_name, " +
            "CONCAT(s2.first_name, ' ', s2.last_name) AS updated_by_name, " +
            "COALESCE(CAST(up.price_new AS DOUBLE), CAST(up.price_new AS DOUBLE)) AS current_price, " +
            "COALESCE( " +
            "    ROUND( " +
            "        GREATEST( " +
            "            COALESCE(CAST(up.price_new AS DOUBLE), 0) * (1 - LEAST(SUM(coupon.percent) / 100, 0.2)), " +
            "            0 " +
            "        ), 2 " +
            "    ), " +
            "    CAST(up.price_new AS DOUBLE) " +
            ") AS discounted_price " +
            "FROM " +
            "product p " +
            "LEFT JOIN brand b ON p.brand_id = b.brand_id " +
            "LEFT JOIN category c ON p.category_id = c.category_id " +
            "LEFT JOIN staff s1 ON p.created_by = s1.staff_id " +
            "LEFT JOIN staff s2 ON p.updated_by = s2.staff_id " +
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
            "LEFT JOIN coupon_detail cd ON p.product_id = cd.product_id " +
            "LEFT JOIN coupon coupon ON cd.coupon_id = coupon.coupon_id " +
            "WHERE " +
            "p.status = 'ACTIVE' " +
            "AND (coupon.status = 'ACTIVE' OR coupon.status IS NULL) " +
            "GROUP BY " +
            "p.product_id, " +
            "p.product_name, " +
            "p.category_id, " +
            "p.brand_id, " +
            "b.brand_name, " +
            "c.category_name, " +
            "p.status, " +
            "p.created_at, " +
            "up.price_new, " +
            "s1.first_name, " +
            "s1.last_name, " +
            "s2.first_name, " +
            "s2.last_name",
            nativeQuery = true)
    List<Object[]> getAllProductOrProductByCoupon();
    // </editor-fold>
}
