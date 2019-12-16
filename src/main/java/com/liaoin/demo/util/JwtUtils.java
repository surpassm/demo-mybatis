package com.liaoin.demo.util;

import com.liaoin.demo.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * @author Administrator
 */
public class JwtUtils {
    /**
     * 私钥
     */
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 过期时间 默认 秒*分钟*小时
     */
    private static final int EXPIRES_TIME = 60000*60*2;

    public static String getSub(String sub) {
        Date nowDate = new Date();
        //当前系统时间+过期时间
        Date expiresDate = new Date(System.currentTimeMillis()+ EXPIRES_TIME);

        return Jwts.builder()
                .setSubject(sub)
                .signWith(KEY)
                //当前时间
                .setIssuedAt(nowDate)
                //过期时间
                .setExpiration(expiresDate)
                .compact();
    }

    public static String getToken(String jws) throws CustomException{
        try {
            return Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(jws)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |  IllegalArgumentException e) {
            throw new CustomException(201,"登录已过期，请重新登录!");
        } catch (SignatureException s){
            throw new CustomException(201,"无法断言WT有效性，因此不应信任!");
        }
    }
}
