package com.haibao.xrules.common.base;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 事件
 *
 * @author ml.c
 * @date 11:46 PM 5/19/21
 **/
@Document(collection = "x_rules_events")
public abstract class BaseEvent implements Serializable {

    public final static String OPERATETIME = "operateTime";

    /**
     * 场景
     */
    private String scene;

    /**
     * 事件id
     */
    @Id
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


    /**
     * 计算事件评分
     *
     * @param count      当前值
     * @param level      阀值
     * @param levelScore 阀值评分
     * @param perScore   超过阀值以上，每个评分
     * @return 是否达到阈值
     */
    public boolean addScore(int count, int level, int levelScore, int perScore) {
        if (level <= 0 || levelScore <= 0 || perScore < 0) {
            return false;
        }
        if (count >= level) {
            this.score += levelScore + (count - level) * perScore;
            return true;
        }
        return false;
    }

}
