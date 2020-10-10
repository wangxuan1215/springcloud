package com.wangx.authcenter.controller;

import com.wangx.authcenter.common.Result;
import com.wangx.authcenter.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RefreshScope //刷新config配置
public class LoginController {


    @Autowired
    private LoginService loginService;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public Result login(@RequestParam("userId") String userId,
                        @RequestParam("password") String password) {
        try {
            logger.info("=====调用登陆接口成功 url{} param {} {}", "/login", userId, password);
            return Result.defaultSuccess(loginService.login(userId, password));
        } catch (RuntimeException b) {
            logger.error("=====调用登陆接口失败 url{}" + "/login" + b.getMessage(), b);
            return Result.failure(0, b.getMessage());
        } catch (Exception e) {
            logger.error("=====调用登陆接口失败 url{}" + "/login" + e.getMessage(), e);
            return Result.failure(0, e.getMessage());
        }
    }

}
