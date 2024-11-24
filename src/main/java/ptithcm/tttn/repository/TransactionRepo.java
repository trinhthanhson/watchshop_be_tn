package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Transaction;

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
}
