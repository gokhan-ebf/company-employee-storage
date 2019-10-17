package com.ebf.storage.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ebf.storage.api.model.Company;
import com.ebf.storage.api.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findAll();

    List<Employee> findByCompany(Company company);

    @Query(value = "SELECT AVG(salary) FROM Employee WHERE company_id = ?1")
    Double getAverageSalaryByCompany(Company company);

}
