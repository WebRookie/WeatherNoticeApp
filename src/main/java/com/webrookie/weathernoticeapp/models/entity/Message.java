package com.webrookie.weathernoticeapp.models.entity;

import lombok.Data;

/**
 * @author WebRookie
 * @date 2023/3/30 17:43
 **/
@Data
public class Message {
    public String date;
    public String weather;
    public String minTemperature;
    public String maxTemperature;
    public String wind;
    public String wet;
}
