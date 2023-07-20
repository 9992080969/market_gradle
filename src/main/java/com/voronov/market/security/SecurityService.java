package com.voronov.market.security;

import com.voronov.market.exception.AuthExceprion;
import com.voronov.market.model.UserEntity;
import com.voronov.market.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @author Alexey Voronov on 19.07.2023
 */
@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expirationInSecret;
    @Value("${jwt.issuer}")
    private String issuer;

    private TokenDetails generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("username", user.getUsername());
        return generateToken(claims, user.getId().toString());
    }

    private TokenDetails generateToken(Map<String, Object> claims, String subject) {
        Long expirationTimeInMillis = expirationInSecret * 1000L;
        Date expirationDate = new Date(new Date().getTime() + expirationTimeInMillis);
        return generateToken(expirationDate, claims, subject);
    }

    private TokenDetails generateToken(Date expirationDate, Map<String, Object> claims, String subject) {
        Date createdDate = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
        return TokenDetails.builder()
                .token(token)
                .issuedAt(createdDate)
                .expiresAt(expirationDate)
                .build();
    }


    public Mono<TokenDetails> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    if (!user.isEnabled()) {
                        return Mono.error(new AuthExceprion("Account disabled", "PROSELYTE_USER_ACCOUNT_DISABLED"));
                    }
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.error(new AuthExceprion("Invalid password","PROSELYTE_INVALID_PASSWORD"));
                    }
                    return Mono.just(generateToken(user).toBuilder().userId(user.getId())
                            .build());
                })
                .switchIfEmpty(Mono.error(new AuthExceprion("Invalid username","PROSELYTE_INVALID_USERNAME")));
    }
}