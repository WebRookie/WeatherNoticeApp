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

    @Value("${com.appId}")
    public String appId;

    @Value("${com.appscret}")
    public String appSecret;


    public String getAccessToken() {
        String grantType = "client_credential";
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("grantType", grantType);
        requestParam.put("appid", appId);
        requestParam.put("secret", appSecret);
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        String sendRequest = HttpMethod.httpGet(requestUrl, requestParam);
        JSONObject resultObject = JSONUtil.parseObj(sendRequest);
        System.out.println("微信相应结果" + resultObject);
        return resultObject.get("access_token").toString();
    }

    /**
     * <xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>
     *   <FromUserName><![CDATA[fromUser]]></FromUserName>
     *   <CreateTime>12345678</CreateTime>
     *   <MsgType><![CDATA[text]]></MsgType>
     *   <Content><![CDATA[你好]]></Content>
     * </xml>
     */
}
