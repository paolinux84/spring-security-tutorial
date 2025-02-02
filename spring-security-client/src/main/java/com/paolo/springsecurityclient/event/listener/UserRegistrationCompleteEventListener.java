package com.paolo.springsecurityclient.event.listener;

import com.paolo.springsecurityclient.entity.User;
import com.paolo.springsecurityclient.event.UserRegistrationCompleteEvent;
import com.paolo.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
public class UserRegistrationCompleteEventListener implements ApplicationListener<UserRegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(UserRegistrationCompleteEvent event) {
        // create verification token for the user with link
        User user = event.getUser();
        String token = String.valueOf(UUID.randomUUID());
        userService.saveVerificationTokenForUser(token, user);

        // send mail to user
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;

        log.info("Click link to verify your account: {}", url);
    }
}
