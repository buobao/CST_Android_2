package com.joint.turman.app.model.base;

/**
 * 响应
 * ============================================================================
 * 版权所有 2015
 *
 * @author fallenpanda
 *
 * @version 1.0 2015-12-1
 * ============================================================================
 */
public class Response<T> extends Base {

    private static final long serialVersionUID = -3832328172713606474L;

    /**
     * 结果参数
     */
    public Result result;
    /**
     * 数据
     */
    public T data;

}
