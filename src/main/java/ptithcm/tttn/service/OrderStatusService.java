package ptithcm.tttn.service;


import ptithcm.tttn.entity.OrderStatus;

import java.util.List;

public interface OrderStatusService {

    List<OrderStatus> findAll();

    OrderStatus createStatus(OrderStatus status, String jwt) throws Exception;

    List<OrderStatus> updateStatus( List<OrderStatus> statusList, String jwt) throws Exception;

    void deleteStatus(Long id) throws Exception;
}
