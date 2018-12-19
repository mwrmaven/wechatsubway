package com.maven.service.impl;

import com.maven.entity.User;
import com.maven.mapper.master.UserMapper;
import com.maven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll(Map<String, Object> param) {
        return userMapper.getAll(param);
    }

    @Override
    public int addUser(Map<String, Object> param) {
        return userMapper.addUser(param);
    }

    @Override
    public int updateUser(Map<String, Object> param) {
        return userMapper.updateUser(param);
    }
}
