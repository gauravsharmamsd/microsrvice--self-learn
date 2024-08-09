package com_employee_app.employee_service.controller;

import com_employee_app.employee_service.controller.service.EmployeeService;
import com_employee_app.employee_service.response.EmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    Logger LOGGER= LoggerFactory.getLogger(EmployeeController.class);
    // Your code here

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable("id") int id) {

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployee(id));
    }

    @PostMapping("/saveemployee")
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employeeRequest));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployees());
    }

}
