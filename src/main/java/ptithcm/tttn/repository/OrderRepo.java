package ptithcm.tttn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.entity.Transaction_request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT * FROM orders WHERE customer_id = ?1  ",nativeQuery = true)
    List<Orders> findByCustomerId(Long customer_id);

    @Query(value = "SELECT * FROM orders WHERE customer_id = ?1  ",
            countQuery = "SELECT * FROM orders WHERE customer_id = ?1",nativeQuery = true)
    Page<Orders> findByCustomerIdPage(Long customer_id, Pageable pageable);
    @Query(value = "SELECT * FROM orders " +
            "WHERE customer_id = ?1 " +
            "AND ((" +
            "(?2 IS NOT NULL AND status_id = ?2 AND is_cancel = 'f') " + // Nếu status_id có giá trị và is_cancel = 'f'
            ") OR (" +
            "(?2 IS NULL AND is_cancel = ?3) " + // Nếu status_id là null, tìm theo is_cancel
            "))",
            nativeQuery = true)
    Page<Orders> findOrderByCustomerAndStatusOrCancel(Long customer_id, Long status_id, String is_cancel, Pageable pageable);
    @Query(value = "SELECT * FROM orders o WHERE DATE(o.created_at) >= DATE(:startDate) AND DATE(o.created_at) <= DATE(:endDate)",
            countQuery = "SELECT COUNT(*) FROM orders o WHERE DATE(o.created_at) >= DATE(:startDate) AND DATE(o.created_at) <= DATE(:endDate)",
            nativeQuery = true)
    Page<Orders> findOrdersByDateRange(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE DATE(o.created_at) >= DATE(:startDate) AND DATE(o.created_at) <= DATE(:endDate)  AND o.status_id = :status_id ",
            countQuery = "SELECT COUNT(*) FROM orders o WHERE DATE(o.created_at) >= DATE(:startDate) AND DATE(o.created_at) <= DATE(:endDate) AND o.status_id = :status_id",
            nativeQuery = true)
    Page<Orders> findOrdersByDateAndStatus(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate, @Param("status_id") Long status_id,
                                       Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE status_id = :status_id ",
            countQuery = "SELECT COUNT(*) FROM orders WHERE status_id = :status_id",
            nativeQuery = true)
    Page<Orders> searchOrderByStatusId(@Param("status_id") Long status_id, Pageable pageable);

    @Query(value =  "SELECT * FROM orders WHERE customer_id = :customerId AND (DATE(created_at) >= DATE(:startDate) AND DATE(created_at) <= DATE(:endDate))",nativeQuery = true)
    Page<Orders> searchOrderByDatePage(@Param("customerId") Long customerId,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE " +
            "(:startDate IS NULL OR o.created_at >= :startDate) AND " +
            "(:endDate IS NULL OR o.created_at <= :endDate) AND " +
            "(:statusId IS NULL OR o.status_id = :statusId) AND " +
            "(:recipientName IS NULL OR o.recipient_name LIKE CONCAT('%', :recipientName, '%')) AND " +
            "(:recipientPhone IS NULL OR o.recipient_phone LIKE CONCAT('%', :recipientPhone, '%'))",nativeQuery = true)
    Page<Orders> searchOrdersCustomer(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("statusId") Long statusId,
            @Param("recipientName") String recipientName,
            @Param("recipientPhone") String recipientPhone,
            Pageable pageable
    );

    @Query("SELECT MONTH(t.created_at) AS month, SUM(t.total_price) AS totalAmount " +
            "FROM Transaction t " +
            "JOIN Type tp ON tp.type_id = t.type_id " +
            "WHERE YEAR(t.created_at) = :year  " +
            "GROUP BY MONTH(t.created_at)")
    List<Object[]> getTotalAmountByMonth(int year);

    @Query("SELECT " +
            "    p.product_id AS product_id, " +
            "    p.product_name AS product_name, " +
            "    SUM(od.quantity * od.price) AS total_sold,  " +
            "	 SUM(od.quantity) AS total_quantity,	 " +
            "    DATE(o.updated_at) as date_pay " +
            "FROM " +
            "    Product p " +
            "JOIN " +
            "    Order_detail od ON p.product_id = od.product_order.product_id " +
            "JOIN " +
            "    Orders o ON od.orders.order_id = o.order_id " +
            "WHERE " +
            "    DATE(o.created_at) >= DATE(:startDate) " +
            "    AND DATE(o.updated_at) <= DATE(:endDate) " +
            "GROUP BY " +
            "    p.product_id, p.product_name,o.updated_at " +
            "ORDER BY " +
            "    total_sold DESC")
    List<Object[]> getTotalAmountByDate(Date startDate, Date endDate);

    @Query("select sum(o.total_price) as total_price from Orders o ")
    List<Object[]> findTotalPriceByStatus();

    @Query(value = "SELECT " +
            "CASE " +
            "WHEN EXISTS ( " +
            "    SELECT 1 " +
            "    FROM orders od " +
            "    JOIN transaction_request tr ON tr.order_id = od.order_id " +
            "    JOIN transaction t ON tr.request_id = t.request_id " +
            "    WHERE od.order_id = :orderId " +
            ") THEN 'True' " +  // Trả về 'True' nếu tồn tại
            "ELSE 'False' " +    // Trả về 'False' nếu không tồn tại
            "END AS is_transaction_created",
            nativeQuery = true)
    String isTransactionCreated(@Param("orderId") Long orderId);

    @Query(value = "SELECT tr.* " +
            "FROM transaction_request tr " +
            "JOIN type t ON tr.type_id = t.type_id " +
            "WHERE t.type_name = :typeName ",
            countQuery = "SELECT COUNT(tr.request_id) " +
                    "FROM transaction_request tr " +
                    "JOIN type t ON tr.type_id = t.type_id " +
                    "WHERE t.type_name = :typeName",
            nativeQuery = true)
    Page<Transaction_request> getAllTransactionRequestByType(@Param("typeName") String typeName, Pageable pageable);

    @Query(value = "SELECT * FROM transaction t " +
            "JOIN transaction_request tr ON t.request_id = tr.request_id " +
            "JOIN orders od ON tr.order_id = od.order_id " +
            "WHERE od.is_delivery = 't' and od.staff_id is null",
            countQuery = "SELECT COUNT(*) FROM transaction t " +
                    "JOIN transaction_request tr ON t.request_id = tr.request_id " +
                    "JOIN orders od ON tr.order_id = od.order_id " +
                    "WHERE od.is_delivery = 't' and od.staff_id is null",
            nativeQuery = true)
    Page<Orders> getAllOrderDelivery(Pageable pageable);

    @Query(value = "SELECT * FROM transaction t " +
            "JOIN transaction_request tr ON t.request_id = tr.request_id " +
            "JOIN orders od ON tr.order_id = od.order_id " +
            "WHERE od.is_delivery = 't' and od.staff_id = :staff_id",
            countQuery = "SELECT COUNT(*) FROM transaction t " +
                    "JOIN transaction_request tr ON t.request_id = tr.request_id " +
                    "JOIN orders od ON tr.order_id = od.order_id " +
                    "WHERE od.is_delivery = 't' and od.staff_id = :staff_id",
            nativeQuery = true)
    Page<Orders> getAllOrderDeliveryByStaff(@Param("staff_id") Long staff_id,Pageable pageable);

}
