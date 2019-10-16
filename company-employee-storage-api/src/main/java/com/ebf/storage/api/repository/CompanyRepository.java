package com.ebf.storage.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ebf.storage.api.model.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findAll();
}
