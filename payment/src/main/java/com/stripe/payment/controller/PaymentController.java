package com.stripe.payment.controller;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.payment.Dto.CustomerDTO;
import com.stripe.payment.service.CustomerEntityService;
import com.stripe.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerEntityService customerEntityService;

    @PostMapping("/create-payment-intent")
    public Map<String, String> createPaymentIntent(@RequestParam Long amount, @RequestParam String currency, @RequestParam String paymentMethodId, @RequestParam String customerId) throws StripeException {
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount, currency, paymentMethodId, customerId);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("client_secret", paymentIntent.getClientSecret());
        customerEntityService.updatePaymentIntent(customerId,paymentIntent.getId());
        return responseData;
    }

    @GetMapping("/retrieve-customer/{customerId}")
    public ResponseEntity<CustomerDTO> retrieveCustomer(@PathVariable("customerId") String customerId) throws StripeException {
        Customer customer = Customer.retrieve(customerId);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setName(customer.getName());
        customerDTO.setDefaultPaymentMethod(customer.getInvoiceSettings().getDefaultPaymentMethod());
        customerDTO.setInvoicePrefix(customer.getInvoicePrefix());
        customerDTO.setNextInvoiceSequence(customer.getNextInvoiceSequence());
        customerDTO.setDelinquent(customer.getDelinquent());
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }


    //Create paymentMethod using token
    @PostMapping("/create-payment-method")
    public Map<String, String> createPaymentMethod(@RequestBody Map<String,String> request) {
        Map<String, String> response = new HashMap<>();
        try {

            String token = request.get("token");

            if (token == null || token.isEmpty()) {
                response.put("error", "Token is required");
                return response;
            }

            PaymentMethodCreateParams params = PaymentMethodCreateParams.builder()
                    .setType(PaymentMethodCreateParams.Type.CARD)
                    .setCard(PaymentMethodCreateParams.Token.builder()
                            .setToken(token)
                            .build())
                    .build();

            PaymentMethod paymentMethod = PaymentMethod.create(params);
            response.put("paymentMethodId", paymentMethod.getId());
        } catch (StripeException e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/attach-pm-to-customer")
    public String attachPaymentMethodToCustomer(@RequestParam String paymentMethodId, @RequestParam String customerId, @RequestParam String token) throws StripeException {
        paymentService.attachPaymentMethodToCustomer(paymentMethodId, customerId, token);
        return "attached successfully";
    }

    @PostMapping("/confirm-payment-intent")
    public String confirmPayIntent(@RequestParam String paymentIntentId) throws StripeException
    {
        paymentService.confirmPaymentIntent(paymentIntentId);
        return "Payment Successfully";
    }

    @PostMapping("/cancel-payment-intent/{id}")
    public Map<String, String> cancelPaymentIntent(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.cancelPaymentIntent(id);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", paymentIntent.getStatus());
        return responseData;
    }



}
