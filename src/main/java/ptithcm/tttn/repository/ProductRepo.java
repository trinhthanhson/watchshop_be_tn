package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE " +
            "p.product_name LIKE %:searchTerm% OR " +
            "p.band_material LIKE %:searchTerm% OR " +
            "p.band_width LIKE %:searchTerm% OR " +
            "p.case_diameter LIKE %:searchTerm% OR " +
            "p.case_material LIKE %:searchTerm% OR " +
            "p.case_thickness LIKE %:searchTerm% OR " +
            "p.color LIKE %:searchTerm% OR " +
            "p.detail LIKE %:searchTerm% OR " +
            "p.dial_type LIKE %:searchTerm% OR " +
            "p.func LIKE %:searchTerm% OR " +
            "p.gender LIKE %:searchTerm% OR " +
            "p.machine_movement LIKE %:searchTerm% OR " +
            "p.model LIKE %:searchTerm% OR " +
            "p.series LIKE %:searchTerm% OR " +
            "p.water_resistance LIKE %:searchTerm%")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT p.product_id, p.product_name, SUM(td.quantity * td.price) as total_sold, SUM(td.quantity) AS total_quantity " +
            "FROM Product p " +
            "JOIN transaction_detail td ON p.product_id = td.product_id " +
            "JOIN transaction t ON t.transaction_id = td.transaction_id " +
            "GROUP BY p.product_id, p.product_name " +
            "ORDER BY total_sold DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> getProductSalesTop5();

    @Query(value = "SELECT p.product_id, p.product_name, SUM(td.quantity * td.price) as total_sold, SUM(td.quantity) AS total_quantity " +
            "FROM Product p " +
            "JOIN transaction_detail td ON p.product_id = td.product_id " +
            "JOIN transaction t ON t.transaction_id = td.transaction_id " +
            "GROUP BY p.product_id, p.product_name " +
            "ORDER BY total_sold DESC", nativeQuery = true)
    List<Object[]> getProductSalesReport();


    @Query(value = "SELECT * FROM product WHERE LOWER(product_name) = ?1  ", nativeQuery = true)
    Product findByName(String product_name);

    @Query(value = "SELECT * FROM product WHERE category_id = ?1  ", nativeQuery = true)
    List<Product> findByCategoryId(Long category_id);

    @Query(value = "SELECT * FROM product WHERE brand_id = ?1  ", nativeQuery = true)
    List<Product> findByBrandId(Long brand_id);

}
