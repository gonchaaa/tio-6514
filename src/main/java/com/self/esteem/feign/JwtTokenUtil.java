package com.self.esteem.feign;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

   private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);
   }


    public Long extractUserId(String authHeader) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(extractUserToken(authHeader))
                .getBody();

        Object userIdObj = claims.get("userId");

        if (userIdObj == null) {
           throw new RuntimeException("Token-də userId tapılmadı.");
        }
        if (userIdObj instanceof Integer) {
            return ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
           return (Long) userIdObj;
        } else if (userIdObj instanceof String) {
            return Long.parseLong((String) userIdObj);
        } else {
          throw new RuntimeException("Token-də userId tipi tanınmadı.");
       }
    }
    public String extractUserEmail(String authHeader) {


        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(extractUserToken(authHeader))
                .getBody();
        System.out.println(claims.getSubject());
        System.out.println(claims.get("sub"));
        return claims.getSubject();
    }

    public String extractUserToken(String authHeader) {
        return authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
    }

}
