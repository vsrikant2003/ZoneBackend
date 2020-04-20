package com.zone.zissa.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;

/**
 * The ResourcebinAttributeRepository Interface for the ResourcebinAttribute
 * database table.
 * 
 */
@Transactional
public interface ResourcebinAttributeRepository extends JpaRepository<ResourcebinAttribute, Integer> {

    /**
     * Find by attribute.
     *
     * @param attribute
     * @return List<ResourcebinAttribute>
     */
    List<ResourcebinAttribute> findByAttribute(Attribute attribute);

    /**
     * Delete resource bin attributes.
     *
     * @param attribute_ID
     * @param resourcebinidlist
     */
    @Modifying
    @Query(value = "Delete FROM resourcebin_attribute where fk_attribute_id = ?1 and fk_resource_id in ?2", nativeQuery = true)
    void deleteResourceBinAttributes(short attributeID, List<Resourcebin> resourcebinIdList);

    /**
     * Find resource bin attributes by value and id.
     *
     * @param attribute
     * @param value
     * @return List<ResourcebinAttribute>
     */
    List<ResourcebinAttribute> findByAttributeAndValue(Attribute attribute, String value);

    /**
     * Update resources bin attribute Value with new value.
     * 
     * @param newValue
     * @param attributeID
     * @param oldValue
     */
    @Modifying
    @Query(value = "update resourcebin_attribute SET value = ?1 where fk_attribute_id = ?2 and value = ?3", nativeQuery = true)
    void updateResourceBinAttributes(String newValue, short attributeID, String oldValue);
}
