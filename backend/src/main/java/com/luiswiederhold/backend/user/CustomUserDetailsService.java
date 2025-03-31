package com.luiswiederhold.backend.user;

import com.luiswiederhold.backend.BackendApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user by email: " + email);

        User user = userRepository.findByEmail(email);
        if(user == null) {
            logger.error(email + " is not a registered E-Mail");
            throw new UsernameNotFoundException(email + " is not a registered E-Mail");
        }
        logger.info("Success, found user: " + user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }
}
