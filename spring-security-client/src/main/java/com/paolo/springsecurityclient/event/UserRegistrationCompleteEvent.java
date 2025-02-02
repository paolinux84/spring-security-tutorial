package com.paolo.springsecurityclient.event;

import com.paolo.springsecurityclient.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserRegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final String applicationUrl;

    public UserRegistrationCompleteEvent(Object source, User user, String applicationUrl) {
        super(source);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
