package com.haibao.xrules;

import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.model.QueryParam;
import com.haibao.xrules.model.RuleResult;
import com.haibao.xrules.service.BlackListService;
import com.haibao.xrules.service.RuleEngineService;
import com.haibao.xrules.utils.GsonUtils;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@SpringBootTest
class XRulesApplicationTests {

    @Autowired
    BlackListService blackListService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    private KieSession kieSession;
    @Resource
    private RuleEngineService ruleEngineService ;


    @Test
    void contextLoads() {
    }

    @Test
    public void blacklist (){
        System.out.println(blackListService.queryAll());
    }

    @Test
    public void drools (){
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

    @Test
    public void redis() {

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

    }


    @Test
    void testBlackList(){

        List<BlackList>  lists = blackListService.queryAll();
        System.out.println(lists.size());
        BlackList blackList = blackListService.get("127.0.0.1");
        System.out.println(blackList);

        blackList = new BlackList();
        blackList.setType("BLACK");
        blackList.setValue("localhost");
        blackList.setDimension("IP");
        Boolean is = blackListService.pub(blackList);
        System.out.println("pub result: " + is);

        int size =0;
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<BlackList>  lists2 = blackListService.queryAll();
            if(lists2.size() != size){
                System.out.println(lists2.size());
                System.out.println(GsonUtils.gsonString(lists2));
                size = lists2.size();
            }
        }

    }



}
