package com.zone.zissa.svcs;

import java.util.List;

import com.zone.zissa.model.Allocation;

/**
 * The Interface AllocationService.
 */
public interface AllocationService {

    /**
     * Gets the all allocation details by resource.
     *
     * @param resource_ID
     * @return ServiceResponse<List<Allocation>>
     */
    public List<Allocation> getAllAllocationDetailsByResource(Integer resourceId);

    /**
     * get All Resources By SearchTerm
     * 
     * @param categoryId
     * @param page
     * @param size
     * @param searchText
     * @param direction
     * @param attrname
     * @return List<Resource>
     */
    public Object getAllResourcesBySearchTerm(List<Integer> categoryId, int page, int size, String searchText,
            String direction, short attrid);
}
