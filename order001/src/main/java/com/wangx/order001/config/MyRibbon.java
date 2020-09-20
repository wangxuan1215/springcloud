//package com.wangx.order001.config;
//
//import com.netflix.loadbalancer.IRule;
//import com.netflix.loadbalancer.RandomRule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 实现自己的Ribbon负载均衡算法
// */
//@Configuration
//public class MyRibbon {
//
//    @Bean
//    public IRule myRule() {
//        //可以自定义一个类,继承AbstractLoadBalancerRule，实现自定义负载均衡算法
//        return new RandomRule();
//    }
//
//}
