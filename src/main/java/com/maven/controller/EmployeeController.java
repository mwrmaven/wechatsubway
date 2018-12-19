package com.maven.controller;

import com.maven.conf.Conf;
import com.maven.entity.Employee;
import com.maven.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/employees", produces = "application/json")
    public List<Employee> getEmployees(HttpServletRequest servletRequest) {
        List<Employee> all = employeeService.getAll();
        for (Employee e : all) {
            System.out.println(e.toString());
        }
        return all;
    }

    @PostMapping(path = "/employees", produces = "application/json")
    public String helloWord() {
        System.out.println(Conf.get("url"));
        return "{'code':'success'}";
    }

}
