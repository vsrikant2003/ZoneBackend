package com.zone.zissa.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.zone.zissa.core.ZissaApplicationTest;

public class LdapMgmtControllerTest extends ZissaApplicationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Junit test case for getAllLdapUsers
     * 
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void getAllLdapUsersTest() throws Exception {

        this.mockMvc.perform(get("/v1/ldapusers")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getAllLdapUsers
     * 
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void getAllLdapUsersByStringTest() throws Exception {

        String searchString = "bathiya";

        this.mockMvc.perform(get("/v1/ldapusers/search").param("searchString", searchString)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for getAllLdapUsers
     * 
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void getAllLdapUsersByStringFailureTest() throws Exception {

        String searchString = "BathiyaT";

        this.mockMvc.perform(get("/v1/ldapusers/search").param("searchString", searchString)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(204));

    }

}
