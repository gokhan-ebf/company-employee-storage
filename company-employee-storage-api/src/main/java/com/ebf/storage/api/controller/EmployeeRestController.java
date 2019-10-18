package com.ebf.storage.api.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.ebf.storage.api.exception.DataNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.ebf.storage.api.model.Employee;
import com.ebf.storage.api.repository.EmployeeRepository;

@RequestMapping("api/v1")
@RestController
public class EmployeeRestController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @ApiOperation(value = "Get Employee from Database",
            notes = "ID of the employee is a required parameter." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 409, message = "Employee not found")})
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable("id") Long id) {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        return employee.orElseThrow(NoSuchElementException::new);
    }

    @ApiOperation(value = "Save employee record to database",
            notes = "Name and surname are required parameters of employee" )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED")})
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee) {
        Employee newEmployee= new Employee(employee.getName(),employee.getSurname(),employee.getEmail(),employee.getAddress(),employee.getSalary(),employee.getCompany());
        this.employeeRepository.save(newEmployee);
        return newEmployee;
    }

    @ApiOperation(value = "Update employee record in database",
            notes = "ID of the employee is a required parameter.")
            @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@RequestBody Employee employee) {
        this.employeeRepository.save(employee);
        return employee;
    }

    @ApiOperation(value = "Lists all employee records in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    @ApiOperation(value = "Deletes employee record from the database",
            notes = "ID of the employee is a required parameter.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Long deleteEmployee(@RequestBody Employee employee) throws DataNotFoundException {
        Employee deletedEmployee=getEmployee(employee.getId());
        if (deletedEmployee ==null){
            String message="Employee Not Found with ID:"+employee.getId();
            logger.error(message);
            throw new DataNotFoundException(message);
        }
        this.employeeRepository.delete(employee);
        return employee.getId();
    }
}
