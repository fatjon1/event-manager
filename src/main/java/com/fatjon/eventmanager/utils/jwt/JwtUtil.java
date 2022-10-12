package com.fatjon.eventmanager.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtUtil {
    private final String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //generateToken call createToken method and pass a map of claims and the username of the user that authenticate
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // here we can pass roles or authorities
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder() // call Jwt api
                .setClaims(claims)// set the claims that we passed in
                .setSubject(subject) // set the authenticated user (principal)
                .setIssuedAt(new Date(System.currentTimeMillis())) // set the creation date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //set the expiration date
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // sing with algorithm and pas the secret key
                .compact(); //
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //Todo: is passed a not valid jwt adding an extra char the app goes in infinit loop
    // io.jsonwebtoken.MalformedJwtException: Unable to read JSON value: �쉅����!L��؉
    // in postman get the following error
    // Error: Exceeded maxRedirects. Probably stuck in a redirect loop http://localhost:8080/login
}
