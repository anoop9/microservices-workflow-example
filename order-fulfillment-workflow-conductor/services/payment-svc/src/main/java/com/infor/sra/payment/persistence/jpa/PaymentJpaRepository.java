package com.infor.sra.payment.persistence.jpa;

import com.infor.sra.payment.persistence.model.PaymentPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentPersistable, Long> {
}
