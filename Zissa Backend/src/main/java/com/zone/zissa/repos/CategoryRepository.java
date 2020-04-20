package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.Category;

/**
 * The CategoryRepository Interface for the Category database table.
 * 
 */
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * The findByName method
     * 
     * @param name
     * @return Category
     */
    public Category findByName(String name);
    
    /**
     * The findByCategoryID method
     * 
     * @param categoryID
     * @return Category
     */
    public Category findByCategoryID(Integer categoryID);
    
    /**
     * The findByCodePattern method
     * 
     * @param codePattern
     * @return Category
     */
    public Category findByCodePattern(String codePattern);

}
