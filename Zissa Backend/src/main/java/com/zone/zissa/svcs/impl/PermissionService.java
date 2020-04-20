package com.zone.zissa.svcs.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.zone.zissa.model.Permission;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.UserRepository;

/**
 * The PermissionService class.
 */
@Component
public class PermissionService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PermissionRepository permissionRepo;

    /**
     * Permission exists Method.
     *
     * @param categoryId
     * @param operationId
     * @return true, if successfull
     */
    public boolean permissionExists(Integer categoryId, Integer operationId) {
        boolean operationExists = false;
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        com.zone.zissa.model.User userObject = userRepo.findByUserName(user.getUsername());
        Set<Permission> permissionObject = permissionRepo.findByRole(userObject.getRole());
        for (Permission permission : permissionObject) {
            if (permission.getCategory().getCategory_ID() == categoryId
                    && permission.getOperation().getOperation_ID() == operationId) {
                operationExists = true;
            }
        }
        return operationExists;
    }

    /**
     * Gets all permissions.
     *
     * @return all permissions
     */
    public Set<Permission> getAllRolePermissions() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        com.zone.zissa.model.User userObject = userRepo.findByUserName(user.getUsername());
        return permissionRepo.findByRole(userObject.getRole());
    }
}
