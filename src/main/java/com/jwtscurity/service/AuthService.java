package com.jwtscurity.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
public interface AuthService {

   String login(String username, String password);

   String signUp(String name, String username, String password);
}
