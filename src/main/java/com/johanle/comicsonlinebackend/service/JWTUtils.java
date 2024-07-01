package com.johanle.comicsonlinebackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JWTUtils {

    private final SecretKey key;

    private static final long EXPIRATION_TiME = 86400000L; //24Hours by miliseconds

    public JWTUtils() {
        String secretString = "sdsjhe84j86cx4qtor7pkxn5qla92cv3xfskfte89ufbitadqdub20kfqj013ci7u4bhxnsq4f8xa0qpk5krmwq5l596i5u1qwocbk40d5f";
        byte[] keyBytes = Base64.getDecoder().decode(secretString);
//        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println(key);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TiME))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Objects> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TiME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}
