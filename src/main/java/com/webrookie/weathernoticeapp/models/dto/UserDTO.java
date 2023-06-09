package com.webrookie.weathernoticeapp.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author WebRookie
 * @date 2023/6/9 19:53
 **/
@Data
public class UserDTO {
    private Integer userId;
    private String openId;
    private String nickname;
    private String avatar;
    private String latitude;
    private String longitude;
    private String adcode;
    private String city;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
}
