# Company Employee Storage Service

This project demonstrates a simple approach for an employee storage service for companies.

## Overview
The project contains 3 maven modules: 
* company-employee-storage-api
* company-employee-storage-ui
* company-employee-storage-app

The `company-employee-storage-api` contains the backend API implemented with Spring Boot and the `company-employee-storage-ui` contains
the Javascript based frontend. Both modules are pulled in as a dependency by `company-employee-storage-app`.

The `company-employee-storage-ui` maven module uses the `com.github.eirslett:frontend-maven-plugin`
to delegate to the node-webpack tooling during the maven build process. 

## Requirements
* Java
* Maven

## Dependencies
* Spring Boot
* Node.js
* Webpack
* React


## Setup
To build the example execute the following in your shell: 
```
mvn clean verify
```

## Running
To run the example execute the following in your shell: 
```
java -jar company-employee-storage-app/target/*.jar
```

Then open [http://localhost:8080/app/index.html](http://localhost:8080/app/index.html) in your browser.


## Running with Docker
```
cd company-employee-storage-app
docker build -t company-employee-storage-app:v1 .
docker run --rm -p 8080:8080 company-employee-storage-app:v1
```
## Workflow

For deployment simply build the `company-employee-storage-app` maven project via `mvn clean verify` and run the resulting jar. 
The artifact from the `company-employee-storage-ui` module will be included in the generated fat-jar(company-employee-storage-app.jar).

For test purposes, a sample data is initially loaded into H2 DB. The sample data can be found in `company-employee-storage-apo/resources/data.sql`

Docker file for the application can be found `company-employee-storage-app\Dockerfile`

Kubernetes deployment yaml: `company-employee-storage-app\deployment.yml`

Kubernetes service yaml: `company-employee-storage-app\kube-service.yml`

I also deployed application my current Gcloud Kubernetes Engine. You can visit the application at [`http://34.68.163.44/app/index.html`](http://34.68.163.44/app/index.html)

## Current Problems
In current UI implementation, the average Salary field is not updated after editing, adding, deleting employee records. The problem is `CompanyAverageSalary` component is not rendering after employee operations.
