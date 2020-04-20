package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.AttrDataType;

/**
 * The AttributeDataTypeRepository Interface for the attribute database table.
 * 
 */
@Transactional
public interface AttributeDataTypeRepository extends JpaRepository<AttrDataType, Integer> {

    /**
     * the findByDataTypeNameIgnoreCase method
     *
     * @param dataTypeName
     * 
     * @return AttrDataType
     */
    AttrDataType findByDataTypeNameIgnoreCase(String dataTypeName);

    /**
     * Find AttrDataType by dataTypeId
     *
     * @param dataTypeId
     * 
     * @return AttrDataType
     */
    AttrDataType findByDataTypeID(int dataTypeId);
}
