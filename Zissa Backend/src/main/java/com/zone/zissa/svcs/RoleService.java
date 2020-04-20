package com.zone.zissa.svcs;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;

/**
 * The Interface RoleService.
 */
public interface RoleService {

    /**
     * Adds the Role.
     *
     * @param roleData
     * @return Role
     */
    public Role addRole(@Valid @RequestBody String roleData) throws JSONException;

    /**
     * Update Role.
     *
     * @param roleData
     * @return Role
     */
    public Role updateRole(@Valid @RequestBody String roleData) throws JSONException;

    /**
     * Delete Role.
     *
     * @param roleId
     */
    public void deleteRole(@NotNull @PathVariable Integer roleId);

    /**
     * Gets all roles.
     *
     * @return List<Role>
     */
    public List<Role> getAllRoles();

    /**
     * Gets the all permissions by role.
     *
     * @param roleId
     * @return Role
     */
    public Role getAllPermissionsByRole(@RequestParam("role_ID") Integer roleId);

    /**
     * Gets the all permissions by role and category.
     *
     * @param roleId
     * 
     * @param categoryId
     * @return List<Permission>
     */
    public List<Permission> getAllPermissionsByRoleAndCategory(Integer roleId, Integer categoryId);
}
