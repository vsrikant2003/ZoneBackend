package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.Operation;

/**
 * The OperationRepository Interface for the Operation database table.
 * 
 */
@Transactional
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    /**
     * The findByOperationID method
     * 
     * @param operationId
     * @return Operation
     */
    Operation findByOperationID(Integer operationId);

}
