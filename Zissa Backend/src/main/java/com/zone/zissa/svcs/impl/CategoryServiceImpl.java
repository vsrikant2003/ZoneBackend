package com.zone.zissa.svcs.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.CategoryAttributeRepository;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.OperationRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.ResourcebinRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.CategoryService;

/**
 * The CategoryServiceImpl Class.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllocationServiceImpl.class);

    @Autowired
    private AttributeRepository attributeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryAttributeRepository categoryAttributeRepo;

    @Autowired
    private OperationRepository operationRepo;

    @Autowired
    private PermissionRepository permissionRepo;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResourceRepository resourceRepo;

    @Autowired
    private ResourceAttributeRepository resourceAttributeRepo;

    @Autowired
    private ResourcebinRepository resourcebinRepo;

    @Autowired
    private ResourcebinAttributeRepository resourcebinAttributeRepo;

    private String defaultKey = "isDefault";

    private Boolean value = true;

    /**
     * Add category service implementation.
     *
     * @param categoryData
     * @return Category
     * @throws JSONException
     */
    @Override
    public Category addCategory(String categoryData) throws JSONException {

        LOGGER.info("Add new Category Service implementation");
        Category category = new Category();
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        Category categoryObject = null;
        JSONObject object = new JSONObject(categoryData);
        String name = object.getString("name");
        String codePattern = object.getString("code_Pattern");
        int userId = object.getInt("user_ID");
        User userObject = userRepo.findByUserID(userId);
        category.setName(name);
        category.setUser(userObject);
        category.setCodePattern(codePattern);
        category.setCreatedDate(timestamp);

        Category categoryExists = categoryRepo.findByName(name);
        Category patternExists = categoryRepo.findByCodePattern(codePattern);
        if (patternExists != null) {

            throw new ConflictException(RestAPIMessageConstants.CODE_PATTERN_EXISTS);
        }
        if (categoryExists == null) {

            categoryObject = categoryRepo.save(category);
            JSONArray jsonChildObject = (JSONArray) object.get("categoryAttributes");
            for (int i = 0; i < jsonChildObject.length(); i++) {
                CategoryAttribute categoryAttributeObject = new CategoryAttribute();
                JSONObject jsonObject = (JSONObject) jsonChildObject.get(i);
                categoryAttributeObject.setCategory(categoryObject);
                Short attributeId = (short) jsonObject.getInt("attribute_ID");
                int keyValue = jsonObject.getInt(defaultKey);
                boolean attributeDefault = (keyValue > 0) ? value : !value;
                Attribute attributeObject = attributeRepo.getOne(attributeId);
                categoryAttributeObject.setAttribute(attributeObject);
                categoryAttributeObject.setDefault(attributeDefault);
                categoryAttributeRepo.save(categoryAttributeObject);
            }
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            com.zone.zissa.model.User userobject = userRepo.findByUserName(user.getUsername());
            Role roleObject = userobject.getRole();
            Integer[] operations = { 1, 2, 3, 4, 5, 6 };
            for (int j = 0; j < operations.length; j++) {
                Integer operationId = operations[j];
                Operation operationObject = operationRepo.findByOperationID(operationId);
                Permission permissionObject = new Permission();
                permissionObject.setRole(roleObject);
                permissionObject.setCategory(categoryObject);
                permissionObject.setOperation(operationObject);
                permissionRepo.save(permissionObject);
            }

        } else {
            throw new ConflictException(RestAPIMessageConstants.CATEGORY_EXISTS);
        }
        return categoryObject;
    }

    /**
     * Get all categories service implementation.
     *
     * @return List<Category>
     */
    @Override
    public List<Category> getAllCategories() {

        LOGGER.info("Get all Categories Service implementation");
        List<Category> categoryList = categoryRepo.findAll();
        for (Category category : categoryList) {
            category.setCategoryAttributes(null);
        }
        return categoryList;
    }

    /**
     * Get all attributes by categoryid service implementation.
     *
     * @param category_ID
     * @return List<CategoryAttribute>
     */
    @Override
    public List<CategoryAttribute> getCategoryAttributesByCategoryId(Integer categoryID) {

        LOGGER.info("Get all Attributes by Categoryid Service implementation");

        Category categoryExists = categoryRepo.findByCategoryID(categoryID);
        List<CategoryAttribute> categoryAttributeList = categoryAttributeRepo.findByCategory(categoryExists);
        for (CategoryAttribute categoryAttribute : categoryAttributeList) {
            List<AttributeValue> list = new ArrayList<>(categoryAttribute.getAttribute().getAttributeValues());

            List<AttributeValue> result = list.stream().sorted((o1, o2) -> Integer.valueOf(o1.getAttribute_Value_ID())
                    .compareTo(Integer.valueOf(o2.getAttribute_Value_ID()))).collect(Collectors.toList());

            Set<AttributeValue> resultSet = new LinkedHashSet<>(result);
            categoryAttribute.getAttribute().setAttributeValues(resultSet);
        }
        if (categoryExists == null) {
            throw new NotFoundException(RestAPIMessageConstants.CATEGORY_NOT_EXISTS);

        }
        if (categoryAttributeList.isEmpty()) {
            throw new NoContentException(RestAPIMessageConstants.NO_ATTRIBUTE_FOR_CATEGORY);

        }
        return categoryAttributeList;
    }

    /**
     * Update category service implementation.
     *
     * @param categoryData
     * @return Category
     * @throws JSONException
     */
    @Override
    public Category updateCategory(String categoryData) throws JSONException {

        LOGGER.info("Update Category Service implementation");
        Category category = new Category();
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        JSONObject object = new JSONObject(categoryData);
        String name = object.getString("name");
        int userId = object.getInt("user_ID");
        int categoryId = object.getInt("category_ID");
        Category categoryObject = categoryRepo.findByCategoryID(categoryId);
        Category categoryobj = null;
        if (categoryObject == null) {
            throw new DataNotFoundException(RestAPIMessageConstants.UPDATE_CATEGORY);

        } else {
            if (categoryRepo.findByName(name) != null && categoryRepo.findByName(name).getCategory_ID() != categoryId) {
                throw new ConflictException(RestAPIMessageConstants.CATEGORY_NAME_EXISTS);

            } else {
                String codePattern = categoryObject.getCode_Pattern();
                User userObject = userRepo.findByUserID(userId);
                category.setName(name);
                category.setUser(userObject);
                category.setCategoryID(categoryId);
                category.setCodePattern(codePattern);
                category.setCreatedDate(timestamp);
                categoryobj = categoryRepo.save(category);
            }
        }
        JSONArray jsonDeleteCategoryObject = (JSONArray) object.get("delete_categoryAttributes");
        JSONArray jsonInsertCategoryObject = (JSONArray) object.get("insert_categoryAttributes");
        JSONArray jsonUpdateCategoryObject = (JSONArray) object.get("update_defaultAttributes");
        if (jsonInsertCategoryObject.length() != 0) {
            this.insertCategoryObjectMethod(jsonInsertCategoryObject, categoryobj, categoryId);
        }
        if (jsonDeleteCategoryObject.length() != 0) {
            this.deleteCategoryObjectMethod(jsonDeleteCategoryObject, categoryId);
        }
        if (jsonUpdateCategoryObject.length() != 0) {
            for (int i = 0; i < jsonUpdateCategoryObject.length(); i++) {
                CategoryAttribute categoryAttributeObject = new CategoryAttribute();
                JSONObject jsonObject = (JSONObject) jsonUpdateCategoryObject.get(i);
                categoryAttributeObject.setCategory(categoryobj);
                int keyValue = jsonObject.getInt(defaultKey);
                boolean attributeDefault = (keyValue > 0) ? value : !value;
                int categoryAttributeId = jsonObject.getInt("category_Attribute_ID");
                CategoryAttribute categoryAttribute = categoryAttributeRepo
                        .findByCategoryAttributeID(categoryAttributeId);
                categoryAttributeObject.setDefault(attributeDefault);
                categoryAttributeObject.setCategoryAttributeID(categoryAttributeId);
                categoryAttributeObject.setAttribute(categoryAttribute.getAttribute());
                categoryAttributeRepo.save(categoryAttributeObject);
            }
        }

        return categoryobj;
    }

    /**
     * Insert category object method.
     *
     * @param jsonInsertCategoryObject the json insert category object
     * @param catobj                   the catobj
     * @param categoryId               the category id
     * @throws JSONException the JSON exception
     */
    public void insertCategoryObjectMethod(JSONArray jsonInsertCategoryObject, Category catobj, int categoryId)
            throws JSONException {

        for (int i = 0; i < jsonInsertCategoryObject.length(); i++) {
            CategoryAttribute categoryAttribute = new CategoryAttribute();
            JSONObject jsonObject = (JSONObject) jsonInsertCategoryObject.get(i);
            Short attributeId = (short) jsonObject.getInt("attribute_ID");
            Category categoryObject = categoryRepo.findByCategoryID(categoryId);
            Attribute attributeObject = attributeRepo.getOne(attributeId);
            int keyValue = jsonObject.getInt(defaultKey);
            boolean attributeDefault = (keyValue > 0) ? value : !value;
            categoryAttribute.setCategory(catobj);
            categoryAttribute.setAttribute(attributeObject);
            categoryAttribute.setDefault(attributeDefault);
            CategoryAttribute categoryAttributeObject = categoryAttributeRepo.save(categoryAttribute);
            List<Resource> resourceList = resourceRepo.findResourceByCategory(categoryObject);
            List<Resourcebin> resourcebinList = resourcebinRepo.findResourceByCategory(categoryObject);
            if (!resourceList.isEmpty()) {
                this.insertResourceAttrMethod(resourceList, categoryAttributeObject, attributeObject);
            }
            if (!resourcebinList.isEmpty()) {
                this.insertReourceBinAttrMethod(resourcebinList, categoryAttributeObject, attributeObject);
            }
        }
    }

    /**
     * Insert Resource Attributes Method
     * 
     * @param resourceList
     * @param categoryAttributeObject
     * @param attributeObject
     */
    public void insertResourceAttrMethod(List<Resource> resourceList, CategoryAttribute categoryAttributeObject,
            Attribute attributeObject) {
        for (int j = 0; j < resourceList.size(); j++) {
            ResourceAttribute resourceAttributeObject = new ResourceAttribute();
            if (attributeObject.getAttrDataType().getData_Type_ID() == 3
                    || attributeObject.getAttrDataType().getData_Type_ID() == 4) {
            	resourceAttributeObject.setValue("0.0");
            } else if (attributeObject.getAttrDataType().getData_Type_ID() == 2) {
            	resourceAttributeObject.setValue("0");
            } else {
            	resourceAttributeObject.setValue("");
            }
            resourceAttributeObject.setAttribute(categoryAttributeObject.getAttribute());
            Resource resource = resourceRepo.findByResourceID(resourceList.get(j).getResource_ID());
            resourceAttributeObject.setResource(resource);
            resourceAttributeRepo.save(resourceAttributeObject);
        }
    }

    /**
     * Insert Resourcebin Attributes Method
     * 
     * @param resourcebinList
     * @param categoryAttributeObject
     * @param attributeObject
     */
    public void insertReourceBinAttrMethod(List<Resourcebin> resourcebinList, CategoryAttribute categoryAttributeObject,
            Attribute attributeObject) {
        for (int j = 0; j < resourcebinList.size(); j++) {
            ResourcebinAttribute resourcebinAttributeObject  = new ResourcebinAttribute();
            if (attributeObject.getAttrDataType().getData_Type_ID() == 3
                    || attributeObject.getAttrDataType().getData_Type_ID() == 4) {
            	resourcebinAttributeObject.setValue("0.0");
            } else if (attributeObject.getAttrDataType().getData_Type_ID() == 2) {
            	resourcebinAttributeObject.setValue("0");
            } else {
            	resourcebinAttributeObject.setValue("");
            }
            resourcebinAttributeObject .setAttribute(categoryAttributeObject.getAttribute());
            Resourcebin resourcebin = resourcebinRepo.findByResourceID(resourcebinList.get(j).getResource_ID());
            resourcebinAttributeObject.setResourcebin(resourcebin);
            resourcebinAttributeRepo.save(resourcebinAttributeObject);
        }
    }

    /**
     * Delete category object method.
     *
     * @param jsonDeleteCategoryObject the json delete category object
     * @param categoryId               the category id
     * @throws JSONException the JSON exception
     */
    public void deleteCategoryObjectMethod(JSONArray jsonDeleteCategoryObject, int categoryId) throws JSONException {
        for (int i = 0; i < jsonDeleteCategoryObject.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonDeleteCategoryObject.get(i);
            int categoryAttributeId = jsonObject.getInt("category_Attribute_ID");
            CategoryAttribute categoryAttribute = categoryAttributeRepo.findByCategoryAttributeID(categoryAttributeId);
            Category category = categoryRepo.findByCategoryID(categoryId);
            short attributeId = categoryAttribute.getAttribute().getAttribute_ID();
            List<Resource> resourceList = resourceRepo.findResourceByCategory(category);
            List<Resourcebin> resourcebinList = resourcebinRepo.findResourceByCategory(category);
            categoryAttributeRepo.deleteCategoryAttributeId(categoryAttributeId);
            if (!resourceList.isEmpty()) {
                resourceAttributeRepo.deleteResourcesAttributes(attributeId, resourceList);
            }
            if (!resourcebinList.isEmpty()) {
                resourcebinAttributeRepo.deleteResourceBinAttributes(attributeId, resourcebinList);
            }
        }
    }

    /**
     * Delete category service implementation.
     *
     * @param category_ID
     * @return ServiceResponseBean
     */
    @Override
    public ServiceResponse deleteCategory(Integer categoryID) {

        LOGGER.info("Delete Category Service implementation");
        ServiceResponse response = new ServiceResponse();
        Category categoryExists = categoryRepo.findByCategoryID(categoryID);
        if (categoryExists != null) {
            List<Resource> resourceList = resourceRepo.findResourceByCategory(categoryExists);
            List<Resourcebin> resourcebinList = resourcebinRepo.findResourceByCategory(categoryExists);
            if (resourceList.isEmpty() && resourcebinList.isEmpty()) {
                permissionRepo.deleteByCategory(categoryID);
            }
            categoryRepo.deleteById(categoryID);
        } else {
            throw new DataNotFoundException(RestAPIMessageConstants.DELETE_CATEGORY);

        }
        return response;
    }

    /**
     * Gets the all operations.
     *
     * @return List<Operation>
     */
    @Override
    public List<Operation> getAllOperations() {
        LOGGER.info("Get all operations Service implementation");
        return operationRepo.findAll();
    }

    /**
     * Gets the all categories based on operation.
     *
     * @param operationId
     * @return the all categories based on operation
     */
    public Set<Category> getAllCategoriesBasedOnOperation(Integer operationId) {
        Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
        Set<Category> categoryList = new LinkedHashSet<>();
        Set<Permission> permissionObject = permissionService.getAllRolePermissions();
        for (Permission permission : permissionObject) {
            Operation operation = permission.getOperation();
            if (operation.getOperation_ID() == operationId) {
                Category category = permission.getCategory();
                category.setCategoryAttributes(categoryAttributeList);
                categoryList.add(category);
            }
        }
        return categoryList;
    }

    /**
     * Gets the all categories with view permissions.
     *
     * @return Set<Category>
     */
    @Override
    public Set<Category> getAllCategoriesWithViewPermissions() {

        LOGGER.info("Get all Categories with View Permission Service implementation");
        Integer operationId = 1;
        return this.getAllCategoriesBasedOnOperation(operationId);

    }

    /**
     * Gets the all categories with add permissions.
     *
     * @return Set<Category>
     */
    @Override
    public Set<Category> getAllCategoriesWithAddPermissions() {

        LOGGER.info("Get all Categories with Add Permission Service implementation");
        Integer operationId = 2;
        return this.getAllCategoriesBasedOnOperation(operationId);
    }

    /**
     * Gets all categories with dispose permissions service implementation.
     *
     * @return Set<Category>
     */
    @Override
    public Set<Category> getAllCategoriesWithDisposePermissions() {

        LOGGER.info("Get all Categories with Dispose Permissions Service implementation");
        Integer operationId = 5;
        return this.getAllCategoriesBasedOnOperation(operationId);
    }

    /**
     * Gets the all categories with allocate permissions.
     *
     * @return Set<Category>
     */
    @Override
    public Set<Category> getAllCategoriesWithAllocatePermissions() {
        LOGGER.info("Get all Categories with Allocate Permission Service implementation");
        Integer operationId = 4;
        return this.getAllCategoriesBasedOnOperation(operationId);
    }

    /**
     * Gets the attribute details by category id list.
     *
     * @param categoryId
     * @return List<CategoryAttribute>
     */
    @Override
    public List<CategoryAttribute> getCategoryAttributesByCategoryIdList(List<Category> categoryId) {
        LOGGER.info("Get all Attributes by Category List Service implementation");

        List<CategoryAttribute> categoryAttributeList = categoryAttributeRepo.findByCategoryIn(categoryId);
        if (categoryAttributeList.isEmpty()) {
            throw new NoContentException(RestAPIMessageConstants.NO_ATTRIBUTE_FOR_CATEGORY);
        }
        return categoryAttributeList;
    }
}
