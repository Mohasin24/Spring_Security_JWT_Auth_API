package com.authentication.controller;

import com.authentication.constants.AuthStatus;
import com.authentication.dao.AuthService;
import com.authentication.dto.AuthRequestDto;
import com.authentication.dto.AuthResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;

    @Autowired
    public AuthenticationController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto){

        String jwtToken = authService.login(authRequestDto.username(),authRequestDto.password());
        System.out.println(jwtToken);
        AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESS);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponseDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto authRequestDto){
        System.out.println(authRequestDto);
        try {
            String jwtToken = authService.signUp(authRequestDto.name(),authRequestDto.username(),authRequestDto.password());

            AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken,AuthStatus.USER_CREATED_SUCCESSFULLY);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authResponseDto);

        } catch (Exception e) {
            System.out.println(e);
            AuthResponseDto authResponseDto = new AuthResponseDto(null,AuthStatus.USER_NOT_CREATED);

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(authResponseDto);

        }
    }
}
