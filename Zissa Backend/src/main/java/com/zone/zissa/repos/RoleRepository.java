package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.Role;

/**
 * The RoleRepository Interface for the Role database table.
 * 
 */
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * The findByName method
     * 
     * @param name
     * @return Role
     */
    public Role findByName(String name);

    /**
     * The findByroleID method
     * 
     * @param roleId
     * @return Role
     */
    public Role findByroleID(int roleId);

}
