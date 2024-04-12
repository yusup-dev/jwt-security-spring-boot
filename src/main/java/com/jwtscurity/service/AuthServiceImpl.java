package com.jwtscurity.service;


import com.jwtscurity.model.User;
import com.jwtscurity.repo.UserRepository;
import com.jwtscurity.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public String login(String username, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(username, password);

        var authentication = authenticationManager.authenticate(authToken);

        return JwtUtils.generateToken(((UserDetails)(authentication.getPrincipal())).getUsername());
    }

    @Override
    public String signUp(String name, String username, String password) {
        // Check whether user already exists or not
        if(userRepository.existsByUsername(username)){
            throw new RuntimeException("User already exists");
        }

        // Encode password
        var encodePassword = passwordEncoder.encode(password);

        // Create App user
        var user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(encodePassword);

        // Save user
        userRepository.save(user);

        // Generate Token
        return JwtUtils.generateToken(username);


    }
}
