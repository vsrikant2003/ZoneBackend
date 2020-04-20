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

public class AttributeMgmtControllerTest extends ZissaApplicationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    // for success test cases authentication
    private static final String username = "BathiyaT";
    private static final String password = "Zone@789";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Junit test case for getAllAttributes
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllAttributesTest() throws Exception {

        this.mockMvc.perform(get("/v1/attributes")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getAllAttributeDataTypes
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAllAttributeDataTypesTest() throws Exception {

        this.mockMvc.perform(get("/v1/attributes/attributeDataTypes")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for getAttributeInfoById
     * 
     * @PathParam attribute_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAttributeInfoByIdTest() throws Exception {
        Integer id = 1;
        this.mockMvc.perform(get("/v1/attributes/" + id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for getAttributeInfoById invalid attribute_ID
     * 
     * @PathParam attribute_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getAttributeInfoByIdFailureTest() throws Exception {
        int id = 0000;
        this.mockMvc.perform(get("/v1/attributes/" + id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit test case for addAttribute
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addAttributeTest() throws Exception {
        String name = "Desktop";
        int dropDownVal = 1;
        int attrDataType = 1;
        int userId = 1;
        JSONObject addAttributeData = new JSONObject();

        addAttributeData.put("name", name);
        addAttributeData.put("dropdowncontrolval", dropDownVal);
        addAttributeData.put("attrDataType", attrDataType);
        addAttributeData.put("user_ID", userId);

        JSONArray attributeValueArray = new JSONArray();
        JSONObject valueObeject = new JSONObject();
        valueObeject.put("value", "14.5 inch laptop");
        attributeValueArray.add(valueObeject);

        JSONObject value = new JSONObject();
        value.put("value", "15.5 inch desktop");
        attributeValueArray.add(value);

        addAttributeData.put("attributeValues", attributeValueArray);

        this.mockMvc
                .perform(post("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addAttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(201));

    }

    /**
     * Junit Failure test case for addAttribute by existing data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addAttributeFailureTest() throws Exception {
        String name = "Office EQ Make";
        int dropDownVal = 1;
        int attrDataType = 1;
        int userId = 1;

        JSONObject addAttributeData = new JSONObject();

        addAttributeData.put("name", name);
        addAttributeData.put("dropdowncontrolval", dropDownVal);
        addAttributeData.put("attrDataType", attrDataType);
        addAttributeData.put("user_ID", userId);

        JSONArray attributeValueArray = new JSONArray();
        JSONObject valueObject = new JSONObject();
        valueObject.put("value", "14.5 inch laptop");
        attributeValueArray.add(valueObject);

        JSONObject value = new JSONObject();
        value.put("value", "15.5 inch desktop");
        attributeValueArray.add(value);

        addAttributeData.put("attributeValues", attributeValueArray);

        this.mockMvc
                .perform(post("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addAttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit Failure test case for addAttribute by invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addAttributeFailureTestByInvalidData() throws Exception {
        String name = "Desktop";
        String dropDownVal = "Screen-Size";
        int attrDataType = 1;
        String userId = "435fc3";
        JSONObject addAttributeData = new JSONObject();

        addAttributeData.put("name", name);
        addAttributeData.put("dropdowncontrolval", dropDownVal);
        addAttributeData.put("attrDataType", attrDataType);
        addAttributeData.put("user_ID", userId);

        JSONArray attributeValueArray = new JSONArray();
        JSONObject valueObject = new JSONObject();
        valueObject.put("value", "14.5 inch laptop");
        attributeValueArray.add(valueObject);

        JSONObject value = new JSONObject();
        value.put("value", "15.5 inch desktop");
        attributeValueArray.add(value);

        addAttributeData.put("attributeValues", attributeValueArray);

        this.mockMvc
                .perform(post("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addAttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(500));

    }

    /**
     * Junit test case for addAttribute by invalid json object
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void addAttributeByInvalidJsonTest() throws Exception {
        String name = "Desktop";
        int dropDownVal = 1;
        int attrDataType = 1;
        int userId = 1;
        JSONObject addAttributeData = new JSONObject();

        addAttributeData.put("Name", name);
        addAttributeData.put("dropdowncontrolval", dropDownVal);
        addAttributeData.put("attrDataType", attrDataType);
        addAttributeData.put("user_ID", userId);

        JSONArray attributeValueArray = new JSONArray();
        JSONObject valueObeject = new JSONObject();
        valueObeject.put("value", "14.5 inch laptop");
        attributeValueArray.add(valueObeject);

        JSONObject value = new JSONObject();
        value.put("value", "15.5 inch desktop");
        attributeValueArray.add(value);

        addAttributeData.put("attributeValues", attributeValueArray);

        this.mockMvc
                .perform(post("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(addAttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));

    }

    /**
     * Junit test case for updateAttribute
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateAttributeTest() throws Exception {
        Integer id = 1;
        String name = "Office EQ Make";
        int dropDownVal = 1;
        int attrDataType = 1;
        int userId = 1;
        String attributeValueID = "6";
        String attributeValueID_2 = "2";

        JSONObject AttributeData = new JSONObject();
        AttributeData.put("attribute_ID", id);
        AttributeData.put("name", name);
        AttributeData.put("dropdown", dropDownVal);
        AttributeData.put("data_Type_ID", attrDataType);
        AttributeData.put("user_ID", userId);

        JSONArray deleteAttributrArray = new JSONArray();
        JSONObject AttributeObject = new JSONObject();
        AttributeObject.put("attribute_Value_ID", attributeValueID);
        deleteAttributrArray.add(AttributeObject);

        JSONArray insertAttributeArray = new JSONArray();
        JSONObject valueObject = new JSONObject();
        valueObject.put("value", "12.5 inch");
        insertAttributeArray.add(valueObject);

        JSONArray attributeArray = new JSONArray();
        JSONObject AttributeValueObject = new JSONObject();
        AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
        AttributeValueObject.put("value", "15.5 inch");
        attributeArray.add(AttributeValueObject);

        AttributeData.put("delete_attributeValues", deleteAttributrArray);
        AttributeData.put("insert_attributeValues", insertAttributeArray);
        AttributeData.put("attributeValues", attributeArray);

        this.mockMvc
                .perform(put("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(AttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for updateAttribute by invalid id
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateAttributeFailureTestByInvalidId() throws Exception {
        Integer id = 123;
        String name = "Office EQ Make";
        int dropDownVal = 1;
        int attrDataType = 4;
        int userId = 1;
        String attributeValueID = "26";
        String attributeValueID_2 = "27";

        JSONObject AttributeData = new JSONObject();
        AttributeData.put("attribute_ID", id);
        AttributeData.put("name", name);
        AttributeData.put("dropdown", dropDownVal);
        AttributeData.put("data_Type_ID", attrDataType);
        AttributeData.put("user_ID", userId);

        JSONArray deleteAttributrArray = new JSONArray();
        JSONObject AttributeObject = new JSONObject();
        AttributeObject.put("attribute_Value_ID", attributeValueID);
        deleteAttributrArray.add(AttributeObject);

        JSONArray insertAttributeArray = new JSONArray();
        JSONObject valueObject = new JSONObject();
        valueObject.put("value", "12.5 inch");
        insertAttributeArray.add(valueObject);

        JSONArray attributeArray = new JSONArray();
        JSONObject AttributeValueObject = new JSONObject();
        AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
        AttributeValueObject.put("value", "15.5 inch");
        attributeArray.add(AttributeValueObject);

        AttributeData.put("delete_attributeValues", deleteAttributrArray);
        AttributeData.put("insert_attributeValues", insertAttributeArray);
        AttributeData.put("attributeValues", attributeArray);

        this.mockMvc
                .perform(put("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(AttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case for updateAttribute by invalid data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateAttributeFailureTestByInvalidData() throws Exception {
        Integer id = 1;
        String name = "Office EQ Make";
        int dropDownVal = 1;
        int attrDataType = 10;
        int userId = 1;
        String attributeValueID = "26";
        String attributeValueID_2 = "27";

        JSONObject AttributeData = new JSONObject();
        AttributeData.put("attribute_ID", id);
        AttributeData.put("name", name);
        AttributeData.put("dropdown", dropDownVal);
        AttributeData.put("data_Type_ID", attrDataType);
        AttributeData.put("user_ID", userId);

        JSONArray deleteAttributrArray = new JSONArray();
        JSONObject AttributeObject = new JSONObject();
        AttributeObject.put("attribute_Value_ID", attributeValueID);
        deleteAttributrArray.add(AttributeObject);

        JSONArray insertAttributeArray = new JSONArray();
        JSONObject valueObject = new JSONObject();
        valueObject.put("value", "12.5 inch");
        insertAttributeArray.add(valueObject);

        JSONArray attributeArray = new JSONArray();
        JSONObject AttributeValueObject = new JSONObject();
        AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
        AttributeValueObject.put("value", "15.5 inch");
        attributeArray.add(AttributeValueObject);

        AttributeData.put("delete_attributeValues", deleteAttributrArray);
        AttributeData.put("insert_attributeValues", insertAttributeArray);
        AttributeData.put("attributeValues", attributeArray);

        this.mockMvc
                .perform(put("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(AttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(400));

    }

    /**
     * Junit test case for updateAttribute by existing attribute name
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void updateAttributeByExistingNameTest() throws Exception {
        Integer id = 1;
        String name = "Models";
        int dropDownVal = 1;
        int attrDataType = 1;
        int userId = 1;
        String attributeValueID = "6";
        String attributeValueID_2 = "2";

        JSONObject AttributeData = new JSONObject();
        AttributeData.put("attribute_ID", id);
        AttributeData.put("name", name);
        AttributeData.put("dropdown", dropDownVal);
        AttributeData.put("data_Type_ID", attrDataType);
        AttributeData.put("user_ID", userId);

        JSONArray deleteAttributrArray = new JSONArray();
        JSONObject AttributeObject = new JSONObject();
        AttributeObject.put("attribute_Value_ID", attributeValueID);
        deleteAttributrArray.add(AttributeObject);

        JSONArray insertAttributeArray = new JSONArray();
        JSONObject valueObject = new JSONObject();
        valueObject.put("value", "12.5 inch");
        insertAttributeArray.add(valueObject);

        JSONArray attributeArray = new JSONArray();
        JSONObject AttributeValueObject = new JSONObject();
        AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
        AttributeValueObject.put("value", "15.5 inch");
        attributeArray.add(AttributeValueObject);

        AttributeData.put("delete_attributeValues", deleteAttributrArray);
        AttributeData.put("insert_attributeValues", insertAttributeArray);
        AttributeData.put("attributeValues", attributeArray);

        this.mockMvc
                .perform(put("/v1/attributes").contentType("application/json;charset=UTF-8")
                        .content(TestUtil.convertObjectToJsonString(AttributeData)))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

    /**
     * Junit test case for deleteAttribute
     * 
     * @PathParam attribute_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteAttributeTest() throws Exception {
        Integer id = 6;

        this.mockMvc.perform(delete("/v1/attributes/{attribute_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit Failure test case using unavailable ID for deleteAttribute
     * 
     * @PathParam attribute_ID
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void deleteAttributeFailureTest() throws Exception {
        int id = 000;
        this.mockMvc.perform(delete("/v1/attributes/{attribute_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit test case for deleteAttribute by deleting Foriegn key relation
     * 
     * @PathParam attribute_ID
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void deleteAttributeFailureTestByForiegnKey() throws Exception {
        Integer id = 1;

        this.mockMvc.perform(delete("/v1/attributes/{attribute_ID}", id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(409));

    }

}
