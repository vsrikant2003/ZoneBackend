package com.zone.zissa.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.zone.zissa.core.ZissaApplicationTest;

import net.minidev.json.JSONObject;

public class UserMgmtControllerTest extends ZissaApplicationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    // for success test cases authentication
    private static final String username = "BathiyaT";
    private static final String password = "Zone@789";

    /**
     * Junit test case for getAllUsers
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllUsersTest() throws Exception {

        this.mockMvc.perform(get("/v1/users")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for addUser
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addUserTest() throws Exception {

        JSONObject addUser = new JSONObject();

        addUser.put("sAMAccountName", "zones");
        addUser.put("email", "zones@zone24x7.com");
        addUser.put("firstName", "Bathiya");
        addUser.put("lastName", "Tennakoon");
        addUser.put("role_ID", "1");

        this.mockMvc
                .perform(post("/v1/users").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addUser)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(201));

    }

    /**
     * Junit Failure test case for addUser By Giving Existing sAMAccountName
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addUserFailureTest() throws Exception {

        JSONObject addUser = new JSONObject();

        addUser.put("sAMAccountName", "BathiyaT");
        addUser.put("email", "zones@zone24x7.com");
        addUser.put("firstName", "Bathiya");
        addUser.put("lastName", "Tennakoon");
        addUser.put("role_ID", "1");

        this.mockMvc
                .perform(post("/v1/users").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addUser)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit Failure test case for addUser By Giving incorrect json object
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addUserFailureByJsonTest() throws Exception {

        JSONObject addUser = new JSONObject();

        addUser.put("sAMAccountName", "BathiyaT");
        addUser.put("Email", "zones@zone24x7.com");
        addUser.put("firstName", "Bathiya");
        addUser.put("lastName", "Tennakoon");
        addUser.put("role_ID", "1");

        this.mockMvc
                .perform(post("/v1/users").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addUser)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));

    }

    /**
     * Junit test case for updateUser
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateUserTest() throws Exception {

        JSONObject userData = new JSONObject();
        userData.put("user_ID", "1");
        userData.put("sAMAccountName", "Zissa");
        userData.put("email", "bathiyat@zone24x7.com");
        userData.put("firstName", "Bathiya");
        userData.put("lastName", "Tennakoon");
        userData.put("role_ID", 1);

        this.mockMvc
                .perform(put("/v1/users").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(userData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for updateUser by modifying emailID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateUserFailureTestByInvalidEmail() throws Exception {

        JSONObject userData = new JSONObject();
        userData.put("user_ID", "1");
        userData.put("sAMAccountName", "Zissa");
        userData.put("email", "bathiyat123@zone24x7.com");
        userData.put("firstName", "Bathiya");
        userData.put("lastName", "Tennakoon");
        userData.put("role_ID", 1);

        this.mockMvc
                .perform(put("/v1/users").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(userData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for updateUser by invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateUserFailureTestByInvalidData() throws Exception {

        JSONObject userData = new JSONObject();
        userData.put("user_ID", "00");
        userData.put("sAMAccountName", "Zissa");
        userData.put("firstName", "Bathiya");
        userData.put("lastName", "Tennakoon");
        userData.put("role_ID", 1);

        this.mockMvc
                .perform(put("/v1/users").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(userData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));

    }

    /**
     * Junit test case for deleteUser
     * 
     * @PathParam user_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteUserTest() throws Exception {

        this.mockMvc.perform(delete("/v1/users/{user_ID}", 3)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for deleteUser By Invalid user_ID
     * 
     * @PathParam user_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteUserFailureTest() throws Exception {
        int id = 000;
        this.mockMvc.perform(delete("/v1/users/{user_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for deleteUser by deleting Foriegn key relation
     * 
     * @PathParam user_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteUserFailureTestByForiegnKey() throws Exception {

        this.mockMvc.perform(delete("/v1/users/{user_ID}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

}
