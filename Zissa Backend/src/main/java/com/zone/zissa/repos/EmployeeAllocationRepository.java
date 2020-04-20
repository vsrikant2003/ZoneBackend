package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.EmployeeAllocation;

/**
 * The EmployeeAllocationRepository Interface for the Employee_Allocation
 * database table.
 * 
 */
@Transactional
public interface EmployeeAllocationRepository extends JpaRepository<EmployeeAllocation, Integer> {

    /**
     * The findByAllocation method
     * 
     * @param allocation
     * @return EmployeeAllocation
     */

    public EmployeeAllocation findByAllocation(Allocation allocation);

    /**
     * Delete employee allocations by allocation id list.
     *
     * @param allocationlist
     */
    @Modifying
    @Query(value = "delete FROM employee_allocation where fk_allocation_id in ?1", nativeQuery = true)
    void deleteEmployeeAllocationsByAllocationIdList(@Param("allocation_ID") List<Allocation> allocationList);
}
