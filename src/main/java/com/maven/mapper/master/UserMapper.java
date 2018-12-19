package com.maven.mapper.master;

import com.maven.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * create by mavenr 2018/12/19
 */
@Repository
public interface UserMapper {
    List<User> getAll(Map<String, Object> param);

    int addUser(Map<String, Object> param);

    int updateUser(Map<String, Object> param);
}
