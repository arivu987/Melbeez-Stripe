package com.stripe.payment.controller;

import com.stripe.payment.Dto.Token;

import com.stripe.payment.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping("/token")
    public Token createToken(@RequestBody Token token) {
        return tokenService.createToken(token);
    }
}
