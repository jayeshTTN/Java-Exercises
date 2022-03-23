package com.REST2.Webservices2.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/Employees")
    public List<Employee> retrieveAllEmployee(){
        return service.findAll();
    }
    @GetMapping("/Employees/{id}")
    public Employee retrieveEmployee(@PathVariable int id){
        Employee employee = service.findOne(id);
        if(employee == null){
            throw new EmployeeNotFoundException("id-"+ id);
        }
        return employee;
    }
    @PostMapping("/Employees")
    public ResponseEntity<Object> addEmployee( @RequestBody Employee employee){
        Employee emp = service.save(employee);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emp.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/Employees/{id}")
    public void deleteEmployee(@PathVariable int id) {
        Employee employee = service.deleteEmployee(id);

        if(employee==null)
            throw new EmployeeNotFoundException("id-"+ id);
    }

    @PutMapping("/Employees/{id}")
    public ResponseEntity<Object> addEmployee( @RequestBody Employee employee,@PathVariable int id){
        Employee emp = service.findOne(id);
        if(emp == null){
            throw new EmployeeNotFoundException("id-"+ id);
        }
        else{
            service.update(employee,id);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(emp.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }
}
