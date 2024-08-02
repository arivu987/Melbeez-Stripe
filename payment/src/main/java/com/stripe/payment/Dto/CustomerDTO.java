package com.stripe.payment.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private String id;
    private String email;
    private String name;
    private String defaultPaymentMethod;
    private String invoicePrefix;
    private Long nextInvoiceSequence;
    private boolean delinquent;
}
