package com.REST.Webservices.Employee;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class Employee {
    private Integer id;
    @Size(min = 2,max = 10)
    private String name;
    @Min(18)
    @Max(45)
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
