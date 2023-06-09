package com.webrookie.weathernoticeapp.models.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author WebRookie
 * @date 2023/3/31 09:21
 **/
@NoArgsConstructor
public class ConfigConstant {

    /**
     * 随机消息字体颜色
     * true 开启
     * false 关闭
     */
    public static boolean randomMessageColorMode = true;

    /**
     * 参与随机的颜色
     * 内容为颜色HEX码（不知道可以百度）
     */
    public static String[] randomColors = {"#FFCCCC", "#33A1C9", "#DC143C","#FF0000","#6B8E23","#236B8E","#FF7F00"};

}
