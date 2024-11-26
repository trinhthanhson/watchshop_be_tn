package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Request_detail;
import ptithcm.tttn.entity.Transaction_request;

import java.util.List;

@Repository
public interface TransactionRequestRepo extends JpaRepository<Transaction_request, Long> {
    @Query(value =
            "SELECT " +
                    "    rq.request_id AS requestId, " +
                    "    rq.product_id AS productId, " +
                    "    rq.price AS price, " +
                    "    rq.note AS note, " +
                    "    p.quantity AS productQuantity, " +
                    "    p.product_name AS productName, " +
                    "    rq.quantity AS quantityRequest, " +
                    "    IFNULL(SUM(td.quantity), 0) AS totalTransactedQuantity, " +
                    "    (rq.quantity - IFNULL(SUM(td.quantity), 0)) AS remainingQuantity " +
                    "FROM " +
                    "    request_detail rq " +
                    "JOIN " +
                    "    transaction_request tr ON rq.request_id = tr.request_id " +
                    "JOIN " +
                    "    transaction t ON t.request_id = tr.request_id " +
                    "LEFT JOIN " +
                    "    transaction_detail td ON t.transaction_id = td.transaction_id AND td.product_id = rq.product_id " +
                    "JOIN " +
                    "    product p ON rq.product_id = p.product_id " +
                    "WHERE " +
                    "    rq.request_id = :requestId " +
                    "GROUP BY " +
                    "    rq.request_id, " +
                    "    rq.product_id, " +
                    "    rq.quantity, " +
                    "    p.quantity, " +
                    "    rq.note, " +
                    "    rq.price, " +
                    "    p.product_name " +
                    "HAVING " +
                    "    remainingQuantity > 0",
            nativeQuery = true)
    List<Object[]> findUnfulfilledRequestDetails(@Param("requestId") Long requestId);
}
