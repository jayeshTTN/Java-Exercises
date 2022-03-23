package com.REST2.Webservices2.Employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Employee {

    @ApiModelProperty(notes = "Must be Unique")
    private Integer id;

    @ApiModelProperty(notes = "Should Contain both first and last name")
    private String name;
    private int age;

    public Employee() {
    }

    public Employee(Integer id, String name, int age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
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
    @Override
    public String toString() {
        return String.format("Employee [id=%s, name=%s, age=%s]", id, name, age);
    }

}
