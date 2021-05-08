package com.haibao.xrules.controller;

import com.haibao.xrules.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 *
 * @author ml.c
 * @date 11:00 AM 5/6/21
 **/
@RestController
public class XRulesController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, X-RULES !";
    }

    /**
     * 响应式编程的返回值必须是 Flux 或者 Mono ，两者之间可以相互转换
     * Mono：实现发布者，并返回 0 或 1 个元素，即单对象
     * Flux：实现发布者，并返回 N 个元素，即 List 列表对象
     * @return
     */
    @GetMapping("/user")
    public Mono<User> getUser() {
        User user = new User();
        user.setName("海宝爸爸");
        user.setDesc("欢迎关注我");
        //just() 方法可以指定序列中包含的全部元素。
        return Mono.just(user);
    }

}
