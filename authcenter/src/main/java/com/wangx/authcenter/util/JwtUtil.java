package com.wangx.authcenter.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Component
public class JwtUtil {

    private final String SECRET = "qazwsx123444$#%#()*&& asdaswwi1235 ?;!@#kmmmpom in***xx**&";
    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER_AUTH = "Authorization";

    /**
     * 生成jwt
     */
    public Map<String, Object> generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>(6);
        Map<String, Object> resultMap = new HashMap<>(6);
        map.put("id", new Random().nextInt());
        map.put("userId", userId);
        String jwt = Jwts.builder()
                .setSubject("user info").setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        String token = TOKEN_PREFIX + " " + jwt;
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
        resultMap.put("token", token);
        resultMap.put("refreshToken", refreshToken);
        return resultMap;
    }
}
