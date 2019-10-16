package com.ebf.storage.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Table(name = "companies")
@Entity
public class Company {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    public Company() {
        super();
    }

    public Company(@NotNull String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
