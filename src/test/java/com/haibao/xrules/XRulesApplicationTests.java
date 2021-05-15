package com.haibao.xrules;

import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.service.BlackListService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XRulesApplicationTests {

    @Autowired
    BlackListService blackListService;

    @Test
    void contextLoads() {
    }

    @Test
    void testBlackList(){
        List<BlackList>  lists = blackListService.queryAll();
        System.out.println(lists.size());
        BlackList blackList = blackListService.get("127.0.0.1");
        System.out.println(blackList);

    }



}
