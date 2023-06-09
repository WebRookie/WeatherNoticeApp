package com.webrookie.weathernoticeapp.models.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WebRookie
 * @date 2023/6/9 20:55
 **/
class RainBowFartTest {

    @Test
    void getRainBowFart() {
        String httpUrl = "https://api.shadiao.pro/chp";
        String requestResult = HttpMethod.httpGet(httpUrl, new HashMap<>());
        JSONObject jsonObject = JSONUtil.parseObj(requestResult);
        System.out.println(jsonObject);
        JSONObject jsonData = (JSONObject)jsonObject.get("data");
        System.out.println(jsonData.getStr("text"));
    }
}