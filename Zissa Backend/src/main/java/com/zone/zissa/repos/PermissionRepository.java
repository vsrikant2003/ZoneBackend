package com.zone.zissa.repos;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;

/**
 * The PermissionRepository Interface for the Permission database table.
 * 
 */
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    /**
     * The findByRole method.
     *
     * @param role
     * @return Set<Permission>
     */
    public Set<Permission> findByRole(Role role);

    /**
     * Delete by category.
     *
     * @param categoryID
     */
    @Modifying
    @Query(value = "delete from permission where fk_category_id =:category_ID", nativeQuery = true)
    void deleteByCategory(@Param("category_ID") Integer categoryID);

    /**
     * The deletePermission method.
     *
     * @param permissionID
     */
    @Modifying
    @Query(value = "delete from permission where Permission_ID =:permission_ID", nativeQuery = true)
    public void deletePermission(@Param("permission_ID") Integer permissionID);

    /**
     * The findPermissionByRole method
     * 
     * @param role
     * @return List<Permission>
     */
    public List<Permission> findPermissionByRole(Role role);

    /**
     * The findPermissionByRoleAndCategory method
     * 
     * @param role
     * @param category
     * @return List<Permission>
     */
    public List<Permission> findPermissionByRoleAndCategory(Role role, Category category);

}
