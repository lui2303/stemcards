package com.luiswiederhold.backend.authentication;

import com.luiswiederhold.backend.user.UserPermission;
import com.luiswiederhold.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class Context {
    @Autowired
    private UserRepository userRepository;

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public boolean usernamesMatchOrAdmin(String username) {
        if(Objects.equals(username, getCurrentUsername())) return true;

        return userRepository.findByEmail(username).getPrivilege() == UserPermission.ADMIN;
    }

}
