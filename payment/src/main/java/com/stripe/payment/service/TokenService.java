package com.stripe.payment.service;

import com.stripe.payment.Dto.Token;
import com.stripe.payment.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;
    public Token createToken(Token token) {
        return tokenRepository.save(token);
    }
}
