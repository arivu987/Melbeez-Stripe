package com.stripe.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint")
    private Long id;

    private String name;

    private String customerId;

    private String paymentMethodId;

    private Long userId;

    private String token;

    private String paymentIntentId;
}
