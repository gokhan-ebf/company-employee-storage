package com.ebf.storage.app.controller;

import java.util.Arrays;

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

import com.ebf.storage.api.controller.CompanyRestController;
import com.ebf.storage.api.model.Company;
import com.ebf.storage.api.repository.CompanyRepository;
import com.ebf.storage.app.CompanyEmployeeStorageAppApplication;
import com.ebf.storage.app.Configuration.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { CompanyEmployeeStorageAppApplication.class })
@ContextConfiguration(classes = { WebAppContext.class })
@WebAppConfiguration
public class CompanyRestControllerTest {
    private MockMvc mockMvc;

    @SpyBean
    private CompanyRestController companyRestController;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(companyRestController);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void add_EmptyCompanyName_ShouldReturnValidationErrorForName() throws Exception {
        Company company = new Company();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/company")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].path", Matchers.is("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].message", Matchers.is("Name is mandatory")));

    }

    @Test
    public void add_NewCompany_ShouldAddCompanyEntryAndReturnAddedEntry() throws Exception {
        Company dto = new Company("ABC");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/company")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("ABC")));

    }

    @Test
    public void deleteById_CompanyEntryFound_ShouldDeleteCompanyEntryAndReturnIt() throws Exception {
        Company deleted = new Company("EBF");
        deleted.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/company")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deleted)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string("1"));

    }

    @Test
    public void findAll_CompaniesFound_ShouldReturnFoundCompanyEntries() throws Exception {
        Company first = new Company("EBF");
        first.setId(1L);
        Company second = new Company("Microsoft");
        first.setId(2L);

        Mockito.when(companyRestController.getCompanies()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/company"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

        Mockito.verify(companyRestController, Mockito.times(1)).getCompanies();
        Mockito.verifyNoMoreInteractions(companyRestController);
    }

    @Test
    public void findAll_EmployeeInCompanyFound_ShouldReturnFoundEmployeeEntries() throws Exception {
        Company first = new Company("EBF");
        first.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/company/{id}/employees", first.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(9)));

        ;
    }
}
