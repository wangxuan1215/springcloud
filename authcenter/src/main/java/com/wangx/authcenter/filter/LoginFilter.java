//package com.wangx.authcenter.filter;
//
//import com.wangx.authcenter.util.JwtUtil;
//import com.wangx.gateway001.common.BizException;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.route.Route;
//import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.PathContainer;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Map;
//
///**
// * 配置请求过滤
// */
//@Component
//public class LoginFilter implements GlobalFilter, Ordered {
//
//
//    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        Route gatewayUrl = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
//        URI uri = gatewayUrl.getUri();
//        ServerHttpRequest request = exchange.getRequest();
//        HttpHeaders header = request.getHeaders();
//        String token = header.getFirst(JwtUtil.HEADER_AUTH);
//
//        // TODO 没有携带token的则判断URL，若是访问登录接口则放行，其他的则返回错误码，提示登录
//        passLogin(request, token);
//
//        Map<String, String> userMap = JwtUtil.validateToken(token);
//        ServerHttpRequest.Builder mutate = request.mutate();
//        if (userMap.get("user").equals("admin") || userMap.get("user").equals("spring") || userMap.get("user").equals("cloud")) {
//            mutate.header("x-user-id", userMap.get("id"));
//            mutate.header("x-user-name", userMap.get("user"));
//            //x-user-serviceName 表示下游请求对应的服务名如 SPRING-CLOUD-SERVICE  SPRING-CLOUD-GATEWAY
//            mutate.header("x-user-serviceName", uri.getHost());
//        } else {
//            throw new BizException("user not exist, please check");
//        }
//        ServerHttpRequest buildReuqest = mutate.build();
//        return chain.filter(exchange.mutate().request(buildReuqest).build());
//    }
//
//
//    /**
//     * 对于没有携带token的，且是访问登录接口的请求，不做认证，直接执行登录操作
//     *
//     * @param request
//     * @param token
//     */
//    private void passLogin(ServerHttpRequest request, String token) {
//        if (StringUtils.isBlank(token)) {
//            //todo 没有token，是登陆接口就放行，不是登陆接口就提示登陆
//            PathContainer pathContainer = request.getPath().pathWithinApplication();
//            String path = pathContainer.value();
//            logger.info("============" + path);
//            List<PathContainer.Element> elements = pathContainer.elements();
//            int getToken = path.indexOf("getToken");
//            //若是访问登录接口则放行
//            if (getToken > 0) {
//                int end = path.lastIndexOf("/");
//                String name = path.substring(getToken + "getToken".length() + 1, end);
//                String admin = JwtUtil.generateToken(name, "123");
//                throw new BizException("please login to get the token:" + admin);
//            }
//        }
//    }
//
//    @Override
//    public int getOrder() {
//        return -100;
//    }
//}
