package com.example.springbootpostgres.controller;

import com.example.springbootpostgres.exception.ResourceNotFoundException;
import com.example.springbootpostgres.model.Employee;
import com.example.springbootpostgres.repository.EmployeeRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    EmployeeRepository repo;

    //greet employee
    @GetMapping("/greet")
    public String greet(){
        return "Welcome";
    }    //get employees

    @GetMapping("getEmployees")
     public List<Employee> getAllEmployees(){
    return this.repo.findAll();
}

    //get employee by id

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable (value="id") Long employeeId)
        throws ResourceNotFoundException {
        Employee employee= repo.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("employee not found for this is:"+employeeId));
        return ResponseEntity.ok().body(employee);
    }

    //create employee
    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return this.repo.save(employee);
    }

    //delete employee
    @DeleteMapping("employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value="id") Long employeeId) throws ResourceNotFoundException{
        Employee employee= repo.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("employee not found for this is:"+employeeId));
    this.repo.delete(employee);
    Map<String, Boolean>response=new HashMap<>();
    response.put("deleted",Boolean.TRUE);

    return response;

    }

    //update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable (value="id") Long employeeId, @Validated @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        Employee employee= repo.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("employee not found for this is:"+employeeId));
        employee.setEmail(employeeDetails.getEmail());
        employee.setFirstname(employeeDetails.getFirstname());
        employee.setLastname(employeeDetails.getLastname());

        return ResponseEntity.ok(this.repo.save(employee));
    }
    }







