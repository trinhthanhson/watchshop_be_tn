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
                    "LEFT JOIN " +
                    "    transaction_request tr ON rq.request_id = tr.request_id " +
                    "LEFT JOIN " +
                    "    transaction t ON t.request_id = tr.request_id " +
                    "LEFT JOIN " +
                    "    transaction_detail td ON t.transaction_id = td.transaction_id AND td.product_id = rq.product_id " +
                    "JOIN " +
                    "    product p ON rq.product_id = p.product_id " +
                    "WHERE " +
                    "    rq.request_id = :requestId OR tr.request_id IS NULL " +
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

    @Query(value =
            "SELECT " +
                    "    tr.* " +
                    "FROM " +
                    "    transaction_request tr " +
                    "LEFT JOIN " +
                    "    request_detail rd ON tr.request_id = rd.request_id " +
                    "LEFT JOIN " +
                    "    transaction t ON tr.request_id = t.request_id " +
                    "LEFT JOIN " +
                    "    transaction_detail td ON td.transaction_id = t.transaction_id AND td.product_id = rd.product_id " +
                    "WHERE " +
                    "    tr.is_cancel = 'f' AND tr.status = 'ACCEPT' "  + // Chỉ lấy request_id không bị hủy
                    "    AND ( " +
                    "        rd.quantity > COALESCE( " +
                    "            (SELECT SUM(td_sub.quantity) " +
                    "             FROM transaction_detail td_sub " +
                    "             JOIN transaction t_sub ON td_sub.transaction_id = t_sub.transaction_id " +
                    "             WHERE t_sub.request_id = tr.request_id " +
                    "               AND td_sub.product_id = rd.product_id), 0) " +
                    "        OR rd.quantity IS NULL " +
                    "    ) " +
                    "GROUP BY " +
                    "    tr.request_id",
            nativeQuery = true)
    List<Transaction_request> findTransactionRequestsNotFull();

    @Query(value = "SELECT " +
            "    CASE " +
            "        WHEN COUNT(CASE " +
            "                      WHEN rd.quantity > COALESCE( " +
            "                          (SELECT SUM(td_sub.quantity) " +
            "                           FROM transaction_detail td_sub " +
            "                           JOIN transaction t_sub ON td_sub.transaction_id = t_sub.transaction_id " +
            "                           WHERE t_sub.request_id = tr.request_id " +
            "                             AND td_sub.product_id = rd.product_id), 0) " +
            "                          THEN 1 " +
            "                  END) = 0 THEN 'TRUE' " +
            "        ELSE 'FALSE' " +
            "    END AS is_fully_fulfilled " +
            "FROM " +
            "    transaction_request tr " +
            "LEFT JOIN " +
            "    request_detail rd ON tr.request_id = rd.request_id " +
            "WHERE " +
            "    tr.request_id = :request_id", nativeQuery = true)
    String checkQuantityRequest(Long request_id);
}
