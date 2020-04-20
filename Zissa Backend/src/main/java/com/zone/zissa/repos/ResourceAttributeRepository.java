package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;

/**
 * The ResourceAttributeRepository Interface for the ResourceAttribute database
 * table.
 * 
 */
@Transactional
public interface ResourceAttributeRepository extends JpaRepository<ResourceAttribute, Integer> {

    /**
     * Find by attribute.
     *
     * @param attribute
     * @return List<ResourceAttribute>
     */
    List<ResourceAttribute> findByAttribute(Attribute attribute);

    /**
     * Delete resources attributes.
     *
     * @param attribute_ID
     * @param resourceidlist
     */
    @Modifying
    @Query(value = "Delete FROM resource_attribute where fk_attribute_id = ?1 and fk_resource_id in ?2", nativeQuery = true)
    void deleteResourcesAttributes(short attributeID, List<Resource> resourceIdList);

    /**
     * Find resource attributes by value and id.
     *
     * @param attribute
     * 
     * @param value
     * @return List<ResourceAttribute>
     */
    List<ResourceAttribute> findByAttributeAndValue(Attribute attribute, String value);

    /**
     * Find resource attributes by id.
     *
     * @param resourceAttributeID
     * 
     * @return ResourceAttribute
     */
    ResourceAttribute findByResourceAttributeID(int resourceAttributeID);

    /**
     * Update resources attribute Value with new value.
     * 
     * @param newValue
     * @param attributeID
     * @param oldValue
     */
    @Modifying
    @Query(value = "update resource_attribute SET value = ?1 where fk_attribute_id = ?2 and value = ?3", nativeQuery = true)
    void updateResourcesAttributes(String newValue, short attributeID, String oldValue);
}
