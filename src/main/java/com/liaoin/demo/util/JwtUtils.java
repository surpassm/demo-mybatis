package com.liaoin.demo.util;

import com.liaoin.demo.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * @author Administrator
 */
public class JwtUtils {
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateWithSub(String sub) {
        return Jwts.builder().setSubject(sub).signWith(KEY).compact();
    }

    public static String getSubFromToken(String jws) throws CustomException{
        try {
            return Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(jws)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |  IllegalArgumentException e) {
            throw new CustomException(201,"解析错误");
        }
    }
}
