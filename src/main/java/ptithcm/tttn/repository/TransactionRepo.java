package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
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


}
