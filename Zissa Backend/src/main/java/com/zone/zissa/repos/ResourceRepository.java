package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Resource;

/**
 * The ResourceRepository Interface for the Resource database table.
 * 
 */
@Transactional
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    /**
     * The findResourceByCategory method
     * 
     * @param category
     * @return List<Resource>
     */
    List<Resource> findResourceByCategory(Category category);

    /**
     * The findResourcesByResourceIdList method
     * 
     * @param resourceIdList
     * @return List<Resource>
     */
    @Query(value = "SELECT * FROM resource where Resource_ID in ?1", nativeQuery = true)
    List<Resource> findResourcesByResourceIdList(@Param("resource_ID") List<Integer> resourceIdList);

    /**
     * The findResourcesByCategory method
     * 
     * @param categoryID
     * @return List<Resource>
     */
    @Query(value = "SELECT * FROM resource where FK_Category_ID in ?1", nativeQuery = true)
    List<Resource> findResourcesByCategory(List<Integer> categoryID);

    /**
     * The findLastResourceByCategoryId method
     * 
     * @param categoryID
     * @return Resource
     */
    @Query(value = "SELECT Resource_ID,code,FK_Category_ID,Created_Date,FK_Create_User_ID,FK_Status_ID FROM resource where FK_Category_ID =:category_ID UNION ALL SELECT Resource_ID,code,FK_Category_ID,Created_Date,FK_Create_User_ID,FK_Status_ID FROM resourcebin where fk_category_id =:category_ID ORDER BY Code DESC LIMIT 1", nativeQuery = true)
    Resource findLastResourceByCategoryId(@Param("category_ID") Integer categoryID);

    /**
     * The findResourcesByCategoryDesc method
     * 
     * @param categoryID
     * @return List<Resource>
     */
    @Query(value = "SELECT * FROM resource where FK_Category_ID in ?1 order by code desc", nativeQuery = true)
    List<Resource> findResourcesByCategoryDesc(List<Integer> categoryID);

    /**
     * The findResourcesByCategoryAsc method
     * 
     * @param categoryID
     * @return List<Resource>
     */
    @Query(value = "SELECT * FROM resource where FK_Category_ID in ?1 order by code asc", nativeQuery = true)
    List<Resource> findResourcesByCategoryAsc(List<Integer> categoryID);

    /**
     * The findByResourceID method
     * 
     * @param resourceId
     * @return Resource
     */
    Resource findByResourceID(int resourceId);

}
