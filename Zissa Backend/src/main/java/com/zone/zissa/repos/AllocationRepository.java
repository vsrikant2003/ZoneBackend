package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zone.zissa.model.Allocation;

/**
 * The AllocationRepository Interface for the allocation database table.
 * 
 */
@Transactional
public interface AllocationRepository extends JpaRepository<Allocation, Integer> {

    /**
     * Find allocations by resource.
     *
     * @param resourceID
     * @return Allocation
     */
    @Query(value = "SELECT * FROM allocation where fk_resource_id =:resource_ID and fk_status_id=1", nativeQuery = true)
    Allocation findAllocationsByResource(@Param("resource_ID") Integer resourceID);

    /**
     * Find allocation history by resource.
     *
     * @param resourceID
     * @return List<Allocation>
     */
    @Query(value = "SELECT * FROM allocation where fk_resource_id =:resource_ID ORDER BY allocation_id desc limit 5", nativeQuery = true)
    List<Allocation> findAllocationHistoryByResource(@Param(value = "resource_ID") Integer resourceID);

    /**
     * Find allocations list by resource.
     *
     * @param resourceID the resource ID
     * @return List<Allocation>
     */
    @Query(value = "SELECT * FROM allocation where fk_resource_id =:resource_ID", nativeQuery = true)
    List<Allocation> findAllocationListByResource(@Param(value = "resource_ID") Integer resourceID);

    /**
     * Delete by allocation id list.
     *
     * @param allocationlist
     */
    @Modifying
    @Query(value = "delete FROM allocation where allocation_id in ?1", nativeQuery = true)
    void deleteByAllocationIdList(List<Allocation> allocationlist);

    /**
     * Find allocation by allocationId
     *
     * @param allocationId
     * 
     * @return Allocation
     */
    Allocation findByAllocationID(int allocationId);
}
