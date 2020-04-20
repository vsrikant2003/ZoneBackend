package com.zone.zissa.svcs.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.DataToLongException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.AllocationType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Employee;
import com.zone.zissa.model.EmployeeAllocation;
import com.zone.zissa.model.OtherAllocation;
import com.zone.zissa.model.Project;
import com.zone.zissa.model.ProjectAllocation;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.Status;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AllocationRepository;
import com.zone.zissa.repos.AllocationTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.EmployeeAllocationRepository;
import com.zone.zissa.repos.EmployeeRepository;
import com.zone.zissa.repos.OtherAllocationRepository;
import com.zone.zissa.repos.ProjectAllocationRepository;
import com.zone.zissa.repos.ProjectRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.ResourcebinRepository;
import com.zone.zissa.repos.StatusRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.ResourceService;

/**
 * The ResourceServiceImpl class.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceRepository resourceRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private AttributeRepository attributeRepo;

    @Autowired
    private ResourceAttributeRepository resourceAttributeRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private ResourcebinRepository resourceBinRepo;

    @Autowired
    private ResourcebinAttributeRepository resourceBinAttributeRepo;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AllocationRepository allocationRepo;

    @Autowired
    private EmployeeAllocationRepository employeeAllocationRepo;

    @Autowired
    private ProjectAllocationRepository projectAllocationRepo;

    @Autowired
    private OtherAllocationRepository otherAllocationRepo;

    @Autowired
    private AllocationTypeRepository allocationTypeRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ProjectRepository projectRepo;

    private String categoryAttributes = "categoryAttributes";

    private String defaultKey = "default";

    private String category = "category";

    private String attributeKey = "attribute";

    private String dateFormat = "dd/MM/yyyy";

    private double count = 0;

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    /**
     * Add resources service implementation.
     *
     * @param resourceData
     * @return List<Resource>
     */
    @Override
    public List<Resource> addResource(List<Resource> resourceData) {

        LOGGER.info("Add Resources Service implementation");

        List<Resource> addResourceList = new ArrayList<>();
        Date date = new Date();
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);
        int operationId = 2;

        for (Resource resourceList : resourceData) {
            Category categoryObject = resourceList.getCategory();

            boolean permissionexists = permissionService.permissionExists(categoryObject.getCategory_ID(), operationId);
            if (permissionexists) {

                Resource resource = new Resource();

                resource.setCategory(categoryObject);
                resource.setUser(resourceList.getUser());
                resource.setCode(resourceList.getCode());
                resource.setStatus(resourceList.getStatus());
                resource.setCreatedDate(timeStamp);

                Resource resourceObject = resourceRepo.save(resource);

                List<ResourceAttribute> resourceAttribute = new ArrayList<>();

                List<ResourceAttribute> resourceAttributeList = resourceList.getResourceAttributes();
                for (ResourceAttribute resourceAttributeObject : resourceAttributeList) {
                    ResourceAttribute saveResourceAttribute = new ResourceAttribute();

                    saveResourceAttribute.setValue(resourceAttributeObject.getValue());
                    saveResourceAttribute.setAttribute(resourceAttributeObject.getAttribute());
                    saveResourceAttribute.setResource(resourceObject);

                    ResourceAttribute resourceAttributeSave = resourceAttributeRepo.save(saveResourceAttribute);
                    resourceAttribute.add(resourceAttributeSave);
                }
                resource.setResourceAttributes(resourceAttribute);
                addResourceList.add(resource);

            } else {

                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_ADD_PERMISSION);

            }
        }

        return addResourceList;

    }

    /**
     * Update resource service implementation.
     *
     * @param resourceData
     * @return List<Resource>
     */
    @Override
    public List<Resource> updateResource(List<Resource> resourceData) {

        LOGGER.info("Update Resources Service implementation");

        List<Resource> updateresourceList = new ArrayList<>();
        Date date = new Date();
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);
        int operationId = 3;

        for (Resource resourceList : resourceData) {

            int resourceId = resourceList.getResource_ID();
            Resource resourceObj = resourceRepo.findByResourceID(resourceId);
            int categoryId = resourceObj.getCategory().getCategory_ID();
            boolean permissionexists = permissionService.permissionExists(categoryId, operationId);

            if (permissionexists) {

                Resource resource = new Resource();
                resource.setResourceID(resourceObj.getResource_ID());
                resource.setCategory(resourceList.getCategory());
                resource.setUser(resourceList.getUser());
                resource.setCode(resourceList.getCode());
                resource.setStatus(resourceList.getStatus());
                resource.setCreatedDate(timeStamp);

                Resource resourceobject = resourceRepo.save(resource);

                List<ResourceAttribute> resourceAttribute = new ArrayList<>();

                List<ResourceAttribute> resourceAttributeList = resourceList.getResourceAttributes();
                for (ResourceAttribute resourceAttributeObject : resourceAttributeList) {
                    ResourceAttribute saveResourceAttribute = new ResourceAttribute();
                    ResourceAttribute resourceAttributeObj = resourceAttributeRepo
                            .findByResourceAttributeID(resourceAttributeObject.getResource_Attribute_ID());
                    Attribute attributeObject = resourceAttributeObj.getAttribute();
                    saveResourceAttribute.setResourceAttributeID(resourceAttributeObj.getResource_Attribute_ID());
                    saveResourceAttribute.setValue(resourceAttributeObject.getValue());
                    saveResourceAttribute.setAttribute(attributeObject);
                    saveResourceAttribute.setResource(resourceobject);

                    ResourceAttribute resourceAttributeSave = resourceAttributeRepo.save(saveResourceAttribute);
                    resourceAttribute.add(resourceAttributeSave);
                }
                resource.setResourceAttributes(resourceAttribute);
                updateresourceList.add(resource);

            } else {
                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_UPDATE_PERMISSION);
            }
        }

        return updateresourceList;
    }

    /**
     * Restore resource attributes method.
     *
     * @param resourceBinAttributeObj
     * @param resourceObject
     */
    public void restoreResourceAttributesMethod(List<ResourcebinAttribute> resourceBinAttributeObj,
            Resource resourceObject) {

        for (int j = 0; j < resourceBinAttributeObj.size(); j++) {
            ResourcebinAttribute resourcebinAttrObject = resourceBinAttributeObj.get(j);
            ResourceAttribute resourceAttributeObject = new ResourceAttribute();
            resourceAttributeObject.setValue(resourcebinAttrObject.getValue());
            resourceAttributeObject.setAttribute(resourcebinAttrObject.getAttribute());
            resourceAttributeObject.setResource(resourceObject);
            resourceAttributeObject.setResourceAttributeID(resourcebinAttrObject.getResource_Attribute_ID());
            resourceAttributeRepo.save(resourceAttributeObject);
        }
    }

    /**
     * Search allocations.
     *
     * @param allocations
     * @param serachText
     * @return true, if successful
     */
    private boolean searchAllocations(Set<Allocation> allocations, String serachText) {

        for (Allocation allocation : allocations) {
            byte allocationTypeId = allocation.getAllocationType().getAllocation_Type_ID();
            if (allocationTypeId == 1) {
                return allocation.getEmployeeAllocations().getEmployee().getUserName().toLowerCase()
                        .contains(serachText.toLowerCase());

            } else if (allocationTypeId == 2) {
                return allocation.getProjectAllocations().getProject().getProjectName().toLowerCase()
                        .contains(serachText.toLowerCase());

            } else if (allocationTypeId == 3) {
                return allocation.getOtherAllocations().getAssignee_Name().toLowerCase()
                        .contains(serachText.toLowerCase());
            }
        }
        return false;

    }

    /**
     * Search resource attribute.
     *
     * @param resourceAttributeList
     * @param serachText
     * @return true, if successful
     */
    private boolean searchResourceAttribute(List<ResourceAttribute> resourceAttributeList, String serachText) {
        boolean found = false;

        for (ResourceAttribute resourceAttribute : resourceAttributeList) {

            found = resourceAttribute.getValue().toLowerCase().contains(serachText.toLowerCase());
            if (found) {
                return true;
            }
        }
        return found;

    }

    /**
     * Search resource bin attribute.
     *
     * @param resourceAttributeList
     * @param serachText
     * @return true, if successful
     */
    private boolean searchResourceBinAttribute(List<ResourcebinAttribute> resourceAttributeList, String serachText) {
        boolean found = false;

        for (ResourcebinAttribute resourceAttribute : resourceAttributeList) {

            found = resourceAttribute.getValue().toLowerCase().contains(serachText.toLowerCase());
            if (found) {
                return true;
            }
        }
        return found;
    }

    /**
     * Search resources.
     *
     * @param resourcecd
     * @param page
     * @param size
     * @param serachText
     * @return List<Resource>
     */
    public List<Resource> searchResources(List<Resource> resourcecd, int page, int size, String serachText) {

        List<Resource> finalResources = new ArrayList<>();
        try {

            Stream<Resource> filteredResources = this.filteringResources(resourcecd, serachText);
            finalResources = filteredResources.collect(Collectors.toList());
            this.count = finalResources.size();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.error("SearchResourceError", e);
        }

        int noOfPages = (int) Math.ceil(this.count / size);

        return this.paginationforResources(page, noOfPages, size, finalResources);

    }

    /**
     * filtering resources method.
     *
     * @param resourcecd
     * @param serachText
     * @return Stream<Resource>
     */

    public Stream<Resource> filteringResources(List<Resource> resourcecd, String serachText) {
        return resourcecd.stream().filter(resource -> {
            boolean flag = true;
            if (resource.getCode().toLowerCase().contains(serachText.toLowerCase())) {
                return flag;
            } else if (resource.getStatus().getStatus_Name().toLowerCase().contains(serachText.toLowerCase())) {
                return flag;
            } else if (searchAllocations(resource.getAllocations(), serachText)) {
                return flag;
            } else if (searchResourceAttribute(resource.getResourceAttributes(), serachText)) {
                return flag;
            } else {
                return false;
            }
        });
    }

    /**
     * pagination for Resources method.
     *
     * @param page
     * @param noOfPages
     * @param size
     * @param finalResources
     * @return List<Resource>
     */
    public List<Resource> paginationforResources(int page, int noOfPages, int size, List<Resource> finalResources) {

        List<Resource> finalList = null;
        if (page <= noOfPages) {

            int startIndex = page * size;
            int endIndex = startIndex + (size - 1);

            if (startIndex <= finalResources.size() - 1 && endIndex < finalResources.size()) {
                finalList = finalResources.subList(startIndex, endIndex + 1);
            } else if (startIndex <= finalResources.size() - 1 && endIndex == finalResources.size()) {
                finalList = finalResources.subList(startIndex, endIndex);
            } else {
                finalList = finalResources.subList(startIndex, finalResources.size());

            }
        }
        return finalList;
    }

    /**
     * Search disposed resources.
     *
     * @param resourcecd
     * @param page
     * @param size
     * @param serachText
     * @return List<Resourcebin>
     */
    public List<Resourcebin> searchDisposedResources(List<Resourcebin> resourcecd, int page,

            int size, String serachText) {
        List<Resourcebin> finalResources = null;

        Stream<Resourcebin> filteredResources = resourcecd.stream().filter(resource -> {
            boolean flag = true;
            if (resource.getCode().toLowerCase().contains(serachText.toLowerCase())) {
                return flag;
            } else if (resource.getDisposeReason().toLowerCase().contains(serachText.toLowerCase())) {
                return flag;
            } else if (searchResourceBinAttribute(resource.getResourcebinAttributes(), serachText)) {
                return flag;
            } else {
                return false;
            }
        });
        finalResources = filteredResources.collect(Collectors.toList());
        this.count = finalResources.size();
        int noOfPages = (int) Math.ceil(this.count / size);
        return this.paginationforDisposedResources(page, noOfPages, size, finalResources);
    }

    /**
     * pagination for Disposed Resources method.
     *
     * @param page
     * @param noOfPages
     * @param size
     * @param finalResources
     * @return List<Resourcebin>
     */
    public List<Resourcebin> paginationforDisposedResources(int page, int noOfPages, int size,
            List<Resourcebin> finalResources) {
        List<Resourcebin> finalList = null;

        if (page <= noOfPages) {

            int startIndex = page * size;
            int endIndex = startIndex + (size - 1);

            if (startIndex <= finalResources.size() - 1 && endIndex < finalResources.size()) {
                finalList = finalResources.subList(startIndex, endIndex + 1);

            } else if (startIndex <= finalResources.size() - 1 && endIndex == finalResources.size()) {
                finalList = finalResources.subList(startIndex, endIndex);

            } else if (startIndex <= finalResources.size() - 1) {
                finalList = finalResources.subList(startIndex, finalResources.size());
            }
        }
        return finalList;
    }

    /**
     * get All Resources By SearchTerm service implementation
     * 
     * @param categoryId
     * @param page
     * @param size
     * @param serachText
     * @param direction
     * @param attributeId
     * @return List<Resource>
     */
    public List<Resource> getAllResourcesBySearchTerm(List<Integer> categoryId, int page, int size, String serachText,
            String direction, short attributeId) {
        LOGGER.info("Get all Resources Service implementation");
        List<Resource> resourceCd = null;
        List<Resource> resourceRecords = null;
        List<Resource> resourceSearchList = null;
        int pageSize = size;
        if (size == 0) {
            pageSize = Integer.MAX_VALUE;
        }
        if (attributeRepo.findByAttributeID(attributeId) != null) {
            resourceCd = resourceRepo.findResourcesByCategory(categoryId);
            resourceRecords = sortAscSearchResources(resourceCd, direction, attributeId);
            resourceSearchList = searchResources(resourceRecords, page, pageSize, serachText);
        } else {
            if ("desc".equalsIgnoreCase(direction)) {
                resourceCd = resourceRepo.findResourcesByCategoryDesc(categoryId);
            } else {
                resourceCd = resourceRepo.findResourcesByCategoryAsc(categoryId);
            }
            resourceSearchList = searchResources(resourceCd, page, pageSize, serachText);
        }
        return resourceSearchList;
    }

    /**
     * get All Disposed Resources By SearchTerm service implementation
     * 
     * @param categoryId
     * @param page
     * @param size
     * @param serachText
     * @param direction
     * @param attributeId
     * @return List<Resourcebin>
     */
    public List<Resourcebin> getAllDisposedResourcesBySearchTerm(List<Integer> categoryId, int page, int size,
            String serachText, String direction, short attributeId) {
        LOGGER.info("Get all Disposed Resources Service implementation");
        List<Resourcebin> resourceCd = null;
        List<Resourcebin> resourceRecords = null;
        List<Resourcebin> resourceSearchList = null;
        int pageSize = size;
        if (size == 0) {
            pageSize = Integer.MAX_VALUE;
        }
        if (attributeRepo.findByAttributeID(attributeId) != null) {

            resourceCd = resourceBinRepo.findResourcesByCategory(categoryId);
            resourceRecords = sortAscSearchResourceBin(resourceCd, direction, attributeId);
            resourceSearchList = searchDisposedResources(resourceRecords, page, pageSize, serachText);
        } else {
            if ("desc".equalsIgnoreCase(direction)) {
                resourceCd = this.getDisposedResourcesByCategoryDesc(attributeId, categoryId);

            } else {

                resourceCd = this.getDisposedResourcesByCategoryAsc(attributeId, categoryId);
            }
            resourceSearchList = searchDisposedResources(resourceCd, page, pageSize, serachText);
        }
        return resourceSearchList;
    }

    /**
     * get Disposed Resources By Category Desc method
     * 
     * @param attributeId
     * @param categoryId
     * @return List<Resourcebin>
     */
    private List<Resourcebin> getDisposedResourcesByCategoryDesc(short attributeId, List<Integer> categoryId) {
        List<Resourcebin> resources;

        if (attributeId == -1) {
            resources = resourceBinRepo.findResourcesByCategoryOrderByReasonDesc(categoryId);
        } else {
            resources = resourceBinRepo.findResourcesByCategoryDesc(categoryId);
        }
        return resources;
    }

    /**
     * get Disposed Resources By Category Asc method
     * 
     * @param attributeId
     * @param categoryId
     * @return List<Resourcebin>
     */
    private List<Resourcebin> getDisposedResourcesByCategoryAsc(short attributeId, List<Integer> categoryId) {
        List<Resourcebin> resources;

        if (attributeId == -1) {
            resources = resourceBinRepo.findResourcesByCategoryOrderByReasonAsc(categoryId);
        } else {
            resources = resourceBinRepo.findResourcesByCategoryAsc(categoryId);
        }
        return resources;
    }

    /**
     * Builds the resources.
     *
     * @param resourceRecords
     * @return the object
     */
    public Object buildResources(List<Resource> resourceRecords) {
        Object resourceAllocationObject = null;
        List<JSONObject> resourceRecordsList = new ArrayList<>();
        List<Object> resourceAllocationObjects = null;
        JSONObject emptyObject = new JSONObject();
        try {
            for (Resource resource : resourceRecords) {
                JSONObject jSONObject = new JSONObject();
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(resource);

                JSONObject childjSONObject = new JSONObject(jsonInString);

                JSONArray resourceAttributeArray = childjSONObject.getJSONArray("resourceAttributes");
                JSONArray categoryAttributeArray = childjSONObject.getJSONObject(category)
                        .getJSONArray(categoryAttributes);
                for (int i = 0; i < resourceAttributeArray.length(); i++) {
                    JSONObject resourceAttributeObject = resourceAttributeArray.getJSONObject(i);
                    String name = resourceAttributeObject.getJSONObject(attributeKey).getString("name");
                    this.setResourceAttributeDefaultKeyMethod(categoryAttributeArray, name, resourceAttributeObject);
                }
                if (resource.getStatus().getStatus_ID() != 0) {
                    Allocation allocationObject = allocationRepo.findAllocationsByResource(resource.getResource_ID());
                    ObjectMapper mapperA = new ObjectMapper();
                    String jsonString = mapperA.writeValueAsString(allocationObject);
                    JSONObject childObject = new JSONObject(jsonString);
                    childjSONObject.put("allocation", childObject);
                } else {
                    childjSONObject.put("allocation", emptyObject);
                }
                childjSONObject.getJSONObject(category).put(categoryAttributes, null);
                jSONObject.put("resource", childjSONObject);
                resourceRecordsList.add(jSONObject);
                resourceAllocationObjects = Arrays
                        .asList(mapper.readValue(resourceRecordsList.toString(), Object.class));

                if (resourceAllocationObjects != null) {
                    resourceAllocationObject = resourceAllocationObjects.get(0);
                }
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.error("BuildResourcesError", e);
        }

        return resourceAllocationObject;
    }

    /**
     * Builds the resourcebin.
     *
     * @param resourceRecords
     * @return the object
     */
    public Object buildResourcebin(List<Resourcebin> resourceRecords) {
        Object resourceAllocationObject = null;
        List<JSONObject> resourceRecordsList = new ArrayList<>();
        List<Object> resourceAllocationObjects = null;
        try {
            for (Resourcebin resource : resourceRecords) {
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(resource);
                JSONObject childjSONObject = new JSONObject(jsonInString);
                JSONArray resourceBinAttributeArray = childjSONObject.getJSONArray("resourcebinAttributes");
                JSONArray categoryAttributeArray = childjSONObject.getJSONObject(category)
                        .getJSONArray(categoryAttributes);

                for (int i = 0; i < resourceBinAttributeArray.length(); i++) {
                    JSONObject resourceAttributeObject = resourceBinAttributeArray.getJSONObject(i);
                    String name = resourceAttributeObject.getJSONObject(attributeKey).getString("name");
                    this.setResourceAttributeDefaultKeyMethod(categoryAttributeArray, name, resourceAttributeObject);
                }
                childjSONObject.getJSONObject(category).put(categoryAttributes, null);
                resourceRecordsList.add(childjSONObject);
                resourceAllocationObjects = Arrays
                        .asList(mapper.readValue(resourceRecordsList.toString(), Object.class));
                if (resourceAllocationObjects != null) {
                    resourceAllocationObject = resourceAllocationObjects.get(0);
                }
            }
        } catch (Exception e) {
            LOGGER.error("BuildResourcebinError", e);
            LOGGER.info(e.getMessage());
        }

        return resourceAllocationObject;
    }

    /**
     * Sets the resource attribute default key method.
     *
     * @param categoryAttributeArray
     * @param name
     * @param resourceAttributeObject
     * @throws JSONException
     */
    public void setResourceAttributeDefaultKeyMethod(JSONArray categoryAttributeArray, String name,
            JSONObject resourceAttributeObject) throws JSONException {

        for (int j = 0; j < categoryAttributeArray.length(); j++) {
            JSONObject categoryAttributeObject = categoryAttributeArray.getJSONObject(j);
            String categoryAttributeName = categoryAttributeObject.getJSONObject(attributeKey).getString("name");
            if (name.equals(categoryAttributeName)) {
                resourceAttributeObject.put(defaultKey, categoryAttributeObject.getBoolean(defaultKey));
            }
        }
    }

    /**
     * Gets the resources by category id service implementation.
     *
     * @param categoryId
     * @return List<Resource>
     */
    @Override
    public List<Resource> getResourcesByCategoryId(Integer categoryId) {

        LOGGER.info("Get all Resources by categoryId Service implementation");
        List<Resource> resourceRecords = null;
        int operationId = 1;

        Category categoryExists = categoryRepo.findByCategoryID(categoryId);

        if (categoryExists == null) {

            throw new NotFoundException(RestAPIMessageConstants.CATEGORY_NOT_EXISTS);

        } else {
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            resourceRecords = resourceRepo.findResourceByCategory(categoryExists);
            if (permissionExists) {

                this.isResourceExistsMethod(resourceRecords);
            } else {

                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_VIEW_PERMISSION);
            }

        }
        return resourceRecords;
    }

    /**
     * Checks if is resource exists method.
     *
     * @param resourceRecords
     */
    public void isResourceExistsMethod(List<Resource> resourceRecords) {
        if (resourceRecords.isEmpty()) {
            throw new NoContentException(RestAPIMessageConstants.RESOURCE_NOT_EXISTS);

        }
    }

    /**
     * Delete resource service implementation.
     *
     * @param resourceId
     */
    @Override
    public void deleteResource(Integer resourceId) {

        LOGGER.info("Delete Resource Service implementation");
        int operationId = 6;
        Resource resourceExists = resourceRepo.findByResourceID(resourceId);
        if (resourceExists != null) {
            int categoryId = resourceExists.getCategory().getCategory_ID();
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            if (permissionExists) {
                this.checkResourceAllocationStatus(resourceExists, resourceId);
            } else {

                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_DELETE_PERMISSION);

            }
        } else {
            throw new DataNotFoundException(RestAPIMessageConstants.DELETE_RESOURCE);
        }
    }

    /**
     * Check resource allocation status.
     *
     * @param resourceExists
     * @param resourceId
     */
    public void checkResourceAllocationStatus(Resource resourceExists, Integer resourceId) {

        if (resourceExists.getStatus().getStatus_ID() == 0) {
            List<Allocation> allocationList = allocationRepo.findAllocationListByResource(resourceId);
            if (allocationList.isEmpty()) {
                resourceRepo.deleteById(resourceId);
            } else {
                employeeAllocationRepo.deleteEmployeeAllocationsByAllocationIdList(allocationList);
                projectAllocationRepo.deleteProjectAllocationsByAllocationIdList(allocationList);
                otherAllocationRepo.deleteOtherAllocationsByAllocationIdList(allocationList);
                allocationRepo.deleteByAllocationIdList(allocationList);
                resourceRepo.deleteById(resourceId);
            }
        } else {
            throw new ConflictException(RestAPIMessageConstants.DELETE_ALLOCATED_RESOURCE);

        }
    }

    /**
     * Dispose resources service implementation.
     *
     * @param disposeData
     * @throws JSONException
     */
    @Override
    public void disposeResources(String disposeData) throws JSONException {

        LOGGER.info("Dispose Resources Service implementation");
        List<String> resourceList = new ArrayList<>();
        Date date = new Date();
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);
        int operationId = 5;
        JSONArray object = new JSONArray(disposeData);
        JSONArray jsonChildObject = object;
        for (int i = 0; i < jsonChildObject.length(); i++) {
            Resourcebin resourceBinObject = new Resourcebin();
            JSONObject jsonObject = (JSONObject) jsonChildObject.get(i);
            Resource resourceObject = resourceRepo.findByResourceID(jsonObject.getInt("resource_ID"));
            String disposeReason = jsonObject.getString("reason");
            int categoryId = resourceObject.getCategory().getCategory_ID();
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            if (disposeReason.length() > 255) {
                throw new DataToLongException(RestAPIMessageConstants.DISPOSE_RESOURCE_REASON);
            } else {

                if (permissionExists) {

                    this.checkDisposeResourceAllocationStatus(resourceObject, resourceBinObject, disposeReason,
                            resourceList, timeStamp);

                } else {
                    throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_DISPOSE_PERMISSION);
                }
            }
        }

    }

    /**
     * Check dispose resource allocation status.
     *
     * @param resourceObject
     * @param resourceBinobject
     * @param disposeReason
     * @param resourceList
     * @param timeStamp
     */
    public void checkDisposeResourceAllocationStatus(Resource resourceObject, Resourcebin resourceBinobject,
            String disposeReason, List<String> resourceList, Timestamp timeStamp) {
        if (resourceObject.getStatus().getStatus_ID() == 0) {
            List<ResourceAttribute> resourceAttributeObject = resourceObject.getResourceAttributes();
            List<Allocation> allocationList = allocationRepo
                    .findAllocationListByResource(resourceObject.getResource_ID());

            if (allocationList.isEmpty()) {
                resourceRepo.deleteById(resourceObject.getResource_ID());
            } else {
                employeeAllocationRepo.deleteEmployeeAllocationsByAllocationIdList(allocationList);
                projectAllocationRepo.deleteProjectAllocationsByAllocationIdList(allocationList);
                otherAllocationRepo.deleteOtherAllocationsByAllocationIdList(allocationList);
                allocationRepo.deleteByAllocationIdList(allocationList);
                resourceRepo.deleteById(resourceObject.getResource_ID());
            }
            resourceBinobject.setCode(resourceObject.getCode());
            resourceBinobject.setCreatedDate(timeStamp);
            resourceBinobject.setCategory(resourceObject.getCategory());
            resourceBinobject.setFKCreateUserID(resourceObject.getUser().getUser_ID());
            resourceBinobject.setFKStatusID(resourceObject.getStatus().getStatus_ID());
            resourceBinobject.setDisposeReason(disposeReason);
            Resourcebin resourceBinObj = resourceBinRepo.save(resourceBinobject);
            for (int j = 0; j < resourceAttributeObject.size(); j++) {
                ResourceAttribute resourceObj = resourceAttributeObject.get(j);
                ResourcebinAttribute resourcebinAttributeObj = new ResourcebinAttribute();
                resourcebinAttributeObj.setValue(resourceObj.getValue());
                resourcebinAttributeObj.setAttribute(resourceObj.getAttribute());
                resourcebinAttributeObj.setResourcebin(resourceBinObj);
                resourceBinAttributeRepo.save(resourcebinAttributeObj);
            }

        } else {
            resourceList.add(resourceObject.getCode());
            throw new ConflictException(RestAPIMessageConstants.DISPOSE_ALLOCATED_RESOURCE);
        }
    }

    /**
     * Restore resources service implementation.
     *
     * @param resourceId
     */
    @Override
    public void restoreResources(List<Resourcebin> resourceId) {

        LOGGER.info("Restore Resources Service implementation");
        Date date = new Date();
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);
        int operationId = 5;

        for (int i = 0; i < resourceId.size(); i++) {

            Resource resourceObject = new Resource();
            Resourcebin resourcebin = resourceId.get(i);
            Resourcebin resourceExists = resourceBinRepo.findByResourceID(resourcebin.getResource_ID());
            int categoryId = resourceExists.getCategory().getCategory_ID();
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            if (permissionExists) {
                List<ResourcebinAttribute> resourcebinAttributeObject = resourcebin.getResourcebinAttributes();
                resourceBinRepo.deleteById(resourcebin.getResource_ID());
                Category categoryObject = categoryRepo.findByCategoryID(resourcebin.getCategory().getCategory_ID());
                User userObject = userRepo.findByUserID(resourcebin.getFK_Create_User_ID());
                Status statusObject = statusRepo.findBystatusID(resourcebin.getFK_Status_ID());
                resourceObject.setCategory(categoryObject);
                resourceObject.setCode(resourcebin.getCode());
                resourceObject.setCreatedDate(timeStamp);
                resourceObject.setStatus(statusObject);
                resourceObject.setUser(userObject);
                Resource saveResource = resourceRepo.save(resourceObject);
                this.restoreResourceAttributesMethod(resourcebinAttributeObject, saveResource);
            } else {

                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_RESTORE_PERMISSION);
            }
        }

    }

    /**
     * Gets the resource service implementation.
     *
     * @param resourceId
     * @return Resource
     */
    @Override
    public Resource getResource(Integer resourceId) {

        LOGGER.info("Get Resource Service implementation");
        Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
        int operationId = 1;
        Resource resourceObject = resourceRepo.findByResourceID(resourceId);
        if (resourceObject == null) {

            throw new NotFoundException(RestAPIMessageConstants.RESOURCE_EXISTS_FAILURE);
        } else {
            int categoryId = resourceObject.getCategory().getCategory_ID();
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            Category categoryObject = resourceObject.getCategory();
            categoryObject.setCategoryAttributes(categoryAttributeObject);
            resourceObject.setCategory(categoryObject);
            List<ResourceAttribute> resourceAttributeList = resourceObject.getResourceAttributes();
            for (ResourceAttribute resourceAttribute : resourceAttributeList) {
                List<AttributeValue> list = new ArrayList<>(resourceAttribute.getAttribute().getAttributeValues());

                List<AttributeValue> result = list.stream().sorted((o1, o2) -> Integer
                        .valueOf(o1.getAttribute_Value_ID()).compareTo(Integer.valueOf(o2.getAttribute_Value_ID())))
                        .collect(Collectors.toList());

                Set<AttributeValue> resultSet = new LinkedHashSet<>(result);
                resourceAttribute.getAttribute().setAttributeValues(resultSet);
            }

            if (!permissionExists) {
                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_VIEW_PERMISSION);
            }
        }
        return resourceObject;
    }

    /**
     * Gets the disposed resources by category service implementation.
     *
     * @param categoryId
     * @return List<Resourcebin>
     */
    @Override
    public List<Resourcebin> getDisposedResourcesByCategory(Integer categoryId) {

        LOGGER.info("Get Disposed Resources by Category Service implementation");
        List<Resourcebin> resourceRecords = null;
        int operationId = 5;

        Category categoryExists = categoryRepo.findByCategoryID(categoryId);
        if (categoryExists == null) {
            throw new NotFoundException(RestAPIMessageConstants.CATEGORY_NOT_EXISTS);

        } else {
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            resourceRecords = resourceBinRepo.findResourceByCategory(categoryExists);
            if (permissionExists) {
                this.isResourceBinExistsMethod(resourceRecords);
            } else {

                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_DISPOSE_PERMISSION);
            }
        }

        return resourceRecords;
    }

    /**
     * Checks if is resource bin exists method.
     *
     * @param resourceRecords
     */
    public void isResourceBinExistsMethod(List<Resourcebin> resourceRecords) {

        if (resourceRecords.isEmpty()) {
            throw new NoContentException(RestAPIMessageConstants.DISPOSE_RESOURCE_FAILURE);
        }
    }

    /**
     * Gets the resources by resource id list service implementation.
     *
     * @param resourceList
     * @return List<Resource>
     */
    @Override
    public List<Resource> getResourcesbyResourceIdList(List<Integer> resourceList) {

        LOGGER.info("Get Resources by ResourceIdList Service implementation");

        Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
        List<Integer> resourceIdList = new ArrayList<>(resourceList);
        List<Resource> resourceObjectList = new ArrayList<>();
        int operationId = 1;
        for (Integer value : resourceList) {
            Resource resourceObject = resourceRepo.findByResourceID(value);
            int categoryId = resourceObject.getCategory().getCategory_ID();
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            if (!permissionExists) {
                resourceIdList.remove(value);
            }
        }
        List<Resource> resourceListObject = resourceRepo.findResourcesByResourceIdList(resourceIdList);
        for (Resource resource : resourceListObject) {
            Category categoryObject = resource.getCategory();
            categoryObject.setCategoryAttributes(categoryAttributeObject);
            resource.setCategory(resource.getCategory());
            List<ResourceAttribute> resourceAttributeList = resource.getResourceAttributes();

            List<ResourceAttribute> sortedResourceAttributeList = resourceAttributeList.stream()
                    .sorted((o1, o2) -> Integer.valueOf(o1.getAttribute().getAttribute_ID())
                            .compareTo(Integer.valueOf(o2.getAttribute().getAttribute_ID())))
                    .collect(Collectors.toList());

            resource.setResourceAttributes(sortedResourceAttributeList);
            resourceObjectList.add(resource);
            for (ResourceAttribute resourceAttribute : sortedResourceAttributeList) {
                List<AttributeValue> list = new ArrayList<>(resourceAttribute.getAttribute().getAttributeValues());

                List<AttributeValue> sortedAttributeValueList = list.stream().sorted((o1, o2) -> Integer
                        .valueOf(o1.getAttribute_Value_ID()).compareTo(Integer.valueOf(o2.getAttribute_Value_ID())))
                        .collect(Collectors.toList());

                Set<AttributeValue> resultSet = new LinkedHashSet<>(sortedAttributeValueList);
                resourceAttribute.getAttribute().setAttributeValues(resultSet);
            }

        }

        return resourceObjectList;
    }

    /**
     * Gets the disposed resource service implementation.
     *
     * @param resourceId
     * @return Resourcebin
     */
    @Override
    public Resourcebin getDisposedResource(Integer resourceId) {
        LOGGER.info("Get Disposed Resource Service implementation");
        Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
        int operationId = 6;
        Resourcebin resourcebinObject = resourceBinRepo.findByResourceID(resourceId);
        if (resourcebinObject == null) {
            throw new NotFoundException(RestAPIMessageConstants.RESOURCE_EXISTS_FAILURE);
        } else {
            int categoryId = resourcebinObject.getCategory().getCategory_ID();
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            Category categoryObject = resourcebinObject.getCategory();
            categoryObject.setCategoryAttributes(categoryAttributeObject);
            resourcebinObject.setCategory(categoryObject);
            if (!permissionExists) {
                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_DISPOSE_PERMISSION);
            }
        }

        return resourcebinObject;
    }

    /**
     * Gets the last resource by category service implementation.
     *
     * @param categoryId
     * @return the last resource
     */
    @Override
    public Resource getLastResourceByCategory(Integer categoryId) {

        LOGGER.info("Get Last Resource by categoryId Service implementation");
        List<ResourceAttribute> resourceAttributeObject = new ArrayList<>();
        Resource resourceObject = resourceRepo.findLastResourceByCategoryId(categoryId);
        resourceObject.setResourceAttributes(resourceAttributeObject);
        resourceObject.setCategory(null);

        return resourceObject;
    }

    /**
     * sort Asc Search Resources method
     *
     * @param resources
     * @param direction
     * @param attributeId
     * @param page
     * @param size
     * @return List<Resource>
     */
    public List<Resource> sortAscSearchResources(List<Resource> resources, String direction, Short attributeId) {
        List<Resource> resourceList = new ArrayList<>();
        List<Resource> otherResourceList = new ArrayList<>();
        ResourceComparator.setAttrId(attributeId);
        ResourceComparator.setDirection(direction);
        for (Resource resourceAttribute : resources) {
            boolean isFound = false;

            for (ResourceAttribute resourceAttribute1 : resourceAttribute.getResourceAttributes()) {
                if (resourceAttribute1.getAttribute().getAttribute_ID() == attributeId) {
                    resourceList.add(resourceAttribute);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                otherResourceList.add(resourceAttribute);
            }

        }
        Collections.sort(resourceList, new ResourceComparator());

        resourceList.addAll(otherResourceList);
        return resourceList;
    }

    /**
     * sort Asc Search Resourcebin method
     *
     * @param resources
     * @param direction
     * @param attributeId
     * @param page
     * @param size
     * @return List<Resourcebin>
     */
    public List<Resourcebin> sortAscSearchResourceBin(List<Resourcebin> resources, String direction,
            Short attributeId) {
        List<Resourcebin> resourceList = new ArrayList<>();
        List<Resourcebin> otherResourceList = new ArrayList<>();
        DisposedResourceComparator.setAttrId(attributeId);
        DisposedResourceComparator.setDirection(direction);
        for (Resourcebin resourceAttribute : resources) {
            boolean isFound = false;

            for (ResourcebinAttribute resourceAttribute1 : resourceAttribute.getResourcebinAttributes()) {
                if (resourceAttribute1.getAttribute().getAttribute_ID() == attributeId) {
                    resourceList.add(resourceAttribute);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                otherResourceList.add(resourceAttribute);
            }

        }

        Collections.sort(resourceList, new DisposedResourceComparator());
        resourceList.addAll(otherResourceList);
        return resourceList;
    }

    /**
     * sort Asc Search datecase method
     *
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @return int
     */
    public int sortAscResourceDateCase(ResourceAttribute resourceAttribute1, ResourceAttribute resourceAttribute2)
            throws ParseException {

        int resourceflag;
        if ("".equals(resourceAttribute1.getValue().trim()) && "".equals(resourceAttribute2.getValue().trim())) {
            resourceflag = 0;
        } else if ("".equals(resourceAttribute1.getValue().trim())
                && !"".equals(resourceAttribute2.getValue().trim())) {
            resourceflag = -1;
        } else if (!"".equals(resourceAttribute1.getValue().trim())
                && "".equals(resourceAttribute2.getValue().trim())) {
            resourceflag = 1;
        } else {
            resourceflag = new SimpleDateFormat(dateFormat).parse(resourceAttribute1.getValue().trim())
                    .compareTo(new SimpleDateFormat(dateFormat).parse(resourceAttribute2.getValue().trim()));
        }
        return resourceflag;
    }

    /**
     * sort desc Search datecase method
     *
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @return int
     */
    public int sortDescResourceDateCase(ResourceAttribute resourceAttribute1, ResourceAttribute resourceAttribute2)
            throws ParseException {

        int resourceValue;

        if ("".equals(resourceAttribute1.getValue().trim()) && "".equals(resourceAttribute2.getValue().trim())) {
            resourceValue = 0;
        } else if ("".equals(resourceAttribute1.getValue().trim())
                && !"".equals(resourceAttribute2.getValue().trim())) {
            resourceValue = 1;
        } else if (!"".equals(resourceAttribute1.getValue().trim())
                && "".equals(resourceAttribute2.getValue().trim())) {
            resourceValue = -1;
        } else {
            resourceValue = new SimpleDateFormat(dateFormat).parse(resourceAttribute1.getValue().trim())
                    .compareTo(new SimpleDateFormat(dateFormat).parse(resourceAttribute2.getValue().trim()));
            resourceValue = -resourceValue;
        }
        return resourceValue;
    }

    /**
     * sort Asc Disposed Resource datecase method
     *
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @return int
     */
    public int sortAscDisposedResourceDateCase(ResourcebinAttribute resourceAttribute1,
            ResourcebinAttribute resourceAttribute2) throws ParseException {
        int disposedResourceFlag;
        if ("".equals(resourceAttribute1.getValue().trim()) && "".equals(resourceAttribute2.getValue().trim())) {
            disposedResourceFlag = 0;
        } else if ("".equals(resourceAttribute1.getValue().trim())
                && !"".equals(resourceAttribute2.getValue().trim())) {
            disposedResourceFlag = -1;
        } else if (!"".equals(resourceAttribute1.getValue().trim())
                && "".equals(resourceAttribute2.getValue().trim())) {
            disposedResourceFlag = 1;
        } else {
            disposedResourceFlag = new SimpleDateFormat(dateFormat).parse(resourceAttribute1.getValue().trim())
                    .compareTo(new SimpleDateFormat(dateFormat).parse(resourceAttribute2.getValue().trim()));
        }
        return disposedResourceFlag;
    }

    /**
     * sort desc Disposed Resource datecase method
     *
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @return int
     */
    public int sortDescDisposedResourceDateCase(ResourcebinAttribute resourceAttribute1,
            ResourcebinAttribute resourceAttribute2) throws ParseException {
        int disposedResourceValue;
        if ("".equals(resourceAttribute1.getValue().trim()) && "".equals(resourceAttribute2.getValue().trim())) {
            disposedResourceValue = 0;
        } else if ("".equals(resourceAttribute1.getValue().trim())
                && !"".equals(resourceAttribute2.getValue().trim())) {
            disposedResourceValue = 1;
        } else if (!"".equals(resourceAttribute1.getValue().trim())
                && "".equals(resourceAttribute2.getValue().trim())) {
            disposedResourceValue = -1;
        } else {
            disposedResourceValue = new SimpleDateFormat(dateFormat).parse(resourceAttribute1.getValue().trim())
                    .compareTo(new SimpleDateFormat(dateFormat).parse(resourceAttribute2.getValue().trim()));
            disposedResourceValue = -disposedResourceValue;
        }
        return disposedResourceValue;
    }

    /**
     * Resource Allocation or Deallocation Service Implementation.
     *
     * @param allocationData
     * @return ServiceResponseBean
     * @throws JSONException
     * @throws ParseException
     */
    @Override
    public ServiceResponse resourceAllocation(String allocationData) throws JSONException, ParseException {

        LOGGER.info("Resource Allocation or DeAllocation Service implementation");
        List<String> resourceList = new ArrayList<>();
        ServiceResponse response = new ServiceResponse();
        Date fromDate = null;
        Date toDate = null;
        Date date = new Date();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int operationId = 4;
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(modifiedDate);
        JSONArray jsonArrayObject = new JSONArray(allocationData);

        for (int i = 0; i < jsonArrayObject.length(); i++) {

            Allocation allocation = new Allocation();
            JSONObject jsonObject = (JSONObject) jsonArrayObject.get(i);
            int resourceId = jsonObject.getInt("resource_ID");
            Resource resource = resourceRepo.findByResourceID(resourceId);
            int categoryId = resource.getCategory().getCategory_ID();
            Byte statusId = (byte) jsonObject.getInt("status_ID");
            Status status = statusRepo.findBystatusID(statusId);
            int userId = jsonObject.getInt("user_ID");
            User user = userRepo.findByUserID(userId);
            boolean permissionExists = permissionService.permissionExists(categoryId, operationId);
            if (permissionExists) {
                if (statusId == 2) {
                    toDate = parsedDate;
                    int allocationId = jsonObject.getInt("allocation_ID");
                    Allocation allocationObject = allocationRepo.findByAllocationID(allocationId);
                    allocationObject.setStatus(status);
                    allocationObject.setToDate(toDate);
                    allocationObject.setCreatedDate(timestamp);
                    allocationRepo.save(allocationObject);
                    statusId = 0;
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setMessage(RestAPIMessageConstants.RESOURCE_ALLOCATION);
                } else {
                    byte allocationTypeId = (byte) jsonObject.getInt("allocationtype_ID");
                    AllocationType allocationTypeObject = allocationTypeRepo.findByAllocationTypeID(allocationTypeId);
                    if (resource.getStatus().getStatus_ID() == 1) {
                        resourceList.add(resource.getCode());
                        throw new ConflictException(RestAPIMessageConstants.ALLOCATION_RESOURCE_FAILURE);
                    } else {
                        fromDate = parsedDate;
                        allocation.setAllocationType(allocationTypeObject);
                        allocation.setResource(resource);
                        allocation.setStatus(status);
                        allocation.setUser(user);
                        allocation.setFromDate(fromDate);
                        allocation.setToDate(toDate);
                        allocation.setCreatedDate(timestamp);
                        Allocation allocationObject = allocationRepo.save(allocation);
                        this.allocationMethod(allocationTypeId, jsonObject, allocationObject);
                    }
                }
                Resource resourceObject = resourceRepo.findByResourceID(resourceId);
                Status statusObject = statusRepo.findBystatusID(statusId);
                resourceObject.setStatus(statusObject);
                resourceRepo.save(resourceObject);
            } else {
                throw new AccessDeniedException(RestAPIMessageConstants.RESOURCE_ALLOCATION_PERMISSION);
            }
        }
        return response;
    }

    /**
     * Resource Allocation Method for different allocation types.
     * 
     * @param allocationTypeId
     * @param jsonObject
     * @param allocationObject
     * @throws JSONException
     */
    private void allocationMethod(byte allocationTypeId, JSONObject jsonObject, Allocation allocationObject)
            throws JSONException {
        if (allocationTypeId == 1) {
            JSONObject employeeAllocationObject = jsonObject.getJSONObject("employeeAllocations");
            Employee employee;
            JSONObject employeeObject = employeeAllocationObject.getJSONObject("employee");
            Employee employeeExists = employeeRepo.findByUserName(employeeObject.getString("sAMAccountName"));
            if (employeeExists != null) {
                employee = employeeExists;
            } else {
                employee = new Employee();
                employee.setUserName(employeeObject.getString("sAMAccountName"));
                employee.setFirstName(employeeObject.getString("firstName"));
                employee.setLastName(employeeObject.getString("lastName"));
                employee.setActiveStatus((byte) 1);
            }
            EmployeeAllocation employeeAllocation = new EmployeeAllocation();
            employeeAllocation.setAllocation(allocationObject);
            employeeAllocation.setEmployee(employee);
            employeeAllocationRepo.save(employeeAllocation);
        } else if (allocationTypeId == 2) {
            JSONObject projectAllocationObject = jsonObject.getJSONObject("projectAllocations");
            Project project;
            JSONObject projectObject = projectAllocationObject.getJSONObject("project");
            Project projectExists = projectRepo.findByProjectName(projectObject.getString("code"));
            if (projectExists != null) {
                project = projectExists;
            } else {
                project = new Project();
                project.setProjectName(projectObject.getString("code"));
                project.setActiveStatus((byte) 1);
            }
            ProjectAllocation projectAllocation = new ProjectAllocation();
            projectAllocation.setAllocation(allocationObject);
            projectAllocation.setProject(project);
            projectAllocationRepo.save(projectAllocation);
        } else {
            OtherAllocation otherAllocation = new OtherAllocation();
            JSONObject otherAllocationObject = jsonObject.getJSONObject("otherAllocations");
            String assigneeName = otherAllocationObject.getString("assignee_Name");
            otherAllocation.setAllocation(allocationObject);
            otherAllocation.setAssigneeName(assigneeName);
            otherAllocationRepo.save(otherAllocation);
        }
    }
}
