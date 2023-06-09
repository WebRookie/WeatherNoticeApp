package com.webrookie.weathernoticeapp.service;

import com.webrookie.weathernoticeapp.models.dto.UserDTO;
import com.webrookie.weathernoticeapp.models.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author WebRookie
 * @date 2023/6/9 18:21
 **/

public interface UserService {

    /**
     * 根据用户openId查询信息
     * @param openId 用户OpenId
     * @return
     */
    public UserDTO getUser(String openId);

    /**
     * 新建用户
     * @param openId 用户openId
     * @return
     */
    public int createUser(String openId);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(UserDTO user);
}
