package com.zone.zissa.svcs;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.response.ServiceResponse;

/**
 * The Interface CategoryService.
 */
public interface CategoryService {

    /**
     * Adds the category.
     *
     * @param categoryData
     * @return Category
     * @throws JSONException
     */
    public Category addCategory(@Valid @RequestBody String categoryData) throws JSONException;

    /**
     * Update category.
     *
     * @param categoryData
     * @return Category
     * @throws JSONException
     */
    public Category updateCategory(@Valid @RequestBody String categoryData) throws JSONException;

    /**
     * Get all the categories.
     *
     * @return List<Category>
     */
    public List<Category> getAllCategories();

    /**
     * Get all the attributes info by categoryid.
     *
     * @param categoryID
     * @return List<CategoryAttribute>
     */
    public List<CategoryAttribute> getCategoryAttributesByCategoryId(@PathVariable Integer categoryId);

    /**
     * Delete Category.
     *
     * @param categoryID
     * @return ServiceResponse
     */
    public ServiceResponse deleteCategory(@NotNull @PathVariable Integer categoryId);

    /**
     * Gets the all operations.
     *
     * @return List<Operation>
     */
    public List<Operation> getAllOperations();

    /**
     * Gets the all categories with view permissions.
     *
     * @return Set<Category>
     */
    public Set<Category> getAllCategoriesWithViewPermissions();

    /**
     * Gets the all categories with add permissions.
     *
     * @return Set<Category>
     */
    public Set<Category> getAllCategoriesWithAddPermissions();

    /**
     * Gets the all categories with dispose permissions.
     *
     * @return Set<Category>
     */
    public Set<Category> getAllCategoriesWithDisposePermissions();

    /**
     * Gets the all categories with allocate permissions.
     *
     * @return Set<Category>
     */
    public Set<Category> getAllCategoriesWithAllocatePermissions();

    /**
     * Gets the attribute details by category id list.
     *
     * @param category_ID
     * @return List<CategoryAttribute>
     */
    public List<CategoryAttribute> getCategoryAttributesByCategoryIdList(List<Category> categoryId);
}
