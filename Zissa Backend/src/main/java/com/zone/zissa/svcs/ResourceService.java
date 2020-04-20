package com.zone.zissa.svcs;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zone.zissa.model.Resource;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.response.ServiceResponse;

/**
 * The Interface ResourceService.
 */
public interface ResourceService {

    /**
     * Adds the Resource.
     *
     * @param resourceData
     * @return List<Resource>
     */
    public List<Resource> addResource(@Valid @RequestBody List<Resource> resourceData);

    /**
     * Gets all resources by category id.
     *
     * @param categoryId
     * @return List<Resource>
     */
    public List<Resource> getResourcesByCategoryId(@RequestParam("category_ID") Integer categoryId);

    /**
     * Delete Resource.
     *
     * @param resourceId
     */
    public void deleteResource(@NotNull @PathVariable Integer resourceId);

    /**
     * Update Resource.
     *
     * @param resourceData
     * 
     * @return List<Resource>
     */
    public List<Resource> updateResource(@Valid @RequestBody List<Resource> resourceData);

    /**
     * Dispose resources.
     *
     * @param disposeData
     */
    public void disposeResources(String disposeData) throws JSONException;

    /**
     * Restore resources.
     *
     * @param resourceId
     */
    public void restoreResources(List<Resourcebin> resourceId);

    /**
     * Gets the resource.
     *
     * @param resourceId
     * @return Resource
     */
    public Resource getResource(@PathVariable Integer resourceId);

    /**
     * Gets the disposed resources by category.
     *
     * @param categoryId
     * @return List<Resourcebin>
     */
    public List<Resourcebin> getDisposedResourcesByCategory(Integer categoryId);

    /**
     * Gets the resources by resource id list.
     *
     * @param resourceIdList
     * @return List<Resource>
     */
    public List<Resource> getResourcesbyResourceIdList(List<Integer> resourceIdList);

    /**
     * Gets the dipsosed resource.
     *
     * @param resourceId
     * @return Resourcebin
     */
    public Resourcebin getDisposedResource(Integer resourceId);

    /**
     * Gets the last resource by category.
     *
     * @param categoryId
     * @return Resource
     */
    public Resource getLastResourceByCategory(Integer categoryId);

    /**
     * Gets the all resources by search term.
     *
     * @param categoryId
     * @param page
     * @param size
     * @param serachText
     * @param direction
     * @param attributeId
     * @return List<Resource>
     */
    public List<Resource> getAllResourcesBySearchTerm(List<Integer> categoryId, int page, int size, String serachText,
            String direction, short attributeId);

    /**
     * Gets the all disposed resources by search term.
     *
     * @param categoryId
     * @param page
     * @param size
     * @param searchText
     * @param direction
     * @param attributeId
     * @return List<Resourcebin>
     */
    public List<Resourcebin> getAllDisposedResourcesBySearchTerm(List<Integer> categoryId, int page, int size,
            String searchText, String direction, short attributeId);

    /**
     * Allocate or Deallocate the Resource.
     *
     * @param allocationData
     * @return ServiceResponseBean
     * @throws JSONException
     * @throws ParseException
     */
    public ServiceResponse resourceAllocation(String allocationData) throws JSONException, ParseException;
}
