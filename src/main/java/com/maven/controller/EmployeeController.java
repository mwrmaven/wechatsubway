package com.maven.controller;

import com.maven.entity.Employee;
import com.maven.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/employees", produces = "application/json")
    public List<Employee> getEmployees(HttpServletRequest servletRequest) {
        Properties properties = new Properties();
        try {
            File conf = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "conf.properties");
            InputStream in = new FileInputStream(conf);
            properties.load(in);
            System.out.println(properties.get("BJ.Station.Url"));
            System.out.println(properties.get("url"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Employee> all = employeeService.getAll();
        for (Employee e : all) {
            System.out.println(e.toString());
        }
        return all;
    }

    @PostMapping(path = "/employees", produces = "application/json")
    public String helloWord() {
        return "{'code':'success'}";
    }

}
