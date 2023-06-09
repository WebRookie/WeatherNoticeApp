package com.webrookie.weathernoticeapp.dao;

import com.webrookie.weathernoticeapp.models.dto.UserDTO;
import com.webrookie.weathernoticeapp.models.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author WebRookie
 * @date 2023/6/9 18:35
 **/

@Repository
public interface UserMapper {
    /**
     * 根据用户openId查询用户信息
     * @param openId
     * @return
     */
    UserDTO getUserByOpenId(String openId);

    /**
     * 创建用户
     * @param openId
     * @return
     */
    int insertUser(String openId);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(UserDTO user);
}
