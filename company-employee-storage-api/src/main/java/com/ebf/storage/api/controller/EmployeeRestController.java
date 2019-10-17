package com.ebf.storage.api.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.ebf.storage.api.model.Employee;
import com.ebf.storage.api.repository.EmployeeRepository;

@RequestMapping("api/v1")
@RestController
public class EmployeeRestController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Employee getCompany(@PathVariable("id") Long id) {
        Optional<Employee> company = this.employeeRepository.findById(id);
        return company.orElseThrow(NoSuchElementException::new);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @ResponseBody
    public Employee saveCompany(@RequestBody Employee employee) {
        Employee newEmployee= new Employee(employee.getName(),employee.getSurname(),employee.getEmail(),employee.getAddress(),employee.getSalary(),employee.getCompany());
        this.employeeRepository.save(newEmployee);
        return newEmployee;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    @ResponseBody
    public Employee updateCompany(@RequestBody Employee employee) {
        this.employeeRepository.save(employee);
        return employee;
    }


    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getCompanies() {
        return this.employeeRepository.findAll();
    }

    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    @ResponseBody
    public Long deleteEmployee(@RequestBody Employee employee) {
        this.employeeRepository.delete(employee);
        return employee.getId();
    }
}
