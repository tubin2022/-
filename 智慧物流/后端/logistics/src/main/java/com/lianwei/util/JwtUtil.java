package com.lianwei.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Jwt工具类
 */
@Component
public class JwtUtil {
    static final String USER_NAME = "usernmae";
    static final String TOKEN_SUB = "uuid";
    private String secret = "logistics2022";
    private Long validity = 1800000L;

    /**
     * 生成token
     * @param username
     * @return  token
     */
    public String generateToken(String username){
        Map<String,Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");

        //签发时间
        long created = System.currentTimeMillis();
        Date issued = new Date(created);

        //过期时间
        long expires = System.currentTimeMillis() + validity;
        Date expiration = new Date(expires);

        Map<String,Object> cliams = new HashMap<>();
        cliams.put(TOKEN_SUB, UUID.randomUUID());
        cliams.put(USER_NAME,username);
        return Jwts.builder()
                .setHeader(header)
                .setClaims(cliams)
                .setIssuedAt(issued)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 解析token
     * @param token
     * @return 身份信息
     */
    public Claims parseToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return token是否过期
     */
    public boolean isExpires(String token){
        if(token == null) return true;
        if(token != null){
            try {
                Claims claims = parseToken(token);
            }catch (Exception e){
                return true;
            }
        }
        return false;
    }

    /**
     * 从token中解析出用户登录账号
     * @param token
     * @return 用户登录账号
     */
    public String getUsername(String token){
        Claims claims = parseToken(token);
        String username = (String) claims.get(USER_NAME);
        return username;
    }

}
