package com.ecjtu.ferry.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    //根据用户手机生成token,过期时间5小时
    public static String getToken(String phone){
        //过期时间
        Date date = new Date(System.currentTimeMillis()+3600*1000*5);
        String token = Jwts.builder()
                .setSubject(phone)
                .signWith(SignatureAlgorithm.HS512, "123456")
                .setExpiration(date)
                .compact();
        return token;
    }

    //根据token解析用户手机
    public static String parseToken(String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey("123456")
                    .parseClaimsJws(token)
                    .getBody();
            String phone = claims.getSubject();
            return phone;
        }catch (Exception e){
            return null;
        }
    }
}
