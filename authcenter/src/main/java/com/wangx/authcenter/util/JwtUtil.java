package com.wangx.authcenter.util;

import com.wangx.authcenter.common.BizException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JwtUtil {

    public static final String SECRET = "qazwsx123444$#%#()*&& asdaswwi1235 ?;!@#kmmmpom in***xx**&";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_AUTH = "Authorization";

    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", new Random().nextInt());
        map.put("user", userId);
        //设置失效时间？
        String jwt = Jwts.builder()
                .setSubject("user info").setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        String finalJwt = TOKEN_PREFIX + " " + jwt;
        return finalJwt;
    }

    public static Map<String, String> validateToken(String token) {
        if (token != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String id = String.valueOf(body.get("id"));
            String user = (String) (body.get("user"));
            // todo 获取user和id之后可以结合数据库？或者请求中的user信息？ 对用户做校验 -ps 如何判断用户是否窃取别人的token呢？
            map.put("id", id);
            map.put("user", user);
            if (StringUtils.isEmpty(user)) {
                throw new BizException("user is error, please check");
            }
            return map;
        } else {
            throw new BizException("token is error, please check");
        }
    }
}
