package com.webrookie.weathernoticeapp.models.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WebRookie
 * @date 2023/6/9 10:53
 **/
class WxUtilsTest {

    @Test
    void geToken() {
        String requestUrl = String.format("https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=%s", WxUtils.getAccessToken());
//        String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry";
        System.out.println(requestUrl);
        String requestParam = "{\n" +
                "    \"industry_id1\":\"1\",\n" +
                "    \"industry_id2\":\"4\"\n" +
                "}";
        String postResult = HttpMethod.httpJsonPost(requestUrl, requestParam);
        System.out.println(postResult);
    }
}