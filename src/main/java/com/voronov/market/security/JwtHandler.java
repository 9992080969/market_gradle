package com.voronov.market.security;

import com.voronov.market.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

/**
 * @author Alexey Voronov on 20.07.2023
 */
@AllArgsConstructor
public class JwtHandler {
    private final String secret;
    public Mono<VerificationResult> check (String accessToken){
        return Mono.just(verify(accessToken))
                .onErrorResume(e->Mono.error(new UnauthorizedException(e.getMessage())));
    }
    private VerificationResult verify(String token){
        Claims claims = getClaimsFromToken(token);
        final Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())){
            throw new RuntimeException("Token expired");
        }
        return new VerificationResult(claims,token);
    }
    private Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }
    @AllArgsConstructor
    public static class VerificationResult {
        public Claims claims;
        private String token;
    }
}