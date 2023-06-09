package com.webrookie.weathernoticeapp.models.utils;

/**
 * @author WebRookie
 * @date 2023/6/9 10:25
 **/
public class AccessToken {
    private String token;
    private long expireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = System.currentTimeMillis() + expireTime * 1000;
    }

    /**
     * 判断是否超时
     * @return
     */
    public boolean isExpired() {
        return  System.currentTimeMillis() > this.expireTime;
    }
}
