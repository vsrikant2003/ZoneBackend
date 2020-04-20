package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.AllocationType;

/**
 * The AllocationTypeRepository Interface for the allocationType database table.
 * 
 */
@Transactional
public interface AllocationTypeRepository extends JpaRepository<AllocationType, Byte> {

    /**
     * Find AllocationType by allocationtypeId
     *
     * @param allocationtypeId
     * 
     * @return AllocationType
     */
    AllocationType findByAllocationTypeID(byte allocationtypeId);

}
