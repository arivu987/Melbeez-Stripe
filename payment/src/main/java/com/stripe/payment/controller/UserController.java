package com.stripe.payment.controller;
import com.stripe.payment.model.User;
import com.stripe.payment.service.CustomerEntityService;
import com.stripe.payment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    CustomerEntityService customerEntityService;


    @PostMapping("/user")
    public String createUser(@RequestBody User user)
    {
        userService.createUser(user);
        return "user created";
    }
}
