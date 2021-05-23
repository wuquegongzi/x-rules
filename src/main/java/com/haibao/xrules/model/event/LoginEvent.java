package com.haibao.xrules.model.event;

import com.haibao.xrules.common.base.BaseEvent;
import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 登录事件
 *
 * @author ml.c
 * @date 1:52 PM 5/23/21
 **/
@Document(collection = "x_rules_events")
public class LoginEvent extends BaseEvent  implements Serializable {

    public final static String MOBILE = "mobile";

    public final static String OPERATEIP = "operateIp";

    private String mobile;

    private String operateIp;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }
}
