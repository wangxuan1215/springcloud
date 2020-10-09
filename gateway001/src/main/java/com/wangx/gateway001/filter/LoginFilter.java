package com.wangx.gateway001.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 配置请求过滤
 */
@Component
public class LoginFilter implements GlobalFilter, Ordered {

    public static final String LOGIN_URL = "/authcenter/login";
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("gateway001开始进行验证token.....");
//        String token = exchange.getRequest().getQueryParams().getFirst("token");
        //从请求头中取出token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            //登陆接口放行
            String url = exchange.getRequest().getURI().getPath();
            if (url.contains(LOGIN_URL)) {
                logger.info("路由登陆认证服务.....");
                return chain.filter(exchange);
            } else {
                //提示登陆
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                logger.info("gateway001过滤该请求,请先登陆！");
                return exchange.getResponse().setComplete();
            }
        }
        logger.info("请求正常，正在路由服务.....");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
