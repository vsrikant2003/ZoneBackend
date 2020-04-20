package com.zone.zissa.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The RoleMgmtController Class.
 */
@RestController
@RequestMapping("/v1/roles")
@Api(value = "zissa", tags = { "Role-mgmt-controller" })
public class RoleMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleMgmtController.class);

    @Autowired
    private RoleService roleServiceImpl;

    /**
     * Add role controller.
     *
     * @param roleData
     * @return Role
     */

    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleData", value = "role Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Add Role", notes = "Return success response if success, or exception if something wrong", response = Role.class, httpMethod = "POST", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "BadRequest"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ServiceResponse<Role> addRole(@RequestBody String roleData) {
        LOGGER.debug("Add new Role Controller implementation");

        ServiceResponse<Role> response = new ServiceResponse<>();
        try {
            Role roleObject = roleServiceImpl.addRole(roleData);
            response.setData(roleObject);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setMessage(RestAPIMessageConstants.ADD_ROLE);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.ROLE_ADDING_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ROLE_ADDING_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_BAD_REQUEST,
                    RestAPIMessageConstants.ROLE_ADDING_FAILURE);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.ROLE_ADDING_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.ROLE_ADDING_FAILURE);
        }

        return response;

    }

    /**
     * Update Role controller.
     *
     * @param roleData
     * @return Role
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleData", value = "role Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Update Role", notes = "Return success response if success, or exception if something wrong", response = Role.class, httpMethod = "PUT", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "BadRequest"),
            @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping
    public ServiceResponse<Role> updateRole(@RequestBody String roleData) {
        LOGGER.debug("Update Role Controller implementation");

        ServiceResponse<Role> response = new ServiceResponse<>();
        try {

            Role roleObject = roleServiceImpl.updateRole(roleData);
            response.setData(roleObject);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.UPDATE_ROLE);

        } catch (DataNotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_ROLE, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_ROLE_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_ROLE_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_BAD_REQUEST,
                    RestAPIMessageConstants.ROLE_UPDATION_FAILURE);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_ROLE_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.ROLE_UPDATION_FAILURE);
        }
        return response;
    }

    /**
     * Delete Role controller.
     *
     * @param role_ID
     * @return ServiceResponse
     */
    @ApiOperation(value = "Delete Role", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/{roleID}")
    public ServiceResponse deleteRole(
            @ApiParam(value = "Roleid of the user which needs to be deleted", required = true) @PathVariable Integer roleID) {
        LOGGER.debug("Delete Role Controller implementation");

        ServiceResponse response = new ServiceResponse();
        try {

            roleServiceImpl.deleteRole(roleID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.DELETE_ROLE);
        } catch (DataNotFoundException ex) {

            LOGGER.error(RestAPIMessageConstants.DELETE_ROLE, ex);
            return ExceptionHandlerClass.handle(ex);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error(RestAPIMessageConstants.DELETE_ROLE_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_CONFLICT,
                    RestAPIMessageConstants.ROLE_DELETION_EXCEPTION);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.DELETE_ROLE_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.ROLE_DELETION_FAILURE);
        }
        return response;
    }

    /**
     * Get all Roles controller.
     *
     * @return List<Role>
     */
    @ApiOperation(value = "View a list of roles", notes = "Return all roles", response = Role.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping
    public ServiceResponse<List<Role>> getAllRoles() {
        LOGGER.debug("Get all Roles Controller implementation");
        ServiceResponse<List<Role>> response = new ServiceResponse<>();
        try {
            List<Role> templateSet = roleServiceImpl.getAllRoles();
            response.setData(templateSet);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GETTING_ROLES);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.GETTING_ROLES_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_ROLES_FAILURE);

        }
        return response;
    }

    /**
     * Get all Role related permissions controller.
     * 
     * @param role_ID
     * @return Role
     */
    @ApiOperation(value = "View list of permissions for a role", notes = "Return all permissions by role", response = Role.class, httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("{roleId}/permissions")
    public ServiceResponse<Role> getAllPermissionsByRole(
            @ApiParam(value = "Roleid for which need to list permissions", required = true) @PathVariable Integer roleId) {
        LOGGER.debug("Get all Permissions by Role Controller implementation");

        ServiceResponse<Role> response = new ServiceResponse<>();
        try {

            Role roleObject = roleServiceImpl.getAllPermissionsByRole(roleId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GETTING_ALL_PERMISSIONS_BY_ROLE);
            response.setData(roleObject);

        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GETTING_PERMISSIONS_BY_ROLE_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.GETTING_PERMISSIONS_BY_ROLE_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_ALL_PERMISSIONS_BY_ROLE_FAILURE);
        }
        return response;
    }

    /**
     * Get all permissions based on role and category controller.
     *
     * @param role_ID
     * @param category_ID
     * @return List<Permissions>
     */
    @ApiOperation(value = "View list of permissions based on role and category", notes = "Return list of permissions", response = Permission.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 204, response = ServiceResponse.class, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/permissions")
    public ServiceResponse<List<Permission>> getAllPermissionsByRoleAndCategory(@RequestParam("role_ID") Integer roleID,
            @RequestParam("category_ID") Integer categoryID) {
        LOGGER.debug("Get all Permissions by role and category Controller implementation");

        ServiceResponse<List<Permission>> response = new ServiceResponse<>();
        try {

            List<Permission> permissionObject = roleServiceImpl.getAllPermissionsByRoleAndCategory(roleID, categoryID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GETTING_PERMISSIONS_BY_ROLE_CATEGORY);
            response.setData(permissionObject);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.GETTING_PERMISSIONS_BY_ROLE_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GETTING_PERMISSIONS_BY_ROLE_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.GETTING_PERMISSIONS_BY_ROLE_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_PERMISSIONS_BY_ROLE_CATEGORY_FAILURE);

        }
        return response;
    }
}
