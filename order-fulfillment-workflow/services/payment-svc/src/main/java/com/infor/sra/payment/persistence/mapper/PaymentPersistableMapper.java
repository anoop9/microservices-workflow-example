package com.infor.sra.payment.persistence.mapper;

import com.infor.sra.payment.persistence.model.PaymentPersistable;
import com.infor.sra.payment.application.domain.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentPersistableMapper {

    public static final PaymentPersistableMapper MAPPER =
            Mappers.getMapper(PaymentPersistableMapper.class);

    @Mapping(source = "paymentId", target = "id")
    public abstract PaymentPersistable map(Payment payment);

    @Mapping(source = "id", target = "paymentId")
    public abstract Payment map(PaymentPersistable paymentPersistable);
}
