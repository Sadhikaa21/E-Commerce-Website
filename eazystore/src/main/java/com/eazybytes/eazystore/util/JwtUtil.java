package com.eazybytes.eazystore.util;

import com.eazybytes.eazystore.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;
//    this line creates an object of all environment variables created in the application
    public String generateJwtToken(Authentication authentication){
        String jwt = "";
        String secret = env.getProperty(com.eazybytes.eazystore.constants.ApplicationConstants.JWT_SECRET_KEY,
                com.eazybytes.eazystore.constants.ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
//        we retrieve the values and they are converted into bytes by using utf8 standard,given as input to hmacshakey(to hash) and stored in secretkey
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Customer fetchedCustomer = (Customer) authentication.getPrincipal();
        //        we invoke getprincipal using authentication object,so when authentication is completed,it is going to populate the details into principal,and then perform casting from Object to User class and store in fetcheduser
        jwt = Jwts.builder().issuer("Eazy Store").subject("JWT Token")
//                we name the issuer of the token..company name or smth
                .claim("username", ((Customer) fetchedCustomer).getName())
//                setting key,value of username (name user entered while logging in)
                .claim("email", fetchedCustomer.getEmail())
                .claim("mobileNumber", fetchedCustomer.getMobileNumber())
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date((new java.util.Date()).getTime() + 60 * 60 * 1000))
                .signWith(secretKey).compact();
//        jwt token is generated
        return jwt;
          }
}
