package com.infor.sra.order.service;

import com.infor.sra.common.service.CompleteOrderService;
import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.order.application.domain.model.Order;
import com.infor.sra.order.application.domain.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompleteOrderServiceImpl implements CompleteOrderService {

    @Autowired
    private OrderService orderRepository;

    @Override
    public void completeOrder(OrderDTO orderDTO) {
        log.info("Marking order as completed, order id {}", orderDTO.getOrderId());
        var order = map(orderDTO);
        order.setOrderStatus(OrderStatus.COMPLETED);
        var completedOrder = orderRepository.save(order);
        log.info("Order completed, {}", completedOrder);
    }

    private Order map(OrderDTO orderDTO) {
        var order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setProductId(orderDTO.getProductId());
        order.setPrice(orderDTO.getPrice());
        order.setQuantity(orderDTO.getQuantity());
        return order;
    }
}
