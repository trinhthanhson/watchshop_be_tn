package ptithcm.tttn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.request.StatisticRequest;
import ptithcm.tttn.request.UpdateStatusRequest;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Orders orderBuyNow(OrderRequest rq, String jwt) throws Exception;

    List<Orders> findByJwtCustomer(String jwt) throws Exception;

    Orders findById(Long id) throws Exception;

    Orders updateStatus(UpdateStatusRequest rq, Long id) throws Exception;
    
    Orders updateStatusPayment(String status, Long id) throws Exception;

    Orders orderBuyCart(OrderRequest rq,String jwt) throws Exception;

    List<StatisticRequest> getTotalAmountByMonth(int year);

    List<Orders> findAll();

    Page<Orders> findPageAll(Pageable pageable);

    List<ProductSaleRequest> getTotalAmountByDate(Date start, Date end);

    Orders updateStatusOrderByStaff(UpdateStatusRequest rq, Long id, String jwt) throws Exception;

    Orders orderPaymentBuyNow(OrderRequest rq, String jwt) throws Exception;

    Orders orderPaymentBuyCart(OrderRequest rq, String jwt) throws Exception;

    List<Orders> allOrderReceiveByStaff(String jwt) throws Exception;

    List<StatisticRequest>  getTotalPriceByStatus();

    String isTransactionCreated(Long orderId);

    Orders paymentSuccess(Long id, Boolean isPayment) throws Exception;

    Page<Orders> getAllOrderDelivery(Pageable pageable);

    Orders updateOrderShipper(Long id, String jwt,UpdateStatusRequest od) throws Exception;

}
