package com.webrookie.weathernoticeapp.service.impl;

import com.webrookie.weathernoticeapp.dao.UserMapper;
import com.webrookie.weathernoticeapp.models.dto.UserDTO;
import com.webrookie.weathernoticeapp.models.entity.User;
import com.webrookie.weathernoticeapp.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author WebRookie
 * @date 2023/6/9 18:22
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserDTO getUser(String openId) {
        UserDTO userByOpenId = userMapper.getUserByOpenId(openId);
        return userByOpenId;
    }

    @Override
    public int createUser(String openId) {
       return userMapper.insertUser(openId);
    }

    @Override
    public int updateUser(UserDTO user) {
        return userMapper.updateUser(user);
    }
}
