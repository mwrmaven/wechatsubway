package com.maven.controller;

import com.maven.entity.CommonEntity;
import com.maven.entity.User;
import com.maven.service.UserService;
import com.maven.utils.CookieManager;
import com.maven.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 登录、登出、注册、更新个人信息
 * create by mavenr 2018/12/19
 */
@Controller
@RequestMapping(path = "/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

    /**
     * 登录
     * @param user
     * @param response
     * @return
     */
    @PostMapping(path = "login", produces = "application/json")
    public CommonEntity enter(@RequestBody User user, HttpServletResponse response) {
        // 判断是否存在用户
        String name = user.getName();
        String pwd = user.getPassword();

        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("password", pwd);

        List<User> all = userService.getAll(param);
        if (all.size() == 0) {
            int code = 201;
            String message = "用户不存在，请注册后登录！";
            return new CommonEntity(code, message);
        }
        // 如果存在，则设置cookie，登录，默认有效时长为1小时
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String key = EncryptionUtil.md5(name + now.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Long age = calendar.getTime().getTime();
        Cookie cookie = new Cookie(key, age + "");
        cookie.setPath("/wechatsubway/cookies/"); // cookie的地址，如果没有一级目录，所有网址的请求都能找到这个cookie
        cookie.setMaxAge(60 * 60); // 最大生存时间，单位为秒
        response.addCookie(cookie); // 将cookie添加到响应中

        CookieManager.set(key, age);

        int code = 200;
        String message = "登录成功！";
        return new CommonEntity(code, message);
    }

    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @PostMapping(path = "logout", produces = "application/json")
    public CommonEntity exit(HttpServletRequest request, HttpServletResponse response) {
        // 删除cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (CookieManager.get(cookie.getName()) != null) {
                cookie.setValue(null);
                cookie.setMaxAge(0); // 设置为0意为立即销毁
                cookie.setPath("/wechatsubway/cookies/");
                response.addCookie(cookie);

                CookieManager.delete(cookie.getName());
            }
        }

        return new CommonEntity(200, "登出成功！");
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping(path = "registry", produces = "application/json")
    public CommonEntity register(@RequestBody User user) {
        String name = user.getName();
        String password = user.getPassword();
        String telephone = user.getTelephone();
        String email = user.getEmail();
        int gender = user.getGender();
        int role = 10;
        String valid = "0";
        if (email.equalsIgnoreCase(user.getVerificationCode())) {
            valid = "1";
        }

        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("password", password);
        param.put("telephone", telephone);
        param.put("gender", gender);
        param.put("role", role);
        param.put("valid", valid);
        param.put("email", email);

        int i = userService.addUser(param);
        if (i == 1) {
            return new CommonEntity(200, "注册成功，请重新登录！");
        } else {
            return new CommonEntity(204, "注册成功，请重新登录！");
        }
    }


}
