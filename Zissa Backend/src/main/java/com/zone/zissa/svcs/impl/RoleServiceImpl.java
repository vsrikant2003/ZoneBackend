package com.zone.zissa.svcs.impl;

import java.util.ArrayList;
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
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.OperationRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.svcs.RoleService;

/**
 * The RoleServiceImpl class.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private PermissionRepository permissionRepo;

    @Autowired
    private OperationRepository operationRepo;

    private String categoryid = "category_ID";

    /**
     * Add role service implementation.
     *
     * @param roleData
     * @return Role
     * @throws JSONException
     */
    @Override
    public Role addRole(String roleData) throws JSONException {

        LOGGER.info("Add new Role Service implementation");
        Role role = new Role();
        List<Permission> permissionList = new ArrayList<>();
        Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
        Role roleObject = null;
        JSONObject jsonObject = new JSONObject(roleData);
        String name = (String) jsonObject.get("name");
        int adminType = jsonObject.getInt("administration_type");
        int defaultCategoryId = jsonObject.getInt(categoryid);
        role.setName(name);
        role.setAdministration(adminType);
        role.setDefaultCategory(categoryRepo.findByCategoryID(defaultCategoryId));
        Role roleExists = roleRepo.findByName(name);

        if (roleExists == null) {

            roleObject = roleRepo.save(role);
            JSONArray jsonChildObject = (JSONArray) jsonObject.get("permissions");

            for (int i = 0; i < jsonChildObject.length(); i++) {

                JSONObject json = (JSONObject) jsonChildObject.get(i);
                Integer categoryId = json.getInt(categoryid);
                Category categoryObject = categoryRepo.findByCategoryID(categoryId);
                categoryObject.setCategoryAttributes(categoryAttributeObject);
                JSONArray list = json.getJSONArray("operation");
                this.setPermissionsList(list, roleObject, categoryObject, permissionList);
                roleObject.setPermissions(permissionList);
            }
        } else {
            throw new ConflictException(RestAPIMessageConstants.ROLE_EXISTS);
        }
        return roleObject;
    }

    /**
     * setPermissionsList method
     * 
     * @param list
     * @param roleObject
     * @param categoryObject
     * @param permissionList
     * @throws JSONException
     */
    public void setPermissionsList(JSONArray list, Role roleObject, Category categoryObject,
            List<Permission> permissionList) throws JSONException {
        for (int j = 0; j < list.length(); j++) {
            Integer operationId = (Integer) list.get(j);
            Operation operationObject = operationRepo.findByOperationID(operationId);
            Permission permissionObject = new Permission();
            permissionObject.setRole(roleObject);
            permissionObject.setCategory(categoryObject);
            permissionObject.setOperation(operationObject);
            Permission addPermissions = permissionRepo.save(permissionObject);
            permissionList.add(addPermissions);
        }
    }

    /**
     * Get all roles service implementation.
     *
     * @return List<Role>
     */
    @Override
    public List<Role> getAllRoles() {

        LOGGER.info("Get all Roles Service implementation");
        List<Role> rolesList = roleRepo.findAll();
        List<Role> templateSet = new ArrayList<>();
        for (Role reportTemplate : rolesList) {
            Role newReportTemplate = new Role();
            newReportTemplate.setRoleID(reportTemplate.getRole_ID());
            newReportTemplate.setName(reportTemplate.getName());
            newReportTemplate.setAdministration(reportTemplate.getAdministration());
            templateSet.add(newReportTemplate);
        }
        return templateSet;
    }

    /**
     * Get all permissions by role service implementation.
     *
     * @param role_ID
     * @return all permissions by role
     */
    @Override
    public Role getAllPermissionsByRole(Integer roleId) {
        LOGGER.info("Get Role Permissions Details by role_ID Service implementation");
        Role roleObject = roleRepo.findByroleID(roleId);
        if (roleObject != null) {
            List<Permission> permissionObject = roleObject.getPermissions();
            for (Permission permission : permissionObject) {
                Category categoryObject = permission.getCategory();
                categoryObject.setUser(null);
                categoryObject.setCategoryAttributes(null);
            }
        } else {
            throw new NotFoundException(RestAPIMessageConstants.ROLE_NOT_EXISTS);
        }
        return roleObject;
    }

    /**
     * Update role service implementation.
     *
     * @param roleData
     * @return Role
     * @throws JSONException
     */
    @Override
    public Role updateRole(String roleData) throws JSONException {

        LOGGER.info("Update Role Service implementation");
        Role role = new Role();
        Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
        List<Permission> permissionList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(roleData);
        String name = jsonObject.getString("name");
        int roleId = jsonObject.getInt("role_ID");
        int adminType = jsonObject.getInt("administration_type");
        int defaultCategoryId = jsonObject.getInt(categoryid);
        role.setAdministration(adminType);
        role.setName(name);
        role.setRoleID(roleId);
        role.setDefaultCategory(categoryRepo.findByCategoryID(defaultCategoryId));
        Role roleExists = roleRepo.findByroleID(roleId);
        Role roleObject = null;
        if (roleExists == null) {
            throw new DataNotFoundException(RestAPIMessageConstants.UPDATE_ROLE);

        } else {
            if (roleRepo.findByName(name) != null && roleRepo.findByName(name).getRole_ID() != roleId) {
                throw new ConflictException(RestAPIMessageConstants.ROLE_NAME_EXISTS);
            } else {
                roleObject = roleRepo.save(role);
            }
        }
        JSONArray jsonPermissionObject = (JSONArray) jsonObject.get("permissions");
        for (int i = 0; i < jsonPermissionObject.length(); i++) {

            JSONObject jsonObj = (JSONObject) jsonPermissionObject.get(i);
            int categoryId = jsonObj.getInt(categoryid);
            Category categoryObject = categoryRepo.findByCategoryID(categoryId);
            categoryObject.setCategoryAttributes(categoryAttributeObject);
            JSONObject deleteObject = jsonObj.getJSONObject("delete_operation");
            JSONObject insertObject = jsonObj.getJSONObject("insert_operation");

            if (deleteObject.length() != 0) {

                this.deletePermissions(deleteObject);
            }
            if (insertObject.length() != 0) {

                JSONArray list = insertObject.getJSONArray("operation_ID");
                this.setPermissionsList(list, roleObject, categoryObject, permissionList);
            }
            List<Permission> permissionObject = permissionRepo.findPermissionByRole(roleObject);
            List<Permission> newPermissionObject = new ArrayList<>();
            for (Permission permission : permissionObject) {
                newPermissionObject.add(permission);
            }
            roleObject.setPermissions(newPermissionObject);
        }
        return roleObject;
    }

    /**
     * deletePermissions method
     * 
     * @param deleteObject
     * @throws JSONException
     */
    public void deletePermissions(JSONObject deleteObject) throws JSONException {
        JSONArray list = deleteObject.getJSONArray("permission_ID");
        for (int j = 0; j < list.length(); j++) {
            Integer permissionId = (Integer) list.get(j);
            permissionRepo.deletePermission(permissionId);
        }
    }

    /**
     * Delete role service implementation.
     *
     * @param role_ID
     */
    @Override
    public void deleteRole(Integer roleId) {

        LOGGER.info("Delete Role Service implementation");
        Role roleExists = roleRepo.findByroleID(roleId);
        if (roleExists != null) {
            roleRepo.deleteById(roleId);

        } else {
            throw new DataNotFoundException(RestAPIMessageConstants.DELETE_ROLE);

        }

    }

    /**
     * Get all permissions by role and category.
     *
     * @param role_ID
     * @param category_ID
     * @return List<Permission>
     */
    @Override
    public List<Permission> getAllPermissionsByRoleAndCategory(Integer roleId, Integer categoryId) {

        LOGGER.info("Get All Permissions Details by role_ID and category_ID Service implementation");
        Category categoryExists = categoryRepo.findByCategoryID(categoryId);
        List<Permission> permissionObject = null;
        if (categoryExists != null) {
            permissionObject = permissionRepo.findPermissionByRoleAndCategory(roleRepo.findByroleID(roleId),
                    categoryRepo.findByCategoryID(categoryId));
            if (permissionObject.isEmpty()) {
                throw new NoContentException(RestAPIMessageConstants.NO_PERMISSION_FOR_CATEGORY_ROLE);
            }
        } else {
            throw new NotFoundException(RestAPIMessageConstants.CATEGORY_NOT_EXISTS);
        }
        return permissionObject;
    }
}
