package com.zone.zissa.svcs.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.Resource;
import com.zone.zissa.repos.AllocationRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.svcs.AllocationService;

/**
 * The AllocationServiceImpl class.
 */
@Service
public class AllocationServiceImpl implements AllocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllocationServiceImpl.class);

    @Autowired
    private ResourceRepository resourceRepo;

    @Autowired
    private AttributeRepository attributeRepo;

    @Autowired
    private AllocationRepository allocationRepo;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResourceServiceImpl resourceService;

    /**
     * Gets the all allocation history details by resource service implementation.
     *
     * @param resourceId
     * @return ServiceResponse<List<Allocation>>
     */
    @Override
    public List<Allocation> getAllAllocationDetailsByResource(Integer resourceId) {
        LOGGER.info("Get all allocation details by Resource Service implementation");
        List<Allocation> allocationList = null;
        int operationId = 1;
        Resource resourceExists = resourceRepo.findByResourceID(resourceId);
        if (resourceExists != null) {
            boolean permissionExists = permissionService.permissionExists(resourceExists.getCategory().getCategory_ID(),
                    operationId);
            if (permissionExists) {
                allocationList = allocationRepo.findAllocationHistoryByResource(resourceId);
                if (allocationList.isEmpty()) {

                    throw new NoContentException(RestAPIMessageConstants.NO_ALLOCATION_CONTENT);

                }
            } else {
                throw new AccessDeniedException(RestAPIMessageConstants.ALLOCATION_ACCESS_DENIED);
            }
        } else {
            throw new NotFoundException(RestAPIMessageConstants.RESOURCE_NOT_FOUND);

        }
        return allocationList;
    }

    /**
     * Builds the allocation resources.
     *
     * @param resourcerecords
     * @return the object
     */
    public Object buildAllocationResources(List<Resource> resourceRecords) {
        Object resourceAllocationObject = null;
        List<JSONObject> resourceList = new ArrayList<>();
        List<Object> resourceAllocationObjects = null;
        try {
            for (Resource resource : resourceRecords) {
                JSONObject jSONObject = new JSONObject();

                ObjectMapper mapper = new ObjectMapper();

                String jsonInString = mapper.writeValueAsString(resource);

                JSONObject childjSONObject = new JSONObject(jsonInString);
                JSONArray resourceAttributeArray = childjSONObject.getJSONArray("resourceAttributes");
                JSONArray categoryAttributeArray = childjSONObject.getJSONObject("category")
                        .getJSONArray("categoryAttributes");
                for (int i = 0; i < resourceAttributeArray.length(); i++) {
                    JSONObject resourceAttributeObject = resourceAttributeArray.getJSONObject(i);
                    String name = resourceAttributeObject.getJSONObject("attribute").getString("name");
                    this.checkNamesForEquality(categoryAttributeArray, name, resourceAttributeObject);
                }
                childjSONObject.getJSONObject("category").put("categoryAttributes", null);
                jSONObject.put("resource", childjSONObject);
                if (resource.getStatus().getStatus_ID() != 0) {

                    Allocation allocation = allocationRepo.findAllocationsByResource(resource.getResource_ID());
                    ObjectMapper mapperA = new ObjectMapper();
                    String jsonString = mapperA.writeValueAsString(allocation);
                    JSONObject childObject = new JSONObject(jsonString);
                    jSONObject.put("allocation", childObject);
                }
                resourceList.add(jSONObject);
                resourceAllocationObjects = Arrays.asList(mapper.readValue(resourceList.toString(), Object.class));

                if (resourceAllocationObjects != null) {
                    resourceAllocationObject = resourceAllocationObjects.get(0);
                }

            }
        } catch (Exception e) {

            LOGGER.error("Error", e);
        }
        return resourceAllocationObject;
    }

    /**
     * checkNamesForEquality method
     * 
     * @param categoryAttributeArray
     * @param name
     * @param resourceAttributeObject
     */
    public void checkNamesForEquality(JSONArray categoryAttributeArray, String name, JSONObject resourceAttributeObject)
            throws JSONException {
        for (int j = 0; j < categoryAttributeArray.length(); j++) {
            JSONObject categoryAttributeObject = categoryAttributeArray.getJSONObject(j);
            String categoryAttributeName = categoryAttributeObject.getJSONObject("attribute").getString("name");
            if (name.equals(categoryAttributeName)) {
                resourceAttributeObject.put("default", categoryAttributeObject.getBoolean("default"));
            }
        }
    }

    /**
     * get All Resources By SearchTerm service implementation
     * 
     * @param categoryId
     * @param page
     * @param size
     * @param searchText
     * @param direction
     * @param attrname
     * @return List<Resource>
     */
    @Override
    public Object getAllResourcesBySearchTerm(List<Integer> categoryId, int page, int size, String searchText,
            String direction, short attrid) {
        LOGGER.info("Get all Resources with allocation details Service implementation");
        int pageSize = size;
        List<Resource> resource = null;
        List<Resource> resourceList = null;
        List<Resource> resourceSearchList = null;
        if (size == 0) {
            pageSize = Integer.MAX_VALUE;
        }
        if (attributeRepo.findByAttributeID(attrid) != null) {
            resource = resourceRepo.findResourcesByCategory(categoryId);
            resourceList = resourceService.sortAscSearchResources(resource, direction, attrid);
            resourceSearchList = resourceService.searchResources(resourceList, page, pageSize, searchText);
        } else {
            if ("desc".equalsIgnoreCase(direction)) {
                resource = resourceRepo.findResourcesByCategoryDesc(categoryId);
            } else {
                resource = resourceRepo.findResourcesByCategoryAsc(categoryId);
            }
            resourceSearchList = resourceService.searchResources(resource, page, pageSize, searchText);
        }
        return resourceSearchList;
    }
}
