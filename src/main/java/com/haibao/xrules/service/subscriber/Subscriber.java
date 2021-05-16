package com.haibao.xrules.service.subscriber;

import org.springframework.data.redis.connection.MessageListener;

/**
 * 订阅者接收消息的基类
 */
public interface Subscriber extends MessageListener {

    /**
     * 类型
     * @return
     */
    default String getType() {
        return this.getClass().getSimpleName();
    }

    /**
     * 通道名称
     * @return
     */
    String getTopic();

}
