package com.webrookie.weathernoticeapp.models.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;

/**
 * @author WebRookie
 * @date 2023/3/30 17:51
 **/

public class RainBowFart {
    /**
     * 彩虹屁
     * @return
     */
    public static String getRainBowFart() {
        String httpUrl = "https://api.shadiao.pro/chp";
        String requestResult = HttpMethod.httpGet(httpUrl, new HashMap<>());
        JSONObject jsonObject = JSONUtil.parseObj(requestResult);
        JSONObject jsonData = (JSONObject)jsonObject.get("data");
        return jsonData.getStr("text");
    }
}
