package com.stripe.payment.repository;

import com.stripe.payment.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity,Long> {
    Optional<CustomerEntity> findByCustomerId(String customerId);
}
