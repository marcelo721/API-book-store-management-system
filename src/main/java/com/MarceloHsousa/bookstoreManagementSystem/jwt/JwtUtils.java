package com.MarceloHsousa.bookstoreManagementSystem.jwt;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtils {

    private JwtUtils(){
    }

    public static final long  EXPIRE_DAYS = 0;

    public static final long  EXPIRE_HOURS = 12;

    public static final long  EXPIRE_MINUTES = 30;

    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";

    private static Date toExpireDate(Date start){

        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);

        return (Date) Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createToken(User user){

        Date issueAt = new Date();
        Date limit = toExpireDate(issueAt);
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(limit)
                    .sign(algorithm);

            return new JwtToken(token);
        }catch (JWTCreationException e){
            throw new RuntimeException("error generating token", e);
        }
    }

    public static String isValidToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            return "token invalid";
        }
    }
}
