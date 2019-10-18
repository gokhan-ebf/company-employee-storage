package com.ebf.storage.api.controller;

import com.ebf.storage.api.exception.DataNotFoundException;
import com.ebf.storage.api.model.Company;
import com.ebf.storage.api.model.Employee;
import com.ebf.storage.api.repository.CompanyRepository;
import com.ebf.storage.api.repository.EmployeeRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @ApiOperation(value = "Get employees of a company from the database",
            notes = "ID of the company is a required parameter." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 409, message = "Company not found")})
    @RequestMapping(value = "/company/{id}/employees", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesForCompany(@PathVariable("id") Long id) throws DataNotFoundException {
        Company selectedCompany = getCompany(id);
        checkCompanyExists(id, selectedCompany);
        return this.employeeRepository.findByCompany(selectedCompany);
    }

    private void checkCompanyExists(Long id, Company selectedCompany) throws DataNotFoundException {
        if (selectedCompany == null) {
            String message = "Company Not Found with ID:" + id;
            logger.error(message);
            throw new DataNotFoundException(message);
        }
    }

    @ApiOperation(value = "Get average salary for a company from the database",
            notes = "ID of the company is a required parameter." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 409, message = "Company not found")})
    @RequestMapping(value = "/company/{id}/salary/avg", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Double getAvgSalaryForCompany(@PathVariable("id") Long id) {
        try {
            Company company = getCompany(id);
            checkCompanyExists(id,company);
            return this.employeeRepository.getAverageSalaryByCompany(company);
        } catch (Exception ex) {
            logger.error("getAvgSalaryForCompany error" + ex.getMessage());
            return 0.0;
        }
    }
    @ApiOperation(value = "Get company from the database",
            notes = "ID of the company is a required parameter." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 409, message = "Company not found")})
    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Company getCompany(@PathVariable("id") Long id) {
        Optional<Company> company = this.companyRepository.findById(id);
        return company.orElseThrow(NoSuchElementException::new);
    }

    @ApiOperation(value = "Save company in the database",
            notes = "Name of the company is a required parameter." )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED")})
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Company saveCompany(@RequestBody Company company) {
        logger.debug("Adding a new company entry with information");
        Company newCompany= new Company(company.getName());
        this.companyRepository.save(newCompany);
        return newCompany;
    }

    @ApiOperation(value = "Update company record in the database",
            notes = "Id and name of the company record are required parameters." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/company", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@RequestBody Company company) {
        this.companyRepository.save(company);
        return company;
    }

    @ApiOperation(value = "Lists all companies in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompanies() {
        return this.companyRepository.findAll();
    }

    @ApiOperation(value = "Deletes company record from the database",
            notes = "ID of the company is a required parameter.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/company", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Long deleteCompany(@RequestBody Company company) throws DataNotFoundException {
        Company deletedCompany = getCompany(company.getId());

        checkCompanyExists(company.getId(), deletedCompany);

        List<Employee> employeeList=getEmployeesForCompany(company.getId());
        if (!employeeList.isEmpty()){
            employeeList.stream().forEach(employee -> this.employeeRepository.delete(employee));
        }

        this.companyRepository.delete(company);
        return company.getId();
    }
}
