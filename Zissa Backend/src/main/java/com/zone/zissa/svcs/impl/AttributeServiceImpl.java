package com.zone.zissa.svcs.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AttributeDataTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.AttributeValueRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.svcs.AttributeService;

/**
 * The AttributeServiceImpl class.
 */
@Service
public class AttributeServiceImpl implements AttributeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeServiceImpl.class);

    @Autowired
    private AttributeRepository attributeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AttributeDataTypeRepository attributeDataRepo;

    @Autowired
    private AttributeValueRepository attributeValueRepo;

    @Autowired
    private ResourceAttributeRepository resourceAttributeRepo;

    @Autowired
    private ResourcebinAttributeRepository resourceBinAttributeRepo;

    private String attributeValue = "value";

    private String attributeValueId = "attribute_Value_ID";

    /**
     * Add attribute service implementation.
     *
     * @param attributeData
     * @return Attribute
     * @throws JSONException
     */
    @Override
    public Attribute addAttribute(String attributeData) throws JSONException {
        LOGGER.info("Add new Attribute Service implementation");

        Attribute attribute = new Attribute();
        Attribute addAttribute = null;
        Set<AttributeValue> attributeValueList = new HashSet<>();

        Date date = new Date();
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);
        JSONObject jsonObject = new JSONObject(attributeData);

        String name = (String) jsonObject.get("name");
        int dataTypeId = (Integer) jsonObject.get("attrDataType");
        int dropdownValue = (Integer) jsonObject.get("dropdowncontrolval");
        int userId = (Integer) jsonObject.get("user_ID");
        User userObject = userRepo.findByUserID(userId);
        AttrDataType attributeDataTypeObject = attributeDataRepo.findByDataTypeID(dataTypeId);
        attribute.setUser(userObject);
        attribute.setAttrDataType(attributeDataTypeObject);
        attribute.setName(name);
        attribute.setCreatedDate(timeStamp);
        Attribute attributeExists = attributeRepo.findByName(name);
        if (attributeExists == null) {
            addAttribute = attributeRepo.save(attribute);
            if (dropdownValue == 1) {
                this.addAttributeValuesMethod(jsonObject, addAttribute, attributeValueList);
            }
        } else {
            throw new ConflictException(RestAPIMessageConstants.ATTRIBUTE_EXISTS);
        }

        return addAttribute;
    }

    /**
     * addAttributeValuesMethod method
     * 
     * @param jsonObject
     * @param attributeObject
     * @param attributeValueList
     * @throws JSONException
     */

    public void addAttributeValuesMethod(JSONObject jsonObject, Attribute attributeObject,
            Set<AttributeValue> attributeValueList) throws JSONException {
        JSONArray jsonChildObject = (JSONArray) jsonObject.get("attributeValues");
        for (int i = 0; i < jsonChildObject.length(); i++) {
            AttributeValue attributeValueobject = new AttributeValue();
            JSONObject jsonValue = (JSONObject) jsonChildObject.get(i);
            attributeValueobject.setValue(jsonValue.getString(attributeValue));
            attributeValueobject.setAttribute(attributeObject);
            AttributeValue addAttributeValue = attributeValueRepo.save(attributeValueobject);
            attributeValueList.add(addAttributeValue);
        }
        attributeObject.setAttributeValues(attributeValueList);
    }

    /**
     * Update attribute service implementation.
     *
     * @param attributeData
     * @return Attribute
     * @throws JSONException
     */
    @Override
    public Attribute updateAttribute(String attributeData) throws JSONException {
        LOGGER.info("Update Attribute Service implementation");
        Attribute attribute = new Attribute();
        Date date = new Date();
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);

        JSONObject jsonObject = new JSONObject(attributeData);
        String name = (String) jsonObject.get("name");
        int dataTypeId = (Integer) jsonObject.get("data_Type_ID");
        int dropDownValue = (Integer) jsonObject.get("dropdown");
        int userId = (Integer) jsonObject.get("user_ID");
        Short attributeId = ((Integer) jsonObject.get("attribute_ID")).shortValue();
        Attribute attributeObject = attributeRepo.findByAttributeID(attributeId);
        User userObject = userRepo.findByUserID(userId);
        AttrDataType attributeDataTypeObject = attributeDataRepo.findByDataTypeID(dataTypeId);
        attribute.setUser(userObject);
        attribute.setAttrDataType(attributeDataTypeObject);
        attribute.setName(name);
        attribute.setAttributeID(attributeId);
        attribute.setCreatedDate(timeStamp);
        Attribute updateAttribute = null;
        if (attributeObject == null) {
            throw new DataNotFoundException(RestAPIMessageConstants.UPDATE_ATTRIBUTE);

        } else {
            if (attributeRepo.findByName(name) != null
                    && attributeRepo.findByName(name).getAttribute_ID() != attributeId) {
                throw new ConflictException(RestAPIMessageConstants.ATTRIBUTE_NAME_EXISTS);

            } else {
                updateAttribute = attributeRepo.save(attribute);
            }
        }
        if (dropDownValue == 1) {

            JSONArray jsonDeleteAttributeObject = (JSONArray) jsonObject.get("delete_attributeValues");
            JSONArray jsonInsertAttributeObject = (JSONArray) jsonObject.get("insert_attributeValues");
            JSONArray jsonChildObject = (JSONArray) jsonObject.get("attributeValues");
            if (jsonInsertAttributeObject.length() != 0) {
                this.insertAttributeMethod(jsonInsertAttributeObject, updateAttribute);
            }
            this.updateAttributeValuesMethod(jsonChildObject, attributeId, updateAttribute);
            if (jsonDeleteAttributeObject.length() != 0) {
                List<String> deleteAttributeFailureList = this.deleteAttributeMethod(jsonDeleteAttributeObject,
                        attributeId);
                return this.checkDeleteAttributeFailureListIsNotEmpty(deleteAttributeFailureList, updateAttribute,
                        attributeObject);
            }
        } else {
            attributeValueRepo.deleteAttributeValue(attributeId);
        }
        Set<AttributeValue> saveAttributeValue = attributeValueRepo.findAttributeValueByAttribute(attributeObject);
        updateAttribute.setAttributeValues(saveAttributeValue);
        return updateAttribute;
    }

    /**
     * checkDeleteAttributeFailureListIsNotEmpty method
     * 
     * @param deleteAttributeFailureList
     * @param updateAttribute
     * @param attributeObject
     * @return Attribute
     */
    public Attribute checkDeleteAttributeFailureListIsNotEmpty(List<String> deleteAttributeFailureList,
            Attribute updateAttribute, Attribute attributeObject) {
        if (!deleteAttributeFailureList.isEmpty()) {
            throw new ConflictException(RestAPIMessageConstants.ATTRIBUTE_UPDATE_CONFLICT);

        } else {
            Set<AttributeValue> attributeValueObject = attributeValueRepo
                    .findAttributeValueByAttribute(attributeObject);
            updateAttribute.setAttributeValues(attributeValueObject);
        }
        return updateAttribute;
    }

    /**
     * Update AttributeValues Method.
     *
     * @param jsonChildObject
     * @param attributeId
     * @param updateAttribute
     * @throws JSONException
     */
    public void updateAttributeValuesMethod(JSONArray jsonChildObject, Short attributeId, Attribute updateAttribute)
            throws JSONException {
        for (int i = 0; i < jsonChildObject.length(); i++) {
            AttributeValue attributeValueObject = new AttributeValue();
            JSONObject json = (JSONObject) jsonChildObject.get(i);
            attributeValueObject.setValue(json.getString(attributeValue));

            AttributeValue attributeValueObj = attributeValueRepo.findByAttributeValueID(json.getInt(attributeValueId));
            String attributeDropdownOldValue = attributeValueObj.getValue();
            String attributeDropdownNewValue = json.getString(attributeValue);
            if (!attributeDropdownNewValue.equals(attributeDropdownOldValue)) {
                resourceAttributeRepo.updateResourcesAttributes(attributeDropdownNewValue, attributeId,
                        attributeDropdownOldValue);
                resourceBinAttributeRepo.updateResourceBinAttributes(attributeDropdownNewValue, attributeId,
                        attributeDropdownOldValue);
            }
            attributeValueObject.setAttributeValueID(json.getInt(attributeValueId));
            attributeValueObject.setAttribute(updateAttribute);
            attributeValueRepo.save(attributeValueObject);
        }
    }

    /**
     * Insert attribute method.
     *
     * @param jsonInsertAttributeObject
     * @param attributeObject
     * @throws JSONException
     */
    public void insertAttributeMethod(JSONArray jsonInsertAttributeObject, Attribute attributeObject)
            throws JSONException {
        for (int i = 0; i < jsonInsertAttributeObject.length(); i++) {
            AttributeValue attributeValueObject = new AttributeValue();
            JSONObject jsonObject = (JSONObject) jsonInsertAttributeObject.get(i);
            attributeValueObject.setValue(jsonObject.getString(attributeValue));
            attributeValueObject.setAttribute(attributeObject);
            attributeValueRepo.save(attributeValueObject);
        }
    }

    /**
     * Delete attribute method.
     *
     * @param jsonDeleteAttrObject
     * @param attributeId
     * @return the list
     * @throws JSONException
     */
    public List<String> deleteAttributeMethod(JSONArray jsonDeleteAttributeObject, Short attributeId)
            throws JSONException {

        List<String> deleteAttributeFailureList = new ArrayList<>();
        for (int i = 0; i < jsonDeleteAttributeObject.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonDeleteAttributeObject.get(i);
            int attributeValueById = jsonObject.getInt(attributeValueId);
            AttributeValue attributeValueObject = attributeValueRepo.findByAttributeValueID(attributeValueById);
            String getAttributeValue = attributeValueObject.getValue();
            Attribute attributeObject = attributeRepo.findByAttributeID(attributeId);
            List<ResourceAttribute> resourceAttributeList = resourceAttributeRepo
                    .findByAttributeAndValue(attributeObject, getAttributeValue);
            List<ResourcebinAttribute> resourceBinAttributeList = resourceBinAttributeRepo
                    .findByAttributeAndValue(attributeObject, getAttributeValue);
            if (resourceAttributeList.isEmpty() && resourceBinAttributeList.isEmpty()) {
                attributeValueRepo.deleteAttributeValueID(attributeValueById);
            } else {
                deleteAttributeFailureList.add(getAttributeValue);

            }
        }
        return deleteAttributeFailureList;
    }

    /**
     * Delete attribute service implementation.
     *
     * @param attributeID the attribute ID
     */
    @Override
    public void deleteAttribute(Short attributeId) {

        LOGGER.info("Delete Attribute Service implementation");
        Attribute attributeExists = attributeRepo.findByAttributeID(attributeId);
        if (attributeExists != null) {
            attributeRepo.deleteById(attributeId);
        } else {
            throw new DataNotFoundException(RestAPIMessageConstants.DELETE_ATTRIBUTE);

        }

    }

    /**
     * Get all attributes service implementation.
     *
     * @return List<Attribute>
     */
    @Override
    public List<Attribute> getAllAttributes() {

        LOGGER.info("Get all Attributes Service implementation");
        return attributeRepo.findAll();

    }

    /**
     * Gets the attribute info by id service implementation.
     *
     * @param attribute_ID
     * @return Attribute
     */
    @Override
    public Attribute getAttributeInfoById(Short attributeId) {

        LOGGER.info("Get Attribute Details by AttributeId Service implementation");
        return attributeRepo.findByAttributeID(attributeId);
    }

    /**
     * Gets all attribute data types.
     *
     * @return List<AttrDataType>
     */
    @Override
    public List<AttrDataType> getAllAttributeDataTypes() {

        LOGGER.info("Get all Attribute DataTypes Service implementation");
        return attributeDataRepo.findAll();

    }
}
