package com.maven.mapper.master;

import com.maven.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {

    List<Employee> getAll();

}
