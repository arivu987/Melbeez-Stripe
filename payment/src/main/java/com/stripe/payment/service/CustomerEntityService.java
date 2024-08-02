package com.stripe.payment.service;


import com.stripe.payment.model.CustomerEntity;
import com.stripe.payment.repository.CustomerEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerEntityService {

    @Autowired
    private CustomerEntityRepository customerEntityRepository;


    public void updateCustomer(String customerId, String paymentMethodId, String token) {
        customerEntityRepository.findByCustomerId(customerId).map(existingCustomer -> {
            if (existingCustomer.getPaymentMethodId().isEmpty()) {
                existingCustomer.setPaymentMethodId(paymentMethodId);
            }
            if (existingCustomer.getToken().isEmpty()) {
                existingCustomer.setToken(token);
            }
            return customerEntityRepository.save(existingCustomer);
        });
    }

    public void updatePaymentIntent(String customerId, String paymentIntentId) {
        customerEntityRepository.findByCustomerId(customerId).map(existingCustomer -> {
            existingCustomer.setPaymentIntentId(paymentIntentId);
            return customerEntityRepository.save(existingCustomer);
        }).orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerId + " not found"));
        ;
    }

    public List<CustomerEntity> getAllUser()
    {
        return customerEntityRepository.findAll();
    }
}
