package com.REST.Webservices.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EmployeeService {
    private static List<Employee> employeeList = new ArrayList<>();
    private static int IdCount = 3;
    static {
        employeeList.add(new Employee(1,"Jayesh",21));
        employeeList.add(new Employee(2,"Mukesh",22));
        employeeList.add(new Employee(3,"Suraj",23));
    }

    public List<Employee> findAll() {
        return employeeList;
    }
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(++IdCount);
        }
        employeeList.add(employee);
        return employee;
    }

    public Employee findOne(int id) {
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
    public Employee deleteEmployee(int id) {
        Iterator<Employee> iterator = employeeList.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId() == id) {
                iterator.remove();
                return employee;
            }
        }
        return null;
    }
   public void update(Employee employee, int ID){
        Employee employee1 = findOne(ID);
        employee1.setId(ID);
        employee1.setAge(employee.getAge());
        employee1.setName(employee.getName());
    }





}
