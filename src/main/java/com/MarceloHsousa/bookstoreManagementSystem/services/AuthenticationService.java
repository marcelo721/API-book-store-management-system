package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.jwt.JwtToken;
import com.MarceloHsousa.bookstoreManagementSystem.jwt.JwtUtils;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByEmail(username);

    }

    public JwtToken getTokenAuthenticated(UserLoginDto userLoginDto){
        User user = userService.findByEmail(userLoginDto.getEmail());
        return JwtUtils.createToken(user);
    }
}
