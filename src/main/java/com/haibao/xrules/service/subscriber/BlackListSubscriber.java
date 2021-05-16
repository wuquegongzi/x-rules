package com.haibao.xrules.service.subscriber;

import com.haibao.xrules.service.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

/**
 * 订阅者 黑名单
 *
 * @author ml.c
 * @date 12:27 PM 5/16/21
 **/
@Component
@Slf4j
public class BlackListSubscriber implements Subscriber {

    @Autowired
    private BlackListService blackListService;

    @Override
    public String getTopic() {
        return blackListService.getTopic();
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {

        log.info("BlackListSubscriber consumer a message",new String(message.getBody()));
        blackListService.updateCache();
    }
}
