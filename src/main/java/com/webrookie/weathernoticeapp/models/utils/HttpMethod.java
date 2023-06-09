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
        return response.body();
    }

    public static String httpFormDataPost(String url, HashMap<String, Object> paramMap) {
        return HttpUtil.post(url, paramMap);
    }

    /**
     * 发送post请求 请求体body参数支持两种类型：
     *   1. 标准参数，例如 a=1&b=2 这种格式
     *   2. Rest模式，此时body需要传入一个JSON或者XML字符串，Hutool会自动绑定其对应的Content-Type
     * 返回数据
     * @param url
     * @param stringBody  body – post表单数据
     * @return
     */
    public static String httpJsonPost(String url, String stringBody) {
        return HttpUtil.post(url, stringBody);
    }
}
