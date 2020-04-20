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

public class CategoryMgmtControllerTest extends ZissaApplicationTest {

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
     * Junit test case for getAllCategories
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllCategoriesTest() throws Exception {

        this.mockMvc.perform(get("/v1/categories")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getCategoryAttributesByCategoryId
     * 
     * @PathParam category_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getCategoryAttributeByIdTest() throws Exception {
        Integer category_ID = 1;
        this.mockMvc.perform(get("/v1/categories/{category_ID}/attributes", +category_ID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for getCategoryAttributesByCategoryId by invalid id
     * 
     * @PathParam category_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getCategoryAttributesByInvalidIdTest() throws Exception {
        Integer id = 879;
        this.mockMvc.perform(get("/v1/categories/{id}/attributes", +id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit Failure test case for getCategoryAttributesByCategoryId
     * 
     * @PathParam category_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getCategoryAttributeByIdFailureTest() throws Exception {
        Integer category_ID = 2;
        this.mockMvc.perform(get("/v1/categories/{category_ID}/attributes", +category_ID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(204));

    }

    /**
     * Junit test case for getCategoryAttributesByCategoryIdList
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getCategoryAttributesByCategoryIdListTest() throws Exception {

        this.mockMvc.perform((get("/v1/categories/attributes")).param("category_ID", "1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit failure test case for getCategoryAttributesByCategoryIdList by invalid
     * category_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getCategoryAttributesByCategoryIdListFailureTest() throws Exception {

        this.mockMvc.perform((get("/v1/categories/attributes")).param("category_ID", "00")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))

                .andExpect(jsonPath("$.status").value(204));

    }

    /**
     * Junit test case for getAllOperations
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllOperationsTest() throws Exception {

        this.mockMvc.perform(get("/v1/categories/operations")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for deleteCategory
     * 
     * @PathParam category_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteCategoryTest() throws Exception {
        int id = 3;
        this.mockMvc.perform(delete("/v1/categories/{category_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for deleteCategory
     * 
     * @PathParam category_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void deleteCategoryFailureTest() throws Exception {
        int id = 000;
        this.mockMvc.perform(delete("/v1/categories/{category_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for deleteCategory by deleting Foriegn key relation
     * 
     * @PathParam category_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteCategoryFailureTestByForiegnKey() throws Exception {
        int id = 1;
        this.mockMvc.perform(delete("/v1/categories/{category_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit test case for addCategory
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addCategoryTest() throws Exception {

        JSONObject addCategoryData = new JSONObject();

        addCategoryData.put("name", "sampleCategory");
        addCategoryData.put("code_Pattern", "CAT/MON");
        addCategoryData.put("user_ID", 1);

        JSONArray categoryAttributesArray = new JSONArray();

        JSONObject AttributeObject1 = new JSONObject();
        AttributeObject1.put("attribute_ID", 1);
        AttributeObject1.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject1);

        JSONObject AttributeObject2 = new JSONObject();
        AttributeObject2.put("attribute_ID", 2);
        AttributeObject2.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject2);

        JSONObject AttributeObject3 = new JSONObject();
        AttributeObject3.put("attribute_ID", 3);
        AttributeObject3.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject3);

        JSONObject AttributeObject4 = new JSONObject();
        AttributeObject4.put("attribute_ID", 4);
        AttributeObject4.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject4);

        addCategoryData.put("categoryAttributes", categoryAttributesArray);

        this.mockMvc
                .perform(post("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addCategoryData)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(201));
    }

    /**
     * Junit Failure test case for addCategory by giving existing data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addCategoryFailuteTest() throws Exception {
        JSONObject addCategoryData = new JSONObject();

        addCategoryData.put("name", "Laptops");
        addCategoryData.put("code_Pattern", "CMB/LAP");
        addCategoryData.put("user_ID", 1);

        JSONArray categoryAttributesArray = new JSONArray();

        JSONObject AttributeObject1 = new JSONObject();
        AttributeObject1.put("attribute_ID", 1);
        AttributeObject1.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject1);

        JSONObject AttributeObject2 = new JSONObject();
        AttributeObject2.put("attribute_ID", 2);
        AttributeObject2.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject2);

        JSONObject AttributeObject3 = new JSONObject();
        AttributeObject3.put("attribute_ID", 3);
        AttributeObject3.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject3);

        JSONObject AttributeObject4 = new JSONObject();
        AttributeObject4.put("attribute_ID", 4);
        AttributeObject4.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject4);

        addCategoryData.put("categoryAttributes", categoryAttributesArray);

        this.mockMvc
                .perform(post("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addCategoryData)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit Failure test case for addCategory by giving invalid user_ID
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void addCategoryFailuteTestByInvalidUser() throws Exception {

        JSONObject addCategoryData = new JSONObject();

        addCategoryData.put("name", "sampleCategory");
        addCategoryData.put("code_Pattern", "CAT/MON");
        addCategoryData.put("user_ID", 000);

        JSONArray categoryAttributesArray = new JSONArray();

        JSONObject AttributeObject1 = new JSONObject();
        AttributeObject1.put("attribute_ID", 1);
        AttributeObject1.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject1);

        JSONObject AttributeObject2 = new JSONObject();
        AttributeObject2.put("attribute_ID", 2);
        AttributeObject2.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject2);

        JSONObject AttributeObject3 = new JSONObject();
        AttributeObject3.put("attribute_ID", 3);
        AttributeObject3.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject3);

        JSONObject AttributeObject4 = new JSONObject();
        AttributeObject4.put("attribute_ID", 4);
        AttributeObject4.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject4);

        addCategoryData.put("categoryAttributes", categoryAttributesArray);
        this.mockMvc
                .perform(post("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addCategoryData)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit test case for addCategory by invalid json object
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addCategoryByInvalidJsonTest() throws Exception {

        JSONObject addCategoryData = new JSONObject();

        addCategoryData.put("Name", "sampleCategory");
        addCategoryData.put("code_Pattern", "CAT/MON");
        addCategoryData.put("user_ID", 1);

        JSONArray categoryAttributesArray = new JSONArray();

        JSONObject AttributeObject1 = new JSONObject();
        AttributeObject1.put("attribute_ID", 1);
        AttributeObject1.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject1);

        JSONObject AttributeObject2 = new JSONObject();
        AttributeObject2.put("attribute_ID", 2);
        AttributeObject2.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject2);

        JSONObject AttributeObject3 = new JSONObject();
        AttributeObject3.put("attribute_ID", 3);
        AttributeObject3.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject3);

        JSONObject AttributeObject4 = new JSONObject();
        AttributeObject4.put("attribute_ID", 4);
        AttributeObject4.put("isDefault", 0);
        categoryAttributesArray.add(AttributeObject4);

        addCategoryData.put("categoryAttributes", categoryAttributesArray);

        this.mockMvc
                .perform(post("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addCategoryData)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Junit test case for updateCategory
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateCategoryTest() throws Exception {

        JSONObject updateCategoryData = new JSONObject();
        updateCategoryData.put("category_ID", 1);
        updateCategoryData.put("name", "Laptops");
        updateCategoryData.put("user_ID", 1);

        JSONArray deleteArray = new JSONArray();

        JSONArray insertArray = new JSONArray();

        JSONArray updateArray = new JSONArray();
        JSONObject updateObject = new JSONObject();
        updateObject.put("category_Attribute_ID", 1);
        updateObject.put("isDefault", 0);
        updateArray.add(updateObject);

        updateCategoryData.put("delete_categoryAttributes", deleteArray);
        updateCategoryData.put("insert_categoryAttributes", insertArray);
        updateCategoryData.put("update_defaultAttributes", updateArray);
        this.mockMvc
                .perform(put("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateCategoryData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit Failure test case for updateCategory by invalid id
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateCategoryFailureTestByInvalidId() throws Exception {

        JSONObject updateCategoryData = new JSONObject();
        updateCategoryData.put("category_ID", 000);
        updateCategoryData.put("name", "Laptops");
        updateCategoryData.put("user_ID", 1);

        JSONArray deleteArray = new JSONArray();

        JSONArray insertArray = new JSONArray();

        JSONArray updateArray = new JSONArray();
        JSONObject updateObject = new JSONObject();
        updateObject.put("category_Attribute_ID", 1);
        updateObject.put("isDefault", 0);
        updateArray.add(updateObject);

        updateCategoryData.put("delete_categoryAttributes", deleteArray);
        updateCategoryData.put("insert_categoryAttributes", insertArray);
        updateCategoryData.put("update_defaultAttributes", updateArray);
        this.mockMvc
                .perform(put("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateCategoryData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit Failure test case for updateCategory by invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateCategoryFailureTestByInvalidData() throws Exception {

        JSONObject updateCategoryData = new JSONObject();
        updateCategoryData.put("category_ID", 1);
        updateCategoryData.put("name", "Laptops");
        updateCategoryData.put("user_ID", 00);

        JSONArray deleteArray = new JSONArray();

        JSONArray insertArray = new JSONArray();

        JSONArray updateArray = new JSONArray();
        JSONObject updateObject = new JSONObject();
        updateObject.put("category_Attribute_ID", 00);
        updateObject.put("isDefault", 0);
        updateArray.add(updateObject);

        updateCategoryData.put("delete_categoryAttributes", deleteArray);
        updateCategoryData.put("insert_categoryAttributes", insertArray);
        updateCategoryData.put("update_defaultAttributes", updateArray);
        this.mockMvc
                .perform(put("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateCategoryData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));
    }

    /**
     * Junit test case for updateCategory by invalid json object
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateCategoryByInvalidJsonTest() throws Exception {

        JSONObject updateCategoryData = new JSONObject();
        updateCategoryData.put("Category_ID", 1);
        updateCategoryData.put("name", "Laptops");
        updateCategoryData.put("user_ID", 1);

        JSONArray deleteArray = new JSONArray();

        JSONArray insertArray = new JSONArray();

        JSONArray updateArray = new JSONArray();
        JSONObject updateObject = new JSONObject();
        updateObject.put("category_Attribute_ID", 1);
        updateObject.put("isDefault", 0);
        updateArray.add(updateObject);

        updateCategoryData.put("delete_categoryAttributes", deleteArray);
        updateCategoryData.put("insert_categoryAttributes", insertArray);
        updateCategoryData.put("update_defaultAttributes", updateArray);
        this.mockMvc
                .perform(put("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateCategoryData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Junit test case for updateCategory by giving existing name
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateCategoryByExistingNameTest() throws Exception {

        JSONObject updateCategoryData = new JSONObject();
        updateCategoryData.put("category_ID", 1);
        updateCategoryData.put("name", "Office Equipments");
        updateCategoryData.put("user_ID", 1);

        JSONArray deleteArray = new JSONArray();

        JSONArray insertArray = new JSONArray();

        JSONArray updateArray = new JSONArray();
        JSONObject updateObject = new JSONObject();
        updateObject.put("category_Attribute_ID", 1);
        updateObject.put("isDefault", 0);
        updateArray.add(updateObject);

        updateCategoryData.put("delete_categoryAttributes", deleteArray);
        updateCategoryData.put("insert_categoryAttributes", insertArray);
        updateCategoryData.put("update_defaultAttributes", updateArray);
        this.mockMvc
                .perform(put("/v1/categories").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(updateCategoryData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));
    }

    /**
     * Junit test case for getAllCategoriesWithViewPermissions
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllCategoriesWithViewPermissionsTest() throws Exception {

        this.mockMvc.perform(get("/v1/categories/viewpermissions")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getAllCategoriesWithAddPermissions
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllCategoriesWithAddPermissionsTest() throws Exception {

        this.mockMvc.perform(get("/v1/categories/addpermissions")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getAllCategoriesWithDisposePermissions
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllCategoriesWithDisposePermissionsTest() throws Exception {

        this.mockMvc.perform(get("/v1/categories/disposepermissions")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getAllCategoriesWithAllocatePermissions
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllCategoriesWithAllocatePermissionsTest() throws Exception {

        this.mockMvc.perform(get("/v1/categories/allocatepermissions")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

}
