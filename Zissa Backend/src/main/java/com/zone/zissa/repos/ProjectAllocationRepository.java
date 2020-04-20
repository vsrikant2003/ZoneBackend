package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.ProjectAllocation;

/**
 * The ProjectAllocationRepository Interface for the Project_Allocation database
 * table.
 * 
 */
@Transactional
public interface ProjectAllocationRepository extends JpaRepository<ProjectAllocation, Integer> {

    /**
     * Delete project allocations by allocation id list.
     *
     * @param allocationList
     */
    @Modifying
    @Query(value = "delete FROM project_allocation where fk_allocation_id in ?1", nativeQuery = true)
    void deleteProjectAllocationsByAllocationIdList(@Param("allocation_ID") List<Allocation> allocationList);
}
