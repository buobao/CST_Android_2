package com.joint.turman.app.model.entity;

import com.joint.turman.app.AppException;
import com.joint.turman.app.model.URLs;
import com.joint.turman.app.model.base.Result;
import com.joint.turman.app.utils.StringUtils;

import org.json.JSONObject;

/**
 * Created by dqf on 2016/1/7.
 */
public class User extends NameEntity {

    private static final long serialVersionUID = 998009362641395810L;

    /**
     * 加密信息
     */
    private String aid;//adminId
    private String salt;//盐值
    private String digest;//加密密码
    /**
     * 账号信息
     */
    private String username;
    //	private String password;
    private boolean isRememberMe;
    /**
     * 头像
     */
    private String portrait;
    /**
     * 公司信息
     */
    private String companyId;
    private String companyName;
    private String companySubName;
    /**
     * 部门信息
     */
    private String departmentId;
    private String departmentName;
    /**
     * 性别（0 女|1 男）
     */
    private int sex;
    /**
     * 是否已绑定手机号（0|1）
     */
    private int ifMobile;
    /**
     * 手机号
     */
    private String mobile;

    public String getAid() {
        return aid;
    }
    public void setAid(String aid) {
        this.aid = aid;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getDigest() {
        return digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    //    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
    public boolean isRememberMe() {
        return isRememberMe;
    }
    public void setRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }
    public String getPortrait() {
        return portrait;
    }
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getCompanySubName() {
        return companySubName;
    }
    public void setCompanySubName(String companySubName) {
        this.companySubName = companySubName;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public int getIfMobile() {
        return ifMobile;
    }
    public void setIfMobile(int ifMobile) {
        this.ifMobile = ifMobile;
    }

    public static User parse(String json) throws AppException {
        User user = null;
        Result result = null;
        JSONObject object = null;
        try {
            if(StringUtils.isNotEmpty(json)){
                object= new JSONObject(json);
                if(object.optJSONObject("result")!=null){
                    result = Result.parse(object.getJSONObject("result"));
                }
                if(object.optJSONObject("data")!=null){
                    user = parse(object.getJSONObject("data"));
                }else{
                    user = new User();
                }
                if(result!=null&&user!=null){
                    user.result = result;
                }
            }
        } catch (Exception e) {
            throw AppException.json(new Exception(json));
        }
        return user;
    }

    public static User parse(JSONObject tempObject) throws AppException {
        User user = null;
        try {
            user = new User();

            user.id = tempObject.optString("id");
            user.createDate = tempObject.optString("createDate");
            user.modifyDate = tempObject.optString("modifyDate");
            user.createrId = tempObject.optString("createrId");
            user.createrName = tempObject.optString("createrName");
            if(StringUtils.isNotEmpty(tempObject.optString("createrFace")))
                user.createrFace = URLs.FILE_PUBLIC+"?keyId="+tempObject.getString("createrFace");

            user.aid = tempObject.optString("aid");
            user.salt = tempObject.optString("salt");

            user.name = tempObject.optString("name");
            user.pinYinHead = tempObject.optString("pinYinHead");
            user.pinYin = tempObject.optString("pinYin");
            user.companyId = tempObject.optString("companyId");
            user.companyName = tempObject.optString("companyName");
            user.companySubName = tempObject.optString("companySubName");
            user.departmentId = tempObject.optString("departmentId");
            user.departmentName = tempObject.optString("departmentName");

            if(StringUtils.isNotEmpty(tempObject.optString("portrait")))
                user.portrait = URLs.FILE_PUBLIC+"?keyId="+tempObject.optString("portrait");
            user.sex = tempObject.optInt("sex");
            user.mobile = tempObject.optString("mobile");
            user.ifMobile = tempObject.optInt("ifMobile");

        } catch (Exception e) {
            throw AppException.json(e);
        }
        return user;
    }

}
