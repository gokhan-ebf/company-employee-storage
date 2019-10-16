package com.ebf.storage.api.controller;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ebf.storage.api.model.Company;
import com.ebf.storage.api.model.Employee;
import com.ebf.storage.api.repository.CompanyRepository;
import com.ebf.storage.api.repository.EmployeeRepository;

@RequestMapping("api/v1")
@RestController
public class EmployeeRestController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @RequestMapping(value = "/companies/{id}/employees", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployeesForCompany(@PathVariable("id") Long id) {
        try {
            Company company = getCompany(id);
            return this.employeeRepository.findByCompany(company);
        } catch (Exception ex) {
            logger.error("getEmployeesForCompany error" + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @RequestMapping(value = "/companies/{id}/salary/avg", method = RequestMethod.GET)
    @ResponseBody
    public Double getAvgSalaryForCompany(@PathVariable("id") Long id) {
        try {
            Company company = getCompany(id);
            return this.employeeRepository.getAverageSalaryByCompany(company);
        } catch (Exception ex) {
            logger.error("getAvgSalaryForCompany error" + ex.getMessage());
            return 0.0;
        }
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Company getCompany(@PathVariable("id") Long id) {
        Optional<Company> company = this.companyRepository.findById(id);
        return company.orElseThrow(NoSuchElementException::new);
    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    @ResponseBody
    public List<Company> getCompanies() {
        return this.companyRepository.findAll();
    }
}
