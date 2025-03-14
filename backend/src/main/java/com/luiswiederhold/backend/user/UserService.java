package com.luiswiederhold.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder;

    public void encodeUserPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

}
