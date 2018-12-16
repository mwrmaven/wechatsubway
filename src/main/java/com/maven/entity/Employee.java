package com.maven.entity;

import java.io.Serializable;

public class Employee implements Cloneable, Serializable {

    private static final long serialVersionUID = -7803245023513315174L;

    private int id;

    private String name; // 姓名

    private int age; // 年龄

    private String company; // 公司

    private String position; // 职位

    private int gender; // 性别

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", gender=" + gender +
                '}';
    }

    // 浅拷贝
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
