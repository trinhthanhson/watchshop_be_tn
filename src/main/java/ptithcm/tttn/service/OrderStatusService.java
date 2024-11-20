package ptithcm.tttn.service;


import ptithcm.tttn.entity.OrderStatus;

import java.util.List;

public interface OrderStatusService {

    List<OrderStatus> findAll();

    OrderStatus createStatus(OrderStatus status, String jwt) throws Exception;

    OrderStatus updateStatus(Long id, OrderStatus status, String jwt) throws Exception;
}
