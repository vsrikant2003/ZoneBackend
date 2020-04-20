package com.zone.zissa.svcs;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;

/**
 * The Interface AttributeService.
 */
public interface AttributeService {

    /**
     * Adds the attribute.
     *
     * @param attributeData
     * @return Attribute
     */
    public Attribute addAttribute(@Valid @RequestBody String attributeData) throws JSONException;

    /**
     * Update attribute.
     *
     * @param attributeData
     * @return Attribute
     */
    public Attribute updateAttribute(@Valid @RequestBody String attributeData) throws JSONException;

    /**
     * Delete attribute.
     *
     * @param attributeId
     */
    public void deleteAttribute(@NotNull @PathVariable Short attributeId);

    /**
     * Get the all attributes.
     *
     * @return List<Attribute>
     */
    public List<Attribute> getAllAttributes();

    /**
     * Get all attribute datatypes.
     *
     * @return List<AttrDataType>
     */
    public List<AttrDataType> getAllAttributeDataTypes();

    /**
     * Gets the attribute info by id.
     *
     * @param attributeId
     * @return Attribute
     */
    public Attribute getAttributeInfoById(@NotNull @PathVariable Short attributeId);
}
