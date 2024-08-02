package com.stripe.payment.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentRequest {

    private String cardNumber;

    private String expMonth;

    private String expYear;

    private String cvc;
}
