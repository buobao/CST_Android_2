package com.joint.turman.app.bean;

/**
 * Token
 * ============================================================================
 * 版权所有 2015 。
 *
 * @author fallenpanda
 *
 * @version 1.0 2015-12-17 。
 * ============================================================================
 */
public class Token {

    public Token() {
    }

    public Token(String username, String digest) {
        this.username = username;
        this.digest = digest;
    }

    /**
     * 账号
     */
    public String username;

    /**
     * 盐值
     */
    public String digest;

}
