package com.haibao.xrules;

import com.haibao.xrules.model.BlackList;
import com.haibao.xrules.service.BlackListService;
import com.haibao.xrules.utils.GsonUtils;
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
