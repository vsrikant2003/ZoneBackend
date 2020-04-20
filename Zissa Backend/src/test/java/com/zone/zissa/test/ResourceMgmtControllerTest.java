package com.zone.zissa.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
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

public class ResourceMgmtControllerTest extends ZissaApplicationTest {

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
     * Junit test case for addResource
     * 
     * @throws Exception
     */
    @Ignore
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addResourceTest() throws Exception {

        JSONObject addResourceData = new JSONObject();

        addResourceData.put("category_ID", 1);
        addResourceData.put("user_ID", 1);

        JSONArray array = new JSONArray();
        JSONObject userData1 = new JSONObject();
        userData1.put("code", "CMB/LAP/0004");
        userData1.put("status_ID", 0);

        JSONArray array1 = new JSONArray();

        JSONObject resAttr = new JSONObject();
        resAttr.put("attribute_ID", 2);
        resAttr.put("value", "dell");
        array1.add(resAttr);
        userData1.put("resourceAttributes", array1);
        array.add(userData1);

        // next object

        JSONArray arrayNext = new JSONArray();
        JSONObject userData = new JSONObject();
        userData.put("code", "CMB/LAP/0005");
        userData.put("status_ID", 0);
        arrayNext.add(userData);
        JSONArray array3 = new JSONArray();

        JSONObject resAttr2 = new JSONObject();
        resAttr2.put("attribute_ID", 2);
        resAttr2.put("value", "SAMSUNG");
        array3.add(resAttr2);
        userData.put("resourceAttributes", array3);
        array.add(userData);

        addResourceData.put("resource", array);

        this.mockMvc.perform(post("/v1/resources").content(TestUtil.convertObjectToJsonString(addResourceData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(201));

    }

    /**
     * Junit Failure test case for addResource with existing data
     * 
     * @throws Exception
     */
    @Ignore
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addResourceFailureTest() throws Exception {

        JSONObject addResourceData = new JSONObject();

        addResourceData.put("category_ID", 1);
        addResourceData.put("user_ID", 1);

        JSONArray array = new JSONArray();
        JSONObject userData1 = new JSONObject();
        userData1.put("code", "CMB/LAP/0002");
        userData1.put("status_ID", 0);

        JSONArray array1 = new JSONArray();

        JSONObject resAttr = new JSONObject();
        resAttr.put("attribute_ID", 2);
        resAttr.put("value", "Samsung");
        array1.add(resAttr);
        userData1.put("resourceAttributes", array1);
        array.add(userData1);

        addResourceData.put("resource", array);
        System.out.println(addResourceData.toString());
        this.mockMvc
                .perform(post("/v1/resources").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addResourceData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));

    }

    /**
     * Junit access Denied Failure test case for addResource
     * 
     * @throws Exception
     */
    @Ignore
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addResourceAccessDeniedTest() throws Exception {

        JSONObject addResourceData = new JSONObject();

        addResourceData.put("category_ID", 2);
        addResourceData.put("user_ID", 1);

        JSONArray array = new JSONArray();
        JSONObject userData1 = new JSONObject();
        userData1.put("code", "CMB/OEQ/0001");
        userData1.put("status_ID", 0);

        JSONArray array1 = new JSONArray();

        JSONObject resAttr = new JSONObject();
        resAttr.put("attribute_ID", 2);
        resAttr.put("value", "Dell");
        array1.add(resAttr);
        userData1.put("resourceAttributes", array1);
        array.add(userData1);

        addResourceData.put("resource", array);

        this.mockMvc
                .perform(post("/v1/resources").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addResourceData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));

    }

