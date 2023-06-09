package com.webrookie.weathernoticeapp.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author WebRookie
 * @date 2023/3/30 18:35
 **/
@Data
public class User {
    private Integer userId;
    private String openId;
    private String nickname;
    private String avatar;
    private String latitude;
    private String longitude;
    private String adcode;
    private String city;
    private Date createDate;
    private Date updateDate;
}
