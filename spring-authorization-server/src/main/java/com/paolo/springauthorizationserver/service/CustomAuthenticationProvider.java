package com.paolo.springauthorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Values passed in input (from where???)
        String username = authentication.getName();
        String rawPassword = String.valueOf(authentication.getCredentials());

        // Validating the user
        UserDetails customerUserDetails = customUserDetailsService.loadUserByUsername(username);
        return checkPassword(customerUserDetails, rawPassword);
    }

    private Authentication checkPassword(UserDetails customerUserDetails, String rawPassword) {
        boolean isPasswordMatching = passwordEncoder.matches(rawPassword, customerUserDetails.getPassword());

        if (isPasswordMatching) {
            return new UsernamePasswordAuthenticationToken(
                    customerUserDetails.getUsername(),
                    customerUserDetails.getPassword(),
                    customerUserDetails.getAuthorities());
        } else
            throw new BadCredentialsException("Bad Credentials");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
