package com.ebf.storage.api.controller;

import com.ebf.storage.api.model.Company;
import com.ebf.storage.api.model.Employee;
import com.ebf.storage.api.repository.CompanyRepository;
import com.ebf.storage.api.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequestMapping("api/v1")
@RestController
public class CompanyRestController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyRestController.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/company/{id}/employees", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployeesForCompany(@PathVariable("id") Long id) {
        try {
            Company company = this.getCompany(id);
            return this.employeeRepository.findByCompany(company);
        } catch (Exception ex) {
            logger.error("getEmployeesForCompany error" + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @RequestMapping(value = "/company/{id}/salary/avg", method = RequestMethod.GET)
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

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Company getCompany(@PathVariable("id") Long id) {
        Optional<Company> company = this.companyRepository.findById(id);
        return company.orElseThrow(NoSuchElementException::new);
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseBody
    public Company saveCompany(@RequestBody Company company) {
        Company newCompany= new Company(company.getName());
        this.companyRepository.save(newCompany);
        return newCompany;
    }

    @RequestMapping(value = "/company", method = RequestMethod.PUT)
    @ResponseBody
    public Company updateCompany(@RequestBody Company company) {
        this.companyRepository.save(company);
        return company;
    }


    @RequestMapping(value = "/company", method = RequestMethod.GET)
    @ResponseBody
    public List<Company> getCompanies() {
        return this.companyRepository.findAll();
    }

    @RequestMapping(value = "/company", method = RequestMethod.DELETE)
    @ResponseBody
    public Long deleteCompany(@RequestBody Company company) {

        List<Employee> employeeList=getEmployeesForCompany(company.getId());
        if (!employeeList.isEmpty()){
            employeeList.stream().forEach(employee -> this.employeeRepository.delete(employee));
        }

        this.companyRepository.delete(company);
        return company.getId();
    }
}
