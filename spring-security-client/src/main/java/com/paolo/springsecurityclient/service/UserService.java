package com.paolo.springsecurityclient.service;

import com.paolo.springsecurityclient.entity.User;
import com.paolo.springsecurityclient.model.UserModel;

public interface UserService {


    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String verifyUserRegistrationToken(String token);
}
