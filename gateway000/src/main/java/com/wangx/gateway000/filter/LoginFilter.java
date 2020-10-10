package com.wangx.gateway000.filter;

import com.wangx.gateway000.common.BizException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 配置请求过滤
 */
@Component
public class LoginFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;
    public static final String LOGIN_URL = "/authcenter/login";
    public static final String REGISTER_URL = "/user/register";
    public static final String SECRET = "qazwsx123444$#%#()*&& asdaswwi1235 ?;!@#kmmmpom in***xx**&";
    public static final String TOKEN_PREFIX = "Bearer";
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("gateway000开始进行验证token.....");
        //从请求头中取出token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            //登陆接口放行
            String url = exchange.getRequest().getURI().getPath();
            if (url.contains(REGISTER_URL)) {
                logger.info("路由到user服务进行注册.....");
                return chain.filter(exchange);
            } else if (url.contains(LOGIN_URL)) {
                logger.info("路由到authcenter服务进行登陆.....");
                return chain.filter(exchange);
            } else {
                //提示登陆
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                logger.info("gateway001过滤掉该请求,请先进行登陆，拿token！");
                return exchange.getResponse().setComplete();
            }
        }
        //todo 请求头中存在token,验证token有效性
        //1.redis是否过期
        String redisUserId = redisTemplate.opsForHash().get(token, "userId").toString();
        logger.info("根据token查询到的redis中userId：" + redisUserId);
        if (StringUtils.isBlank(redisUserId)) {
            logger.error("token已过期");
        }
        //2.userId和否一致
        String userId = validateToken(token);
        if (!userId.equals(redisUserId)) {
            logger.error("用户身份有误");
        }
        RequestPath path = exchange.getRequest().getPath();
        logger.info("token有效，正在路由服务,路径: " + path);
        return chain.filter(exchange);
    }

    /**
     * 通过token解析userId
     */
    private String validateToken(String token) {
        if (token != null) {
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String userId = (String) (body.get("userId"));
            if (StringUtils.isEmpty(userId)) {
                throw new BizException("user is error, please check");
            }
            return userId;
        } else {
            throw new BizException("token is error, please check");
        }
    }


    @Override
    public int getOrder() {
        return 0;
    }


}
