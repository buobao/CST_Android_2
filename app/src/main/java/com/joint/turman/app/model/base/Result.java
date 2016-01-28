package com.joint.turman.app.model.base;

import com.joint.turman.app.AppException;
import com.joint.turman.app.utils.StringUtils;

import org.json.JSONObject;

/**
 * Created by dqf on 2016/1/7.
 */
public class Result extends Base {
    private static final long serialVersionUID = -645821020648740668L;

    private int errorCode;
    private String errorMessage;

    public boolean OK() {
        return errorCode == 1;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static Result parse(String json) throws AppException {
        Result result = null;
        JSONObject object = null;
        try {
            if(StringUtils.isNotEmpty(json)){
                object= new JSONObject(json);
                if(object.optJSONObject("result")!=null){
                    result = parse(object.getJSONObject("result"));
                }
            }
        } catch (Exception e) {
            throw AppException.json(new Exception(json));
        }
        return result;
    }

    public static Result parse(JSONObject tempObject) throws AppException {
        Result result = null;
        try {
            result = new Result();
            result.errorCode = tempObject.optInt("errorCode", 0);
            result.errorMessage = tempObject.optString("errorMessage", "");
        } catch (Exception e) {
            throw AppException.json(e);
        }
        return result;
    }
}
