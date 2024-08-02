package com.stripe.payment.controller;

import com.stripe.payment.model.CustomerEntity;
import com.stripe.payment.service.CustomerEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerEntityController {

    @Autowired
    CustomerEntityService customerEntityService;

    @GetMapping("/user/all")
    public List<CustomerEntity> getAllUser()
    {
        return customerEntityService.getAllUser();
    }
}

