package com.webrookie.weathernoticeapp.models.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.util.HashMap;

/**
 * @author WebRookie
 * @date 2023/3/30 18:09
 **/

public class HttpMethod {
    /**
     * 发送get请求
     * @param url
     * @param paramMap
     * @return
     */
    public static String httpGet(String url, HashMap<String, Object> paramMap) {
        HttpResponse response = HttpRequest.get(url).form(paramMap).execute();
        return  response.body();
    }

    public static String httpPost(String url, HashMap<String, Object> paramMap) {
        return HttpUtil.post(url, paramMap);
    }
}
