package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.Status;

/**
 * The StatusRepository Interface for the Status database table.
 * 
 */
@Transactional
public interface StatusRepository extends JpaRepository<Status, Byte> {

    /**
     * The findBystatusID method
     * 
     * @param statusId
     * @return Status
     */
    Status findBystatusID(Byte statusId);

}
