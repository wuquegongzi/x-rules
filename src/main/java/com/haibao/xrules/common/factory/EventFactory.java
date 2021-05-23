package com.haibao.xrules.common.factory;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.haibao.xrules.common.base.BaseEvent;
import com.haibao.xrules.common.enums.SceneEnums;
import com.haibao.xrules.model.event.LoginEvent;
import com.haibao.xrules.utils.GsonUtils;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 事件工厂
 *
 * @author ml.c
 * @date 1:46 PM 5/23/21
 **/
@Component
public class EventFactory {

    private static Logger logger = LoggerFactory.getLogger(EventFactory.class);

    public BaseEvent build(String json) {

        Map parmMap = GsonUtils.gsonToMaps(json);
        if (null == parmMap || !parmMap.containsKey("scene")) {
            logger.error("scene参数错误，" + json);
            throw new RuntimeException("scene参数缺失，" + json);
        }
        String scene = (String) parmMap.get("scene");
        SceneEnums enumScene = SceneEnums.valueOf(scene.toUpperCase());
        if (null == enumScene) {
            logger.error("json格式错误，" + json);
            throw new RuntimeException("json格式错误，" + json);
        }

        BaseEvent event = null;
        if (SceneEnums.LOGIN.equals(enumScene)) {
            event = GsonUtils.gsonToBean(json, LoginEvent.class);
        }

        check(event);

        return event;
    }


    private void check(BaseEvent event) {
        if ( null == event ) {
            throw new RuntimeException("事件错误");
        }
        if (null == event.getOperateTime() ) {
            event.setOperateTime(new Date());
        }
        if (StrUtil.isEmpty(event.getId())) {
            event.setId(UUID.fastUUID().toString());
        }

        // TODO  扩展维度信息
    }

}
