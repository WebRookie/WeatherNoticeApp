package com.webrookie.weathernoticeapp.models.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.List;

/**
 * @author WebRookie
 * @date 2023/6/9 11:57
 **/
public class Weather {

    /**
     * 目前使用的是高德天气key
     */
    public static final String weatherKey = "f9206f4ec824aa7b2effb88c2fbc411b";

    public static JSONObject getTodayWeather(String cityCode) {
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
        return weather;
    }

    /**
     * 获取城市的行政区编码
     * @param longitude
     * @param latitude
     * @return adcode
     */
    public static JSONObject getCityCode(String longitude, String latitude) {
        String location = longitude + ',' + latitude;
        String requestUrl = "https://restapi.amap.com/v3/geocode/regeo";
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("location", location);
        requestParam.put("key", weatherKey);
        String requestResult = HttpMethod.httpGet(requestUrl, requestParam);
        JSONObject jsonObject = JSONUtil.parseObj(requestResult);
        JSONObject regeocode = (JSONObject) jsonObject.get("regeocode");
        JSONObject addressComponent =(JSONObject) regeocode.get("addressComponent");
        return addressComponent;
//        if(getCityName) {
//            String cityName = addressComponent.getStr("city");
//            return cityName;
//        }else {
//            String adcode = addressComponent.getStr("adcode");
//            return adcode;
//        }

    }
}
