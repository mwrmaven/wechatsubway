package com.maven.service;

import com.maven.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getAll(Map<String, Object> param);

    int addUser(Map<String, Object> param);

    int updateUser(Map<String, Object> param);

}
