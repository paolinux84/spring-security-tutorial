package com.paolo.springsecurityclient.controller;

import com.paolo.springsecurityclient.entity.User;
import com.paolo.springsecurityclient.event.UserRegistrationCompleteEvent;
import com.paolo.springsecurityclient.model.UserModel;
import com.paolo.springsecurityclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest httpServletRequest) {
        User newUser = userService.registerUser(userModel);
        applicationEventPublisher.publishEvent(new UserRegistrationCompleteEvent(
                this,
                newUser,
                applicationUrl(httpServletRequest)));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistrationToken(@RequestParam(name = "token") String token) {
        String result = userService.verifyUserRegistrationToken(token);

        if ("valid".equalsIgnoreCase(result))
            return "User verified successfully!";
        else
            return "Bad User!";

    }

    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://" +
                httpServletRequest.getServerName() +
                ":" +
                httpServletRequest.getServerPort() +
                httpServletRequest.getContextPath();
    }

}
