package com.stripe.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

import com.stripe.payment.model.CustomerEntity;
import com.stripe.payment.model.User;
import com.stripe.payment.repository.CustomerEntityRepository;
import com.stripe.payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    public User createUser(User user)
    {
        User saveUser = userRepository.save(user);

        try{

            Customer stripeCustomer = paymentService.customerCreateParams(saveUser.getEmail(), saveUser.getName());
            CustomerEntity customerEntity=new CustomerEntity();
            customerEntity.setUserId(saveUser.getId());

            customerEntity.setCustomerId(stripeCustomer.getId());
            customerEntity.setPaymentMethodId("");
            customerEntity.setPaymentIntentId("");
            customerEntity.setToken("");
            customerEntity.setName(stripeCustomer.getName());

            customerEntityRepository.save(customerEntity);
        }catch (StripeException e) {
            e.printStackTrace();
        }

        return saveUser;
    }

}
