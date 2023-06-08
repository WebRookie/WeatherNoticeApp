package com.webrookie.weathernoticeapp.models.entity;

import lombok.Data;

/**
 * @author WebRookie
 * @date 2023/3/30 18:35
 **/
@Data
public class User {
    public String userNo;
    public String wechat;
    public String openId;
    public String province;
}
