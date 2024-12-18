package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.response.RevenueReportRsp;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM transaction t " +
            "    JOIN transaction_request tr ON t.request_id = tr.request_id " +
            "    WHERE t.request_id = :requestId " +
            ")", nativeQuery = true)
    BigInteger existsByRequestId(@Param("requestId") Long requestId);


    @Query(value = "SELECT " +
            "DATE(t.created_at) AS createDate, " +
            "t.transaction_code AS transactionCode, " +
            "ti.product_id AS watchId, " +
            "b.product_name AS watchName, " +
            "ti.price, " +
            "ti.start_quantity AS startQty, " +
            "ti.quantity AS actualQuantity, " +
            "t.type_id " +
            "FROM tn_watchshop.transaction t " +
            "LEFT JOIN tn_watchshop.transaction_detail ti ON t.transaction_id = ti.transaction_id " +
            "LEFT JOIN tn_watchshop.product b ON ti.product_id = b.product_id " +
            "JOIN tn_watchshop.type ty ON t.type_id = ty.type_id " +
            "WHERE (:inputType IS NULL OR :inputType = '' OR ty.type_name = :inputType)",
            nativeQuery = true)
    List<Object[]> findTransactionStatistics(@Param("inputType") String inputType);

    @Query(value = "SELECT " +
            "DATE(t.created_at) AS createDate, " +
            "t.transaction_code AS transactionCode, " +
            "ti.product_id AS watchId, " +
            "b.product_name AS watchName, " +
            "ti.price, " +
            "SUM(CASE WHEN t.type_id = 'IMPORT' THEN ti.start_quantity ELSE 0 END) AS importQty, " +
            "SUM(CASE WHEN t.type_id = 'EXPORT' THEN ti.start_quantity ELSE 0 END) AS exportQty, " +
            "SUM(CASE WHEN t.created_at < :startDate THEN " +
            "    (CASE WHEN t.type_id = 'IMPORT' THEN ti.start_quantity ELSE -ti.start_quantity END) " +
            "    ELSE 0 END) AS startQty, " +
            "SUM(CASE WHEN t.created_at <= :endDate THEN " +
            "    (CASE WHEN t.type_id = 'IMPORT' THEN ti.start_quantity ELSE -ti.start_quantity END) " +
            "    ELSE 0 END) AS endQty " +
            "FROM tn_watchshop.transaction t " +
            "LEFT JOIN tn_watchshop.transaction_detail ti ON t.transaction_id = ti.transaction_id " +
            "LEFT JOIN tn_watchshop.product b ON ti.product_id = b.product_id " +
            "JOIN tn_watchshop.type ty ON t.type_id = ty.type_id " +
            "WHERE (:inputType IS NULL OR :inputType = '' OR ty.type_name = :inputType) " +
            "GROUP BY " +
            "DATE(t.created_at), " +
            "t.transaction_code, " +
            "ti.product_id, " +
            "b.product_name, " +
            "ti.price",
            nativeQuery = true)
    List<Object[]> getTransactionReport(
            @Param("inputType") String inputType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query(value = "SELECT " +
            "ti.product_id AS productCode, " +
            "b.product_name AS productName, " +
            "SUM(CASE WHEN :startDate IS NOT NULL AND t.created_at < :startDate THEN ti.quantity ELSE 0 END) AS openingQty, " + // Tồn đầu kỳ (số lượng)
            "SUM(CASE WHEN :startDate IS NOT NULL AND t.created_at < :startDate THEN (ti.quantity * ti.price) ELSE 0 END) AS openingValue, " + // Tồn đầu kỳ (thành tiền)
            "SUM(CASE WHEN :startDate IS NOT NULL AND :endDate IS NOT NULL AND t.created_at BETWEEN :startDate AND :endDate AND ty.type_name = 'IMPORT' THEN ti.quantity ELSE 0 END) AS importQty, " + // Nhập trong kỳ (số lượng)
            "SUM(CASE WHEN :startDate IS NOT NULL AND :endDate IS NOT NULL AND t.created_at BETWEEN :startDate AND :endDate AND ty.type_name = 'IMPORT' THEN (ti.quantity * ti.price) ELSE 0 END) AS importValue, " + // Nhập trong kỳ (thành tiền)
            "SUM(CASE WHEN :startDate IS NOT NULL AND :endDate IS NOT NULL AND t.created_at BETWEEN :startDate AND :endDate AND ty.type_name = 'EXPORT' THEN ti.quantity ELSE 0 END) AS exportQty, " + // Xuất trong kỳ (số lượng)
            "SUM(CASE WHEN :startDate IS NOT NULL AND :endDate IS NOT NULL AND t.created_at BETWEEN :startDate AND :endDate AND ty.type_name = 'EXPORT' THEN (ti.quantity * ti.price) ELSE 0 END) AS exportValue, " + // Xuất trong kỳ (thành tiền)
            "SUM(CASE WHEN :endDate IS NOT NULL AND t.created_at <= :endDate THEN ti.quantity ELSE 0 END) AS closingQty, " + // Tồn cuối kỳ (số lượng)
            "SUM(CASE WHEN :endDate IS NOT NULL AND t.created_at <= :endDate THEN (ti.quantity * ti.price) ELSE 0 END) AS closingValue " + // Tồn cuối kỳ (thành tiền)
            "FROM tn_watchshop.transaction t " +
            "LEFT JOIN tn_watchshop.transaction_detail ti ON t.transaction_id = ti.transaction_id " +
            "LEFT JOIN tn_watchshop.type ty ON t.type_id = ty.type_id " + // Thêm JOIN với bảng type
            "LEFT JOIN tn_watchshop.product b ON ti.product_id = b.product_id " +
            "GROUP BY ti.product_id, b.product_name",
            nativeQuery = true)
    List<Object[]> getFullInventoryReport(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query(value = "SELECT " +
            "ti.product_id AS productCode, " +
            "b.product_name AS productName, " +
            "SUM(CASE WHEN :startDate IS NOT NULL AND t.created_at < :startDate THEN ti.quantity ELSE 0 END) AS openingQty, " + // Tồn đầu kỳ (số lượng)
            "SUM(CASE WHEN :startDate IS NOT NULL AND t.created_at < :startDate THEN (ti.quantity * ti.price) ELSE 0 END) AS openingValue, " + // Tồn đầu kỳ (thành tiền)
            "SUM(CASE WHEN :startDate IS NOT NULL AND :endDate IS NOT NULL AND t.created_at BETWEEN :startDate AND :endDate AND ty.type_name = 'IMPORT' THEN ti.quantity ELSE 0 END) AS importQty, " + // Nhập trong kỳ (số lượng)
            "SUM(CASE WHEN :startDate IS NOT NULL AND :endDate IS NOT NULL AND t.created_at BETWEEN :startDate AND :endDate AND ty.type_name = 'IMPORT' THEN (ti.quantity * ti.price) ELSE 0 END) AS importValue, " + // Nhập trong kỳ (thành tiền)
            "SUM(CASE WHEN :endDate IS NOT NULL AND t.created_at <= :endDate THEN ti.quantity ELSE 0 END) AS closingQty, " + // Tồn cuối kỳ (số lượng)
            "SUM(CASE WHEN :endDate IS NOT NULL AND t.created_at <= :endDate THEN (ti.quantity * ti.price) ELSE 0 END) AS closingValue " + // Tồn cuối kỳ (thành tiền)
            "FROM tn_watchshop.transaction t " +
            "LEFT JOIN tn_watchshop.transaction_detail ti ON t.transaction_id = ti.transaction_id " +
            "LEFT JOIN tn_watchshop.product b ON ti.product_id = b.product_id " +
            "LEFT JOIN tn_watchshop.type ty ON t.type_id = ty.type_id " + // Thêm JOIN với bảng type
            "WHERE (:typeName IS NULL OR ty.type_name = :typeName) " + // Điều kiện lọc theo type_name
            "GROUP BY ti.product_id, b.product_name",
            nativeQuery = true)
    List<Object[]> getFullInventoryReportByType(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("typeName") String typeName // Tham số lọc type_name
    );

    @Query(value = "SELECT " +
            "ti.product_id AS productId, " +
            "b.product_name AS productName, " +
            "WEEK(t.created_at) AS week, " +
            "SUM(ti.quantity) AS quantity, " +
            "SUM(ti.quantity) - LAG(SUM(ti.quantity)) OVER (PARTITION BY ti.product_id ORDER BY WEEK(t.created_at)) AS difference_quantity, " +
            "STDDEV_POP(ti.price) AS price_volatility, " +
            "up.price_new " +
            "FROM tn_watchshop.transaction t " +
            "LEFT JOIN tn_watchshop.transaction_detail ti ON t.transaction_id = ti.transaction_id " +
            "LEFT JOIN tn_watchshop.type ty ON t.type_id = ty.type_id " +
            "LEFT JOIN tn_watchshop.product b ON ti.product_id = b.product_id " +
            "JOIN tn_watchshop.update_price up ON b.product_id = up.product_id " +
            "GROUP BY ti.product_id, b.product_name, WEEK(t.created_at),up.price_new  " +
            "ORDER BY ti.product_id, WEEK(t.created_at)", // Đảm bảo WEEK được đặt trong ORDER BY
            nativeQuery = true)
    List<Object[]> getProductData();

    @Query("SELECT DATE(t.created_at) as transactionDate, SUM(t.total_price) as totalRevenue, SUM(t.total_quantity) as totalQuantitySold " +
            "FROM Transaction t JOIN Type tp ON t.type_id = tp.type_id " +
            "WHERE tp.type_name = 'EXPORT' " +
            "AND (Date(:startDate) IS NULL OR Date(:endDate) IS NULL OR Date(t.created_at) BETWEEN Date(:startDate) AND Date(:endDate)) " +
            "GROUP BY DATE(t.created_at)")
    List<Object[]> getRevenueReport(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value =
            "SELECT  td.product_id AS productId, " +
                    "    WEEK(t.created_at, 1) AS week,  " +
                    "    MIN(DATE(t.created_at)) AS start_date,  " +
                    "    MAX(DATE(t.created_at)) AS end_date,  " +
                    "    COALESCE(SUM(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'IMPORT')  " +
                    "             THEN td.price ELSE 0 END), 0) AS import_price,  " +
                    "    COALESCE(SUM(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'IMPORT')  " +
                    "             THEN td.quantity ELSE 0 END), 0) AS import_quantity,  " +
                    "    COALESCE(SUM(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'EXPORT')  " +
                    "             THEN td.quantity ELSE 0 END), 0) AS export_quantity,  " +
                    "    COALESCE(AVG(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'EXPORT')  " +
                    "             THEN td.price ELSE NULL END), 0) AS price_export,  " +
                    "    COALESCE(ROUND(  " +
                    "        (AVG(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'EXPORT') THEN td.price ELSE NULL END) -  " +
                    "         AVG(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'IMPORT') THEN td.price ELSE NULL END)) /  " +
                    "         NULLIF(AVG(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'IMPORT') THEN td.price ELSE NULL END), 0), 2 " +
                    "    ), 0) AS price_volatility,  " +
                    "    COALESCE(SUM(CASE WHEN WEEK(t.created_at, 1) = WEEK(CURDATE(), 1)  " +
                    "                      AND t.type_id = (SELECT type_id FROM type WHERE type_name = 'IMPORT')  " +
                    "                      THEN td.quantity ELSE 0 END), 0) -  " +
                    "    COALESCE(SUM(CASE WHEN WEEK(t.created_at, 1) = WEEK(CURDATE(), 1) - 1  " +
                    "                      AND t.type_id = (SELECT type_id FROM type WHERE type_name = 'IMPORT')  " +
                    "                      THEN td.quantity ELSE 0 END), 0) AS quantity_difference,  " +
                    "    COALESCE(MIN(td.start_quantity), 0) +  " +
                    "    COALESCE(SUM(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'IMPORT')  " +
                    "             THEN td.quantity ELSE 0 END), 0) -  " +
                    "    COALESCE(SUM(CASE WHEN t.type_id = (SELECT type_id FROM type WHERE type_name = 'EXPORT')  " +
                    "             THEN td.quantity ELSE 0 END), 0) AS end_quantity  " +
                    "FROM  " +
                    "    transaction_detail td " +
                    "JOIN  " +
                    "    transaction t ON td.transaction_id = t.transaction_id " +
                    "JOIN  " +
                    "    type ty ON t.type_id = ty.type_id " +
                    "JOIN  " +
                    "    product p ON td.product_id = p.product_id " +
                    "WHERE  " +
                    "    p.quantity <= :quantity " +
                    "GROUP BY  " +
                    "    td.product_id, WEEK(t.created_at, 1) " +
                    "ORDER BY  " +
                    "    WEEK(t.created_at, 1) DESC ", nativeQuery = true)
    List<Object[]> getDataAiTransaction(@Param("quantity") int quantity);

}
