package com.haibao.xrules.service.subscriber;

import com.haibao.xrules.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

/**
 * 订阅者 系统配置
 *
 * @author ml.c
 * @date 9:03 PM 5/16/21
 **/
@Component
@Slf4j
public class SysConfigSubscriber implements Subscriber {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public String getTopic() {
        return sysConfigService.getTopic();
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("SysConfigSubscriber consumer a message",new String(message.getBody()));
        sysConfigService.updateCache();
    }
}
