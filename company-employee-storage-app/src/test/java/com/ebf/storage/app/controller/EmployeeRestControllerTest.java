package com.ebf.storage.app.controller;

import com.ebf.storage.api.controller.EmployeeRestController;
import com.ebf.storage.api.model.Company;
import org.eclipse.jetty.webapp.WebAppContext;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ebf.storage.api.model.Employee;
import com.ebf.storage.app.CompanyEmployeeStorageAppApplication;
import com.ebf.storage.app.Configuration.TestUtil;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { CompanyEmployeeStorageAppApplication.class })
@ContextConfiguration(classes = { WebAppContext.class })
@WebAppConfiguration
public class EmployeeRestControllerTest {
    private MockMvc mockMvc;

    @SpyBean
    private EmployeeRestController employeeRestController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(employeeRestController);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void add_EmptyEmployeeName_ShouldReturnValidationErrorForNameAndSurname() throws Exception {
        Employee employee = new Employee();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors", Matchers.hasSize(2)));

    }

    @Test
    public void add_NewEmployee_ShouldAddCompanyEntryAndReturnAddedEntry() throws Exception {
        Employee employee = new Employee();
        employee.setName("Gokhan");
        employee.setSurname("Kuyucak");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(16)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Gokhan")));

    }

    @Test
    public void deleteById_EmployeeEntryFound_ShouldDeleteEmployeeEntryAndReturnID() throws Exception {
        Employee employee = new Employee();
        employee.setName("Noak");
        employee.setSurname("MacKain");
        employee.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string("1"));

    }

    @Test
    public void findAll_CompaniesFound_ShouldReturnFoundCompanyEntries() throws Exception {
        Employee employee = new Employee();
        employee.setName("Noak");
        employee.setSurname("MacKain");
        employee.setId(1L);

        Mockito.when(employeeRestController.getEmployees()).thenReturn(Arrays.asList(employee));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

        Mockito.verify(employeeRestController, Mockito.times(1)).getEmployees();
        Mockito.verifyNoMoreInteractions(employeeRestController);
    }
}
