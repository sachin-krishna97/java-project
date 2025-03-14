package com.example.employee.controller;

import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee API", description = "CRUD operations for Employee Management")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    @Operation(summary = "Get all employees", description = "Fetches a list of all employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new employee", description = "Adds a new employee to the database")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Fetches employee details based on ID")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id: " + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an employee", description = "Updates employee details based on ID")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id: " + id));

        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmail(employeeDetails.getEmail());

        employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an employee", description = "Deletes an employee based on ID")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id: " + id));

        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an employee", description = "Updates specific fields of an employee")
    public ResponseEntity<Employee> partialUpdateEmployee(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not exist with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    employee.setFirstName((String) value);
                    break;
                case "lastName":
                    employee.setLastName((String) value);
                    break;
                case "email":
                    employee.setEmail((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }

}
