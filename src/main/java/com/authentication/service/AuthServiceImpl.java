package com.authentication.service;

import com.authentication.repository.UserRepo;
import com.authentication.dao.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepo userRepo,AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public String signUp(String name, String username, String password){

        if(userRepo.existsByUsername(username)){
            throw new RuntimeException("User already exists");
        }

        return null;
    }
}
