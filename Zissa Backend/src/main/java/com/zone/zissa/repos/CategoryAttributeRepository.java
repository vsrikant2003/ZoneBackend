package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;

/**
 * The CategoryAttributeRepository Interface for the category_attribute database
 * table.
 * 
 */
@Transactional
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Integer> {

    /**
     * The getAllAttributeValue method
     * 
     * @param categoryId
     * @return List<CategoryAttribute>
     */
    public List<CategoryAttribute> findByCategory(Category categoryId);

    /**
     * The getAllAttributeValue by category list method
     * 
     * @param categoryId
     * @return List<CategoryAttribute>
     */

    public List<CategoryAttribute> findByCategoryIn(List<Category> categoryId);

    /**
     * The deleteCategoryAttributeId method
     * 
     * @param categoryAttributeID
     */
    @Modifying
    @Query(value = "delete from category_attribute where Category_Attribute_ID=:category_Attribute_ID", nativeQuery = true)
    void deleteCategoryAttributeId(@Param("category_Attribute_ID") Integer categoryAttributeID);

    /**
     * The findByCategoryAttributeID method
     * 
     * @param categoryAttributeId
     * @return CategoryAttribute
     */
    public CategoryAttribute findByCategoryAttributeID(int categoryAttributeId);
}
