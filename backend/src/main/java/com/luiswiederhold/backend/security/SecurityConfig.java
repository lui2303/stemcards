package com.luiswiederhold.backend.security;

import com.luiswiederhold.backend.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private String PRIVATEKEY = "TODO: Load private key from env variables";
    @Autowired
    private UserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/register", "/login", "/flashcards/create") // change for production use!
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .rememberMe((remember) -> remember
                    .rememberMeServices(rememberMeServices()));

        return http.build();
    }

    @Bean
    TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices(PRIVATEKEY, customUserDetailsService);
    }

    @Bean
    RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(PRIVATEKEY);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(daoAuthenticationProvider(), rememberMeAuthenticationProvider()));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

}
