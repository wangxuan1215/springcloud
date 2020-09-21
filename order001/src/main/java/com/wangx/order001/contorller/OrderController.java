package com.wangx.order001.contorller;

import com.wangx.order001.service.OrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RefreshScope //刷新config配置
public class OrderController {

    //提供Http远程访问服务,简单的Restful服务模版.结合Ribbon使用
//    @Autowired
//    private RestTemplate restTemplate;

//    private static final String REST_URL_PREFIX = "http://user"; //通过服务名访问远程服务

//    //通过Ribbon请求user远程服务
//    @GetMapping("/user/get")
//    public String order() {
//        return restTemplate.getForObject(REST_URL_PREFIX + "/user/get", String.class);
//    }


    @Autowired
    private OrderInfoService orderInfoService;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 通过订单id查询用户信息
     */
    @GetMapping("/remote/getUserInfo/{id}")//通过feign，找到远程服务和暴露的接口
    public String feignUser(@PathVariable String id) {
        return orderInfoService.getUserInfoByOrderId(id);
    }

    /**
     * 通过订单id查询订单信息
     */
    @GetMapping("/getOrderInfo/{id}")
    public String orderInfo(@PathVariable String id) {
        try {
            logger.info("======>>>>>>通过订单id查询订单信息-调用 url:{{}} params:{}", "/getOrderInfo", id);
            return orderInfoService.orderInfo(id);
        } catch (RuntimeException b) {
            logger.error("<<<<<<======通过订单id查询订单信息-出错 reason:{}", "/getOrderInfo", b);
            return "500";
        } catch (Exception e) {
            logger.error("<<<<<<======通过订单id查询订单信息-出错 reason:{}", "/getOrderInfo", e);
            return "500";
        }
    }


//    /**
//     * config统一配置中心文件内容
//     */
//    @Value("${env.version}")
//    private String msg;
//
//    @RequestMapping("/config")
//    public String index1() {
//        return "读取的配置文件是:" + msg;
//    }

}
