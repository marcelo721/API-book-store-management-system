package com.MarceloHsousa.bookstoreManagementSystem.web.controller;


import com.MarceloHsousa.bookstoreManagementSystem.jwt.JwtToken;
import com.MarceloHsousa.bookstoreManagementSystem.services.AuthenticationService;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserLoginDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/login")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final  AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody UserLoginDto userLoginDto,  HttpServletRequest request){

        try {
            var userAuthenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
            authenticationManager.authenticate(userAuthenticationToken);

            JwtToken token = authenticationService.getTokenAuthenticated(userLoginDto);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e){
            log.warn("Bad credentials");
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Credentials"));
    }
}
