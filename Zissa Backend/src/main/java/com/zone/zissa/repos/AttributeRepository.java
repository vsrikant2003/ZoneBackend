package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.Attribute;

/**
 * The AttributeDataTypeRepository Interface for the attribute database table.
 * 
 */
@Transactional
public interface AttributeRepository extends JpaRepository<Attribute, Short> {
    /**
     * The findByName method
     * 
     * @param name
     * @return Attribute
     */
    public Attribute findByName(String name);

    /**
     * The findByAttributeID method
     * 
     * @param attrid
     * @return Attribute
     */
    public Attribute findByAttributeID(short attributeId);

}
