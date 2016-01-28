package com.joint.turman.app.model.base;

/**
 * Created by dqf on 2016/1/7.
 */
public class Entity extends Base{
    private static final long serialVersionUID = 7331096913171486933L;

    /**
     * 时间戳
     */
    protected String timestamp;

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 返回结果
     */
    protected Result result;

    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * ID
     */
    protected String id;
    /**
     * 创建时间（yyyy-MM-dd HH:mm:ss）
     */
    protected String createDate;
    /**
     * 修改时间（yyyy-MM-dd HH:mm:ss）
     */
    protected String modifyDate;
    /**
     * 创建人信息
     */
    protected String createrId;
    protected String createrName;
    protected String createrFace;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
    public String getCreaterId() {
        return createrId;
    }
    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }
    public String getCreaterName() {
        return createrName;
    }
    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }
    public String getCreaterFace() {
        return createrFace;
    }
    public void setCreaterFace(String createrFace) {
        this.createrFace = createrFace;
    }

    /**
     * 缓存信息
     */
    protected String cacheKey;
    protected String cacheDate;

    public String getCacheKey() {
        return cacheKey;
    }
    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
    public String getCacheDate() {
        return cacheDate;
    }
    public void setCacheDate(String cacheDate) {
        this.cacheDate = cacheDate;
    }
}
