package com.luiswiederhold.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.print("Loading username " + username);
        User user = userRepository.findByEmail(username);
        if(user == null) throw new UsernameNotFoundException(username + " is not a registered E-Mail");
        //return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, null);
        return new org.springframework.security.core.userdetails.User("admin", "pwd", true, true, true, true, null);

    }
}
