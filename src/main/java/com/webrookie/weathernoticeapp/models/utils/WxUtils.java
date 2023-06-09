package com.webrookie.weathernoticeapp.models.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

/**
 * @author WebRookie
 * @date 2023/3/31 09:16
 **/
public class WxUtils {

//    @Value("${com.appId}")
    public static String appId = "wx88848b8d08572c24";
//    @Value("${com.appscret}")
    public static String appSecret = "9d0ce793242a18a5aa2bc4b69a95238a";
    public static String weatherTemplateId = "_HHx6TCakyNeYhcyMni_eexXr4B3IwWDGDQ_NERsbbw";
    public static AccessToken accessToken = new AccessToken();

    public static void geToken() {
        String grantType = "client_credential";
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("grant_type", grantType);
        requestParam.put("appid", appId);
        requestParam.put("secret", appSecret);
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        String sendRequest = HttpMethod.httpGet(requestUrl, requestParam);
        JSONObject resultObject = JSONUtil.parseObj(sendRequest);
        String token = resultObject.getStr("access_token");
        long expiresIn = resultObject.getLong("expires_in");
         accessToken.setToken(token);
        accessToken.setExpireTime(expiresIn);
        System.out.println("微信相应结果" + resultObject);
     }

    /**
     * 获取AcessToken
     * @return
     */
    public static String getAccessToken() {
        if(accessToken == null || accessToken.isExpired()) {
            // 获取token或者刷新token
            geToken();
        }
        return accessToken.getToken();
    }


}
