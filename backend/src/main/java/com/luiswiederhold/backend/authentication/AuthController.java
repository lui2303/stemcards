package com.luiswiederhold.backend.authentication;


import com.luiswiederhold.backend.user.User;
import com.luiswiederhold.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        if(userRepository.existsByEmail(user.getEmail())) return null;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // login via postman usually requires csrf token, which I disabled for development purposes
}
