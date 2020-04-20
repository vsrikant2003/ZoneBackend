package com.zone.zissa.repos;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;

/**
 * The AttributeValueRepository Interface for the attribute_Value database
 * table.
 * 
 */
@Transactional
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {

    /**
     * The deleteAttributeValue method
     * 
     * @param attributeID
     */
    @Modifying
    @Query(value = "delete from attribute_value where FK_Attribute_ID =:attribute_ID ", nativeQuery = true)
    void deleteAttributeValue(@Param("attribute_ID") Short attributeID);

    /**
     * The deleteAttributeValueID method
     * 
     * @param attributeValueID
     */
    @Modifying
    @Query(value = "delete from attribute_value where Attribute_Value_ID =:attribute_Value_ID ", nativeQuery = true)
    void deleteAttributeValueID(@Param("attribute_Value_ID") Integer attributeValueID);

    /**
     * The findAttributeValueByAttribute method
     * 
     * @param attribute
     * @return Set<AttributeValue>
     */
    Set<AttributeValue> findAttributeValueByAttribute(Attribute attribute);
    
    /**
     * The findByAttributeValueID method
     * 
     * @param attributeValueId
     * @return AttributeValue
     */
    AttributeValue findByAttributeValueID(int attributeValueId);

}
