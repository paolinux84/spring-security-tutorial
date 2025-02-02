package com.paolo.springsecurityclient.controller;

import com.paolo.springsecurityclient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    UserRepository repository;

    @GetMapping("/api/hello")
    public String hello() {
        return "hello!";
    }
}
