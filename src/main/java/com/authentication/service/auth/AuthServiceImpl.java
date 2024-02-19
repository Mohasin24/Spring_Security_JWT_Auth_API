package com.authentication.service.auth;

import com.authentication.dao.AuthService;
import com.authentication.model.User;
import com.authentication.repository.UserRepo;
import com.authentication.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User authenticatedUser = (User) authentication.getPrincipal();

        return JwtUtils.generateJwtToken(authenticatedUser.getUsername());
    }

    @Override
    public String signUp(String name, String username, String password){

        // check if the user already exist in the database
        if(userRepo.existsByUsername(username)){
            throw new RuntimeException("User already exists");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(password);

        // Create authorities
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // build the user
        User user = User.builder()
                .name(name)
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();

        userRepo.save(user);

        return JwtUtils.generateJwtToken(username);
    }
}
