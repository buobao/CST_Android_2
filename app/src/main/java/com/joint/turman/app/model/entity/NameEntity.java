package com.joint.turman.app.model.entity;

import com.joint.turman.app.model.base.Entity;

/**
 * Created by dqf on 2016/1/7.
 */
public class NameEntity extends Entity {

    private static final long serialVersionUID = -2619897979845236821L;

    /**
     * 名称
     */
    protected String name;
    /**
     * 拼音
     */
    protected String pinYinHead;
    protected String pinYin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinYinHead() {
        return pinYinHead;
    }

    public void setPinYinHead(String pinYinHead) {
        this.pinYinHead = pinYinHead;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }
}