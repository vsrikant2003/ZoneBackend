package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Resourcebin;

/**
 * The ResourcebinRepository Interface for the Resourcebin database table.
 * 
 */
@Transactional
public interface ResourcebinRepository extends JpaRepository<Resourcebin, Integer> {

    /**
     * The findResourceByCategory method
     * 
     * @param category
     * @return List<Resourcebin>
     */
    List<Resourcebin> findResourceByCategory(Category category);

    /**
     * The findResourcesByCategory method
     * 
     * @param categoryID
     * @return List<Resourcebin>
     */
    @Query(value = "SELECT * FROM resourcebin where FK_Category_ID in ?1", nativeQuery = true)
    List<Resourcebin> findResourcesByCategory(List<Integer> categoryID);

    /**
     * The findResourcesByCategoryDesc method
     * 
     * @param categoryID
     * @return List<Resourcebin>
     */
    @Query(value = "SELECT * FROM resourcebin where FK_Category_ID in ?1 order by code desc", nativeQuery = true)
    List<Resourcebin> findResourcesByCategoryDesc(List<Integer> categoryID);

    /**
     * The findResourcesByCategoryAsc method
     * 
     * @param categoryID
     * @return List<Resourcebin>
     */
    @Query(value = "SELECT * FROM resourcebin where FK_Category_ID in ?1 order by code asc", nativeQuery = true)
    List<Resourcebin> findResourcesByCategoryAsc(List<Integer> categoryID);

    /**
     * The findResourcesByCategoryDesc method
     * 
     * @param categoryID
     * @return List<Resourcebin>
     */
    @Query(value = "SELECT * FROM resourcebin where FK_Category_ID in ?1 order by dispose_reason desc", nativeQuery = true)
    List<Resourcebin> findResourcesByCategoryOrderByReasonDesc(List<Integer> categoryID);

    /**
     * The findResourcesByCategoryAsc method
     * 
     * @param categoryID
     * @return List<Resourcebin>
     */
    @Query(value = "SELECT * FROM resourcebin where FK_Category_ID in ?1 order by dispose_reason asc", nativeQuery = true)
    List<Resourcebin> findResourcesByCategoryOrderByReasonAsc(List<Integer> categoryID);

    /**
     * The findByResourceID method
     * 
     * @param resourceID
     * @return Resourcebin
     */
    Resourcebin findByResourceID(int resourceID);
}
