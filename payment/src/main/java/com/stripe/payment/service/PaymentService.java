package com.stripe.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;


import com.stripe.model.PaymentMethod;
import com.stripe.param.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {



    @Autowired
    CustomerEntityService customerEntityService;


    public Customer customerCreateParams(String email,String name) throws StripeException {
        CustomerCreateParams params=CustomerCreateParams.builder().setEmail(email).setName(name).build();

        Customer customer=Customer.create(params);

        return customer;
    }

    public void attachPaymentMethodToCustomer(String paymentMethodId, String customerId, String token) throws StripeException {
        PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
        paymentMethod.attach(PaymentMethodAttachParams.builder().setCustomer(customerId).build());

        Customer customer = Customer.retrieve(customerId);
        customer.update(CustomerUpdateParams.builder().setInvoiceSettings(
                CustomerUpdateParams.InvoiceSettings.builder().setDefaultPaymentMethod(paymentMethodId).build()).build());

        customerEntityService.updateCustomer(customerId,paymentMethodId,token);
    }


    public PaymentIntent createPaymentIntent(Long amount, String currency, String paymentMethodId, String customerId) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency(currency)
                        .addPaymentMethodType("card")
                        .setPaymentMethod(paymentMethodId)
                        .setCustomer(customerId)
                        .setConfirm(false)
                        .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.AUTOMATIC)
                        .build();

        return PaymentIntent.create(params);
    }

    public void confirmPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        PaymentIntentConfirmParams params = PaymentIntentConfirmParams.builder()
                .setPaymentMethod(paymentIntent.getPaymentMethod())
                .build();

        paymentIntent.confirm(params);
    }

    public PaymentIntent cancelPaymentIntent(String id) throws  StripeException{
        PaymentIntent resource=PaymentIntent.retrieve(id);
        PaymentIntentCancelParams params=PaymentIntentCancelParams.builder().build();
        PaymentIntent paymentIntent= resource.cancel(params);
        return paymentIntent;
    }

}