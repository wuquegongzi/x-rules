package com.haibao.xrules.controller;

import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.model.QueryParam;
import com.haibao.xrules.model.RuleResult;
import com.haibao.xrules.model.User;
import com.haibao.xrules.service.BlackListService;
import com.haibao.xrules.service.RuleEngineService;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private static Logger logger = LoggerFactory.getLogger(XRulesController.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    private KieSession kieSession;
    @Resource
    private RuleEngineService ruleEngineService ;

    @Autowired
    BlackListService blackListService;

    @RequestMapping("/blacklist")
    public List<BlackList> blacklist (){
       return blackListService.queryAll();
    }

    @RequestMapping("/param")
    public void param (){
        QueryParam queryParam1 = new QueryParam() ;
        queryParam1.setParamId("1");
        queryParam1.setParamSign("+");
        QueryParam queryParam2 = new QueryParam() ;
        queryParam2.setParamId("2");
        queryParam2.setParamSign("-");
        // 入参
        kieSession.insert(queryParam1) ;
        kieSession.insert(queryParam2) ;
        kieSession.insert(this.ruleEngineService) ;
        // 返参
        RuleResult resultParam = new RuleResult() ;
        kieSession.insert(resultParam) ;
        kieSession.fireAllRules() ;
    }

    @GetMapping("/hello")
    public String hello() {

        redisTemplate.opsForZSet().add("user:001","123",60);

        String lockKey = "123";
        String UUID = cn.hutool.core.lang.UUID.fastUUID().toString();
        boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey,UUID,3, TimeUnit.MINUTES);
        if (!success){
            System.out.println("锁已存在");
        }
        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/DelKey.lua")));
        // 指定返回类型
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),UUID);

        System.out.println(result);

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
