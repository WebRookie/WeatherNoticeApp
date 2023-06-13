package com.webrookie.weathernoticeapp.models.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WebRookie
 * @date 2023/6/9 14:28
 **/
class WeatherTest {

    private final static String weatherKey = "f9206f4ec824aa7b2effb88c2fbc411b";

    @Test
    void getTodayWeather() {
        String cityCode = "610104";
        String requestUrl = "https://restapi.amap.com/v3/weather/weatherInfo";
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("city", cityCode);
        requestParam.put("key", weatherKey);
        requestParam.put("extensions", "all");
        String requestResult = HttpMethod.httpGet(requestUrl, requestParam);
        JSONObject jsonObject = JSONUtil.parseObj(requestResult);
        JSONArray forecasts = (JSONArray) jsonObject.get("forecasts");
        JSONObject forecastsWeather = (JSONObject)forecasts.get(0);
//        获取第一个值
        List casts = (List)(forecastsWeather.get("casts"));
        JSONObject weather = (JSONObject)casts.get(0);
        System.out.println(weather);
    }


    @Test
    void getCityCode() {
        Boolean getCityName = true;
        String longitude = "108.898476";
        String latitude = "34.242062";
        String location = longitude + ',' + latitude;
        String requestUrl = "https://restapi.amap.com/v3/geocode/regeo";
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("location", location);
        requestParam.put("key", weatherKey);
        String requestResult = HttpMethod.httpGet(requestUrl, requestParam);
        JSONObject jsonObject = JSONUtil.parseObj(requestResult);
        JSONObject regeocode = (JSONObject) jsonObject.get("regeocode");
        JSONObject addressComponent =(JSONObject) regeocode.get("addressComponent");
        if(getCityName) {
            String cityName = addressComponent.getStr("city");
            System.out.println(cityName);
        }else {
            String adcode = addressComponent.getStr("adcode");
            System.out.println(adcode);
        }

    }

    @Test
    void testModelMessage() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", WxUtils.getAccessToken());
        String requestParam = "{\n" +
                "      \"touser\":\"oC4Nv50TEM4Kcc9V5LM-Co2rENHU\",\n" +
                "      \"template_id\":\"bb1uIGU2dgscyl5Z5yNc2B5wVWsQiCqLyKsmRmVtI18\",\n" +
                "      \"data\":{\n" +
                "        \"first\":{\n" +
                "         \"value\":\"早上好呀\"\n" +
                "         },\n" +
                "       \"city\": {\n" +
                "         \"value\":\"西安\"\n" +
                "         },\n" +
                "       \"weather\": {\n" +
                "         \"value\":\"34.56\"\n" +
                "         },\n" +
                "       \"minTemperature\": {\n" +
                "         \"value\":\"34.56\"\n" +
                "         },\n" +
                "       \"maxTemperature\": {\n" +
                "         \"value\":\"34.56\"\n" +
                "         },\n" +
                "       \"wind\": {\n" +
                "         \"value\":\"12\"\n" +
                "         },\n" +
                "       \"wet\": {\n" +
                "         \"value\":\"24.56\"\n" +
                "         },\n" +
                "       \"birthDay\": {\n" +
                "         \"value\":\"2\"\n" +
                "         },\n" +
                "       \"note\": {\n" +
                "         \"value\":\"好好学习，天天向上\"\n" +
                "         }\n" +
                "     }\n" +
                "}";
//        String requestResult = HttpMethod.httpJsonPost(url, requestParam);
//        System.out.println(requestResult);
    }
}