package com.infor.sra.order.service;

import com.infor.sra.order.persistence.repository.jpa.OrderJpaRepository;
import com.infor.sra.common.error.ResourceNotFoundException;
import com.infor.sra.order.application.domain.model.Order;
import com.infor.sra.order.persistence.mapper.OrderPersistableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Transactional
    public Order save(Order order) {
        log.info("Saving Order");
        var orderPersistable = OrderPersistableMapper.MAPPER.map(order);
        orderPersistable = orderJpaRepository.save(orderPersistable);
        log.info("Order saved, order {}", orderPersistable);
        return OrderPersistableMapper.MAPPER.map(orderPersistable);
    }


    @Transactional
    public Order get(Long orderId) {
        log.info("Fetching order for id {}", orderId);
        var orderPersistable =
                orderJpaRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        log.info("Fetched order, {}", orderPersistable);
        return OrderPersistableMapper.MAPPER.map(orderPersistable);
    }
}