    /**
     * Junit test case for updateResource
     * 
     * @throws Exception
     */
    @Ignore
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateResourceTest() throws Exception {
        JSONObject updateResourceData = new JSONObject();

        updateResourceData.put("resource_ID", 1);
        updateResourceData.put("user_ID", 1);
        updateResourceData.put("status_ID", 0);

        JSONArray array = new JSONArray();
        JSONArray array1 = new JSONArray();

        JSONObject resAttr = new JSONObject();
        resAttr.put("resource_Attribute_ID", 1);
        resAttr.put("value", "Dell");
        array1.add(resAttr);

        updateResourceData.put("resourceAttributes", array1);
        array.add(updateResourceData);

        this.mockMvc
                .perform(put("/v1/resources").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit Failure test case for updateAttribute for Bad request
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateResourceFailureTest() throws Exception {
        JSONObject updateResourceData = new JSONObject();

        updateResourceData.put("resource_ID", 2);
        updateResourceData.put("user_ID", 1);
        updateResourceData.put("status_ID", 0);

        JSONArray array = new JSONArray();
        JSONArray array1 = new JSONArray();

        JSONObject resAttr = new JSONObject();
        resAttr.put("resource_Attribute_ID", 1);
        resAttr.put("value", "Dell");
        array1.add(resAttr);
        JSONObject resAttr2 = new JSONObject();
        resAttr2.put("resource_Attribute_ID", 2);
        resAttr2.put("value", "Dell");
        array1.add(resAttr2);
        updateResourceData.put("resourceAttributes", array1);
        array.add(updateResourceData);
        System.out.println(array);

        this.mockMvc
                .perform(put("/v1/resources").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Junit access Denied Failure test case for updateAttribute.
     * 
     * @throws Exception
     */
    @Ignore
    @Test
    @Transactional
    @WithMockUser(username = f_Username, password = f_Password)
    public void updateResourceAccessDeniedTest() throws Exception {
        JSONObject updateResourceData = new JSONObject();

        updateResourceData.put("resource_ID", 1);
        updateResourceData.put("user_ID", 1);
        updateResourceData.put("status_ID", 0);

        JSONArray array = new JSONArray();
        JSONArray array1 = new JSONArray();

        JSONObject resAttr = new JSONObject();
        resAttr.put("resource_Attribute_ID", 1);
        resAttr.put("value", "Samsung");
        array1.add(resAttr);

        updateResourceData.put("resourceAttributes", array1);
        array.add(updateResourceData);

        this.mockMvc
                .perform(put("/v1/resources").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));
    }

    /**
     * Junit Failure test case for deleteResource by resource_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void deleteResourceTest() throws Exception {

        this.mockMvc.perform(delete("/v1/resources/{resource_ID}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for deleteResource with invalid resurce_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void deleteResourceFailureTest() throws Exception {

        this.mockMvc.perform(delete("/v1/resources/{resource_ID}", 10)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit access Denied Failure test case for deleteResource by resource_ID
     * 
     * @throws Exception
     */
    @Test
    @WithMockUser(username = f_Username, password = f_Password)
    public void deleteResourceAccessDeniedTest() throws Exception {

        this.mockMvc.perform(delete("/v1/resources/{resource_ID}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));
    }

    /**
     * Junit Failure test case to deleteResource for allocatedResource.
     * 
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteResourceByAllocatedResourceTest() throws Exception {

        this.mockMvc.perform(delete("/v1/resources/{resource_ID}", 7)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));
    }

    /**
     * Junit test case for disposeResources
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void disposeResourcesTest() throws Exception {

        JSONArray array = new JSONArray();
        JSONObject resourceData1 = new JSONObject();
        resourceData1.put("resource_ID", 1);
        resourceData1.put("reason", "not required");
        array.add(resourceData1);
        this.mockMvc
                .perform(delete("/v1/resources/dispose").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit access Denied Failure test case for disposeResources
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = f_Username, password = f_Password)
    public void disposeResourcesFailureTest() throws Exception {
        JSONArray array = new JSONArray();
        JSONObject resourceData1 = new JSONObject();
        resourceData1.put("resource_ID", 1);
        resourceData1.put("reason", "not required");
        array.add(resourceData1);
        this.mockMvc
                .perform(delete("/v1/resources/dispose").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));
    }

    /**
     * Junit Failure test case to disposeResources for Allocated Resources.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void disposeResourcesByAllocateResourceTest() throws Exception {
        JSONArray array = new JSONArray();
        JSONObject resourceData = new JSONObject();
        resourceData.put("resource_ID", 7);
        resourceData.put("reason", "not required");
        array.add(resourceData);
        this.mockMvc
                .perform(delete("/v1/resources/dispose").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));
    }

    /**
     * Junit Failure test case for disposeResources for Not found
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void disposeResourcesFailureByNotFoundTest() throws Exception {

        JSONArray array = new JSONArray();
        JSONObject resourceData1 = new JSONObject();
        resourceData1.put("resource_ID", 10);
        resourceData1.put("reason", "not required");
        JSONObject resourceData2 = new JSONObject();
        resourceData2.put("resource_ID", 11);
        resourceData2.put("reason", "no longer needed");
        array.add(resourceData1);
        array.add(resourceData2);
        this.mockMvc
                .perform(delete("/v1/resources/dispose").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));
    }

    /**
     * Junit test case for restoreResources
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void restoreResourcesTest() throws Exception {

        this.mockMvc.perform(delete("/v1/resources/restore?resource_ID=1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit Failure test case for restoreResources Not found
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void restoreResourcesFailureTest() throws Exception {

        this.mockMvc.perform(delete("/v1/resources/restore?resource_ID=10,11")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));
    }

    /**
     * Junit Access denied Failure test case for restoreResources.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = f_Username, password = f_Password)
    public void restoreResourcesAccessDeniedTest() throws Exception {

        this.mockMvc.perform(delete("/v1/resources/restore?resource_ID=1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));
    }

    /**
     * Junit test case for getLastResourceByCategory
     * 
     * @PathParam categoryID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getLastResourceByCategoryTest() throws Exception {
        int categoryID = 1;
        this.mockMvc.perform(get("/v1/resources/lastresource/{categoryID}", categoryID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit failure test case for getLastResourceByCategory
     * 
     * @PathParam categoryID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getLastResourceByCategoryFailureTest() throws Exception {
        int categoryID = 00;
        this.mockMvc.perform(get("/v1/resources/lastresource/{categoryID}", categoryID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit test case for getDisposedResourcesByCategoryId
     * 
     * @PathParam categoryID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourcesByCategoryIdTest() throws Exception {
        int categoryID = 1;
        this.mockMvc.perform(get("/v1/resources/disposed/{categoryID}", categoryID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit failure test case for getDisposedResourcesByCategoryId by invalid
     * category_ID
     * 
     * @PathParam categoryID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourcesByCategoryIdFailureTest() throws Exception {
        int categoryID = 000;
        this.mockMvc.perform(get("/v1/resources/disposed/{categoryID}", categoryID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit failure test case for getDisposedResourcesByCategoryId by invalid data
     * 
     * @PathParam categoryID
     * @throws Exception
     */
    @Test
    @Transactional
    public void getDisposedResourcesByCategoryIdTestByInvalidData() throws Exception {
        int categoryID = 1;
        this.mockMvc.perform(get("/v1/resources/disposed/{categoryID}", categoryID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit failure test case for getDisposedResourcesByCategoryId for Access
     * denied
     * 
     * @PathParam categoryID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourcesByCategoryIdTestAccessDenied() throws Exception {
        int categoryID = 2;
        this.mockMvc.perform(get("/v1/resources/disposed/{categoryID}", categoryID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));

    }

    /**
     * Junit test case for getDisposedResource
     * 
     * @PathParam resource_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourceTest() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/v1/resources/disposedobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for getDisposedResource for Not Found
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourceFailureTest() throws Exception {
        int id = 000;
        this.mockMvc.perform(get("/v1/resources/disposedobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit Failure test case for getDisposedResource for invalidData
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void getDisposedResourceFailureTestByInvalidData() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/v1/resources/disposedobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit Failure test case for getDisposedResource for Access Denied
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourceFailureTestAccessDenied() throws Exception {
        int id = 2;
        this.mockMvc.perform(get("/v1/resources/disposedobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));

    }

    /**
     * Junit test case for getResourcesbyResourceIdList by sending list of
     * resource_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesbyResourceIdListTest() throws Exception {

        this.mockMvc.perform(get("/v1/resources/resourceobj?resource_ID=1,2")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getResourcesbyResourceIdList by sending list of invalid
     * resource_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesbyResourceIdListFailureTest() throws Exception {

        this.mockMvc.perform(get("/v1/resources/resourceobj?resource_ID=000,1000")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit test case for getAllResourcesByCategoryId
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllResourcesByCategoryIdTest() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/v1/resources/{category_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for getAllResourcesByCategoryId invalid category_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllResourcesByCategoryIdFailureTest() throws Exception {
        int id = 000;
        this.mockMvc.perform(get("/v1/resources/{category_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit Failure test case for getAllResourcesByCategoryId by invalid Data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void getAllResourcesByCategoryIdFailureTestByInvalidData() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/v1/resources/{category_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit failure test case for getAllResourcesByCategoryId for Access Denied
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllResourcesByCategoryIdFailureTestAccessDenied() throws Exception {
        int id = 2;
        this.mockMvc.perform(get("/v1/resources/{category_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));

    }

    /**
     * Junit test case for getResourcesBySearchTerm
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesBySearchTermTest() throws Exception {

        String category = "1";
        String page = "0";
        String size = "0";
        String searchText = "Samsung";
        String direction = "desc";
        String attribute = "2";

        this.mockMvc
                .perform(get("/v1/resources").param("category_ID", category).param("page", page).param("size", size)
                        .param("searchText", searchText).param("direction", direction).param("attrid", attribute))

                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.totalRecords").value(1));

    }

    /**
     * Junit failure test case for getResourcesBySearchTerm invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesBySearchTermFailureTest() throws Exception {

        String category = "0";
        String page = "1";
        String size = "10";
        String searchText = "laptop";
        String direction = "desc";
        String attribute = "0000";

        this.mockMvc
                .perform(get("/v1/resources").param("category_ID", category).param("page", page).param("size", size)
                        .param("searchText", searchText).param("direction", direction).param("attrid", attribute))

                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").isEmpty()).andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.totalRecords").value(0));
    }

    /**
     * Junit test case for getDisposedResourcesBySearchTerm
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourcesBySearchTermTest() throws Exception {

        String category = "1";
        String page = "1";
        String size = "10";
        String searchText = "laptop";
        String direction = "desc";
        String attribute = "2";

        this.mockMvc
                .perform(get("/v1/resources/disposed").param("category_ID", category).param("page", page)
                        .param("size", size).param("searchText", searchText).param("direction", direction)
                        .param("attrid", attribute))

                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").isEmpty()).andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.totalRecords").value(1));
    }

    /**
     * Junit failure test case for getDisposedResourcesBySearchTerm by invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getDisposedResourcesBySearchTermFailureTest() throws Exception {

        String category = "00";
        String page = "1";
        String size = "10";
        String searchText = "laptop";
        String direction = "desc";
        String attribute = "0000";

        this.mockMvc
                .perform(get("/v1/resources/disposed").param("category_ID", category).param("page", page)
                        .param("size", size).param("searchText", searchText).param("direction", direction)
                        .param("attrid", attribute))

                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").isEmpty()).andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.totalRecords").value(0));
    }

    /**
     * Junit test case for getResource
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourceTest() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/v1/resources/resourceobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for getResource with invalid resource_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourceFailureTest() throws Exception {
        int id = 000;
        this.mockMvc.perform(get("/v1/resources/resourceobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit Failure test case for getResource for Access Denied
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourceFailureTestByAccessDenied() throws Exception {
        int id = 3;
        this.mockMvc.perform(get("/v1/resources/resourceobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));

    }

    /**
     * Junit failure test case for getResource by invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void getResourceFailureTestInvalidData() throws Exception {
        int id = 1;
        this.mockMvc.perform(get("/v1/resources/resourceobj/{resource_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit test case for resourceAllocation
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void resourceAllocationTest() throws Exception {

        JSONArray array = new JSONArray();
        JSONObject allocateResource = new JSONObject();
        allocateResource.put("allocationtype_ID", 3);
        allocateResource.put("resource_ID", 6);
        allocateResource.put("status_ID", 1);
        allocateResource.put("user_ID", 1);

        JSONObject otherAllocations = new JSONObject();
        otherAllocations.put("assignee_Name", "Nikhil");
        allocateResource.put("otherAllocations", otherAllocations);

        array.add(allocateResource);

        this.mockMvc.perform(post("/v1/resources/allocation").content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit test case for resourceAllocation for update already existing one
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void resourceAllocationForExistingOneTest() throws Exception {

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("allocation_ID", 1);
        obj.put("allocationtype_ID", 3);
        obj.put("resource_ID", 7);
        obj.put("status_ID", 2);
        obj.put("user_ID", 1);

        JSONObject otherAllocations = new JSONObject();
        otherAllocations.put("assignee_Name", "AmaliK");

        obj.put("otherAllocations", otherAllocations);
        array.add(obj);

        this.mockMvc.perform(post("/v1/resources/allocation").content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for resourceAllocation for Parent child relation
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void resourceAllocationFailureTest() throws Exception {

        JSONArray array = new JSONArray();
        JSONObject allocateResource = new JSONObject();
        allocateResource.put("allocationtype_ID", 3);
        allocateResource.put("resource_ID", 7);
        allocateResource.put("status_ID", 1);
        allocateResource.put("user_ID", 1);

        JSONObject otherAllocations = new JSONObject();
        otherAllocations.put("assignee_Name", "BathiyaT");
        allocateResource.put("otherAllocations", otherAllocations);

        array.add(allocateResource);

        this.mockMvc.perform(post("/v1/resources/allocation").content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit Failure test case for resourceAllocation for Access denied
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = f_Username, password = f_Password)
    public void resourceAllocationFailureTestAccessDenied() throws Exception {

        JSONArray array = new JSONArray();
        JSONObject allocateResource = new JSONObject();
        allocateResource.put("allocationtype_ID", 3);
        allocateResource.put("resource_ID", 7);
        allocateResource.put("status_ID", 2);
        allocateResource.put("user_ID", 1);

        JSONObject otherAllocations = new JSONObject();
        otherAllocations.put("assignee_Name", "");
        allocateResource.put("otherAllocations", otherAllocations);

        array.add(allocateResource);
        System.out.println(array.toString());

        this.mockMvc.perform(post("/v1/resources/allocation").content(TestUtil.convertObjectToJsonString(array)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));

    }

    /**
     * Junit failure test case for resourceAllocation for bad request - invalid
     * resourceId
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void resourceAllocationFailureTestByInvalidId() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("allocationtype_ID", 3);
        obj.put("resource_ID", 123456);
        obj.put("status_ID", 1);
        obj.put("user_ID", 1);

        JSONObject otherAllocations = new JSONObject();
        otherAllocations.put("assignee_Name", "Nikhil");
        obj.put("otherAllocations", otherAllocations);

        this.mockMvc.perform(post("/v1/resources/allocation").content(TestUtil.convertObjectToJsonString(obj)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));

    }

}
