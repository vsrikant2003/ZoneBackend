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

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class RoleMgmtControllerTest extends ZissaApplicationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    // for success test cases authentication
    private static final String username = "BathiyaT";
    private static final String password = "Zone@789";

    // for failure test cases authentiation (access denied)
    private static final String f_Username = "AmaliK";
    private static final String f_Password = "Zone@789";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Junit test case for addRole
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addRoleTest() throws Exception {

        JSONObject addRoleData = new JSONObject();

        addRoleData.put("name", "User");
        addRoleData.put("administration_type", "1");
        addRoleData.put("category_ID", "1");

        JSONArray permissionsArray = new JSONArray();
        JSONObject permisssionobject1 = new JSONObject();
        permisssionobject1.put("category_ID", 1);
        JSONArray operationArray1 = new JSONArray();
        operationArray1.add(1);
        operationArray1.add(2);
        operationArray1.add(3);
        operationArray1.add(4);
        operationArray1.add(5);
        permisssionobject1.put("operation", operationArray1);
        permissionsArray.add(permisssionobject1);

        JSONObject permisssionobject2 = new JSONObject();
        permisssionobject2.put("category_ID", 2);
        JSONArray operationArray2 = new JSONArray();
        operationArray2.add(1);
        operationArray2.add(2);
        operationArray2.add(3);
        operationArray2.add(4);
        operationArray2.add(5);
        permisssionobject2.put("operation", operationArray2);
        permissionsArray.add(permisssionobject2);

        addRoleData.put("permissions", permissionsArray);

        this.mockMvc
                .perform(post("/v1/roles").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addRoleData)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(201));
    }

    /**
     * Junit Failure test case for addRole using existing values
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addRoleFailureTest() throws Exception {

        JSONObject addRoleData = new JSONObject();

        addRoleData.put("name", "Admin");
        addRoleData.put("administration_type", "1");
        addRoleData.put("category_ID", "1");

        JSONArray permissionsArray = new JSONArray();
        JSONObject permisssionobject1 = new JSONObject();
        permisssionobject1.put("category_ID", 1);
        JSONArray operationArray1 = new JSONArray();
        operationArray1.add(1);
        operationArray1.add(2);
        operationArray1.add(3);
        operationArray1.add(4);
        operationArray1.add(5);
        permisssionobject1.put("operation", operationArray1);
        permissionsArray.add(permisssionobject1);

        JSONObject permisssionobject2 = new JSONObject();
        permisssionobject2.put("category_ID", 2);
        JSONArray operationArray2 = new JSONArray();
        operationArray2.add(1);
        operationArray2.add(2);
        operationArray2.add(3);
        operationArray2.add(4);
        operationArray2.add(5);
        permisssionobject2.put("operation", operationArray2);
        permissionsArray.add(permisssionobject2);

        addRoleData.put("permissions", permissionsArray);

        this.mockMvc
                .perform(post("/v1/roles").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addRoleData)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));
    }

    /**
     * Junit Failure test case for addRole using invalid administration_type
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addRoleFailureTestByInvalidData() throws Exception {

        JSONObject addRoleData = new JSONObject();

        addRoleData.put("name", "Admin");
        addRoleData.put("administration_type", "Abc123");
        addRoleData.put("category_ID", "1");

        JSONArray permissionsArray = new JSONArray();
        JSONObject permisssionobject1 = new JSONObject();
        permisssionobject1.put("category_ID", 1);
        JSONArray operationArray1 = new JSONArray();
        operationArray1.add(1);
        operationArray1.add(2);
        operationArray1.add(3);
        operationArray1.add(4);
        operationArray1.add(5);
        permisssionobject1.put("operation", operationArray1);
        permissionsArray.add(permisssionobject1);

        JSONObject permisssionobject2 = new JSONObject();
        permisssionobject2.put("category_ID", 2);
        JSONArray operationArray2 = new JSONArray();
        operationArray2.add(1);
        operationArray2.add(2);
        operationArray2.add(3);
        operationArray2.add(4);
        operationArray2.add(5);
        permisssionobject2.put("operation", operationArray2);
        permissionsArray.add(permisssionobject2);

        addRoleData.put("permissions", permissionsArray);

        this.mockMvc
                .perform(post("/v1/roles").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addRoleData)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Junit test case for updateRole
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateRoleTest() throws Exception {

        JSONObject updateRoleData = new JSONObject();

        updateRoleData.put("role_ID", "1");
        updateRoleData.put("name", "Admin");
        updateRoleData.put("administration_type", "1");
        updateRoleData.put("category_ID", "1");

        JSONArray permisssionarray = new JSONArray();

        JSONObject permissisonObject = new JSONObject();
        permissisonObject.put("category_ID", "1");

        JSONObject deleteOperationObject = new JSONObject();

        JSONArray permissionId = new JSONArray();
        permissionId.add(6);

        deleteOperationObject.put("permission_ID", permissionId);

        permissisonObject.put("delete_operation", deleteOperationObject);

        JSONObject insertOperationObject = new JSONObject();

        JSONArray operationId = new JSONArray();

        insertOperationObject.put("operation_ID", operationId);

        permissisonObject.put("insert_operation", insertOperationObject);

        permisssionarray.add(permissisonObject);

        updateRoleData.put("permissions", permisssionarray);

        this.mockMvc
                .perform(put("/v1/roles").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateRoleData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit failure test case for updateRole by giving invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateRoleFailureTest() throws Exception {

        JSONObject updateRoleData = new JSONObject();

        updateRoleData.put("role_ID", "1");
        updateRoleData.put("name", "Admin");
        updateRoleData.put("administration_type", "1");
        updateRoleData.put("category_ID", "00");

        JSONArray permisssionarray = new JSONArray();

        JSONObject permissisonObject = new JSONObject();
        permissisonObject.put("category_ID", "1");

        JSONObject deleteOperationObject = new JSONObject();

        JSONArray permissionId = new JSONArray();
        permissionId.add(6);

        deleteOperationObject.put("permission_ID", permissionId);

        permissisonObject.put("delete_operation", deleteOperationObject);

        JSONObject insertOperationObject = new JSONObject();

        JSONArray operationId = new JSONArray();
        operationId.add(5);
        operationId.add(6);

        insertOperationObject.put("operation_ID", operationId);

        permissisonObject.put("insert_operation", insertOperationObject);

        permisssionarray.add(permissisonObject);

        updateRoleData.put("permissions", permisssionarray);

        this.mockMvc
                .perform(put("/v1/roles").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateRoleData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));

    }

    /**
     * Junit failure test case for updateRole by giving invalid roleId
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateRoleFailureTestByInvalidId() throws Exception {

        JSONObject updateRoleData = new JSONObject();

        updateRoleData.put("role_ID", "000");
        updateRoleData.put("name", "Admin");
        updateRoleData.put("administration_type", "1");
        updateRoleData.put("category_ID", "00");

        JSONArray permisssionarray = new JSONArray();

        JSONObject permissisonObject = new JSONObject();
        permissisonObject.put("category_ID", "1");

        JSONObject deleteOperationObject = new JSONObject();

        JSONArray permissionId = new JSONArray();
        permissionId.add(6);

        deleteOperationObject.put("permission_ID", permissionId);

        permissisonObject.put("delete_operation", deleteOperationObject);

        JSONObject insertOperationObject = new JSONObject();

        JSONArray operationId = new JSONArray();
        operationId.add(5);
        operationId.add(6);

        insertOperationObject.put("operation_ID", operationId);

        permissisonObject.put("insert_operation", insertOperationObject);

        permisssionarray.add(permissisonObject);

        updateRoleData.put("permissions", permisssionarray);

        this.mockMvc
                .perform(put("/v1/roles").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateRoleData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit failure test case for updateRole by giving Existing role name
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateRoleFailureTestByExistingName() throws Exception {

        JSONObject updateRoleData = new JSONObject();

        updateRoleData.put("role_ID", "1");
        updateRoleData.put("name", "user");
        updateRoleData.put("administration_type", "1");
        updateRoleData.put("category_ID", "1");

        JSONArray permisssionarray = new JSONArray();

        JSONObject permissisonObject = new JSONObject();
        permissisonObject.put("category_ID", "1");

        JSONObject deleteOperationObject = new JSONObject();

        JSONArray permissionId = new JSONArray();
        permissionId.add(6);

        deleteOperationObject.put("permission_ID", permissionId);

        permissisonObject.put("delete_operation", deleteOperationObject);

        JSONObject insertOperationObject = new JSONObject();

        JSONArray operationId = new JSONArray();

        insertOperationObject.put("operation_ID", operationId);

        permissisonObject.put("insert_operation", insertOperationObject);

        permisssionarray.add(permissisonObject);

        updateRoleData.put("permissions", permisssionarray);

        this.mockMvc
                .perform(put("/v1/roles").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateRoleData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit test case for deleteRole
     * 
     * @PathParam role_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteRoleTest() throws Exception {
        Integer id = 4;

        this.mockMvc.perform(delete("/v1/roles/{role_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case using invalid ID for deleteRole
     * 
     * @PathParam role_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void deleteRoleFailureTest() throws Exception {
        int id = 0000;
        this.mockMvc.perform(delete("/v1/roles/{role_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for deleteRole by deleting Foriegn key relation
     * 
     * @PathParam role_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteRoleForiegnKeyTest() throws Exception {
        Integer id = 1;

        this.mockMvc.perform(delete("/v1/roles/{role_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit test case for getAllRoles
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllRolesTest() throws Exception {

        this.mockMvc.perform(get("/v1/roles")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getAllPermissionsByRole
     * 
     * @PathParam role_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllPermissionsByRoleTest() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/v1/roles/{id}/permissions/", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for getAllPermissionsByRole
     * 
     * @PathParam role_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllPermissionsByRoleFailureTest() throws Exception {
        int id = 0000;
        this.mockMvc.perform(get("/v1/roles/{id}/permissions/", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit test case for getAllPermissionsByRoleAndCategory
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllPermissionsByRoleAndCategoryTest() throws Exception {

        this.mockMvc.perform(get("/v1/roles/permissions?role_ID=1&category_ID=1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit Failure test case for getAllPermissionsByRoleAndCategory without
     * permissions.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllPermissionsByRoleAndCategoryFailureTest() throws Exception {

        this.mockMvc.perform(get("/v1/roles/permissions?role_ID=3&category_ID=1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(204));
    }

    /**
     * Junit Failure test case for getAllPermissionsByRoleAndCategory for
     * unavailable category.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllPermissionsByRoleAndCategoryFailureByUnavailableCategoryTest() throws Exception {

        this.mockMvc.perform(get("/v1/roles/permissions?role_ID=1&category_ID=15")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));
    }

}
