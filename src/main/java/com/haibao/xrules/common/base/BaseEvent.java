package com.haibao.xrules.common.base;

import java.util.Date;

/**
 * 事件
 *
 * @author ml.c
 * @date 11:46 PM 5/19/21
 **/
public abstract class BaseEvent {

    /**
     * 场景
     */
    private String scene;

    /**
     * 事件id
     */
    private String id;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 事件评分
     */
    private int score;


    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
