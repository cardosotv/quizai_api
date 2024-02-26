package com.cardosotv.quizai.security;

import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;


public class JWTUtil {
    private static final long EXPIRATION_TIME = 900000; // 1 day in milliseconds 86400000
    private static final SecretKey key = Jwts.SIG.HS512.key().build();

    @Deprecated
    public static String generateToken(String userID) {
    // https://github.com/jwtk/jjwt#jws-create-key

        return Jwts.builder()
            .subject(userID)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
    }

    public static String getUserIdFromToken(String token) {
        String result = Jwts.parser()
                            .verifyWith(key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload()
                            .getSubject();
        return result;
    }
}
