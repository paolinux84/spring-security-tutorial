package com.paolo.springsecurityclient.service;

import com.paolo.springsecurityclient.entity.User;
import com.paolo.springsecurityclient.entity.VerificationToken;
import com.paolo.springsecurityclient.model.UserModel;
import com.paolo.springsecurityclient.repository.UserRepository;
import com.paolo.springsecurityclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel) {

        String encodedPassword = passwordEncoder.encode(userModel.getPassword());
        User newUser = User.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .password(encodedPassword)
                .role("USER")
                .build();


        return userRepository.save(newUser);
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String verifyUserRegistrationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null)
            return "invalid";

        Long deltaTime = verificationToken.getExpirationTime().getTime() - Calendar.getInstance().getTimeInMillis();
        if (deltaTime <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
