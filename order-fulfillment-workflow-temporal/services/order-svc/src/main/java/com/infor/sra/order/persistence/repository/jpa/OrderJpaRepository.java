package com.infor.sra.order.persistence.repository.jpa;

import com.infor.sra.order.persistence.repository.model.OrderPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderPersistable, Long> {
}
