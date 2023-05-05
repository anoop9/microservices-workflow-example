package com.infor.sra.order.persistence.repository.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_order")
public class OrderPersistable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;
    private String orderStatus;
    private Double price;
    private Double quantity;
}
