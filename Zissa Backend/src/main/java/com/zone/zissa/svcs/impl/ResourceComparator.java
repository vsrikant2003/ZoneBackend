package com.zone.zissa.svcs.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;

@Component
public class ResourceComparator implements Comparator<Resource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceComparator.class);

    private static String direction;

    private static Short attrId;

    /**
     * compare method
     *
     * @param resource1
     * @param resource2
     * @return List<Resource>
     */
    @Override
    public int compare(Resource resource1, Resource resource2) {

        List<ResourceAttribute> resourceArr1 = resource1.getResourceAttributes();
        List<ResourceAttribute> resourceArr2 = resource2.getResourceAttributes();
        ResourceServiceImpl resourceServ = new ResourceServiceImpl();
        ResourceAttribute resourceAttribute1 = new ResourceAttribute();
        ResourceAttribute resourceAttribute2 = null;
        for (ResourceAttribute resourceAttribute : resourceArr1) {
            if (resourceAttribute.getAttribute().getAttribute_ID() == getAttrId()) {
                resourceAttribute1 = resourceAttribute;
            }
        }
        for (ResourceAttribute resourceAttribute : resourceArr2) {
            if (resourceAttribute.getAttribute().getAttribute_ID() == getAttrId()) {
                resourceAttribute2 = resourceAttribute;
            }
        }

        int dataType = resourceAttribute1.getAttribute().getAttrDataType().getData_Type_ID();

        if ("ASC".equalsIgnoreCase(getDirection())) {

            return this.compareAttributesAsc(dataType, resourceAttribute1, resourceAttribute2, resourceServ);
        } else {

            return this.compareAttributesDesc(dataType, resourceAttribute1, resourceAttribute2, resourceServ);
        }
    }

    /**
     * compareAttributesAsc method
     *
     * @param dataType
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @param resourceServ
     * @return int
     */
    public int compareAttributesAsc(int dataType, ResourceAttribute resourceAttribute1,
            ResourceAttribute resourceAttribute2, ResourceServiceImpl resourceServ) {

        int returnValue = 0;

        switch (dataType) {
        case 1:
            returnValue = resourceAttribute1.getValue().trim()
                    .compareToIgnoreCase(resourceAttribute2.getValue().trim());
            break;
        case 2:
            returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
            break;
        case 3:
        case 4:
            returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
            break;
        case 5:
            try {
                returnValue = resourceServ.sortAscResourceDateCase(resourceAttribute1, resourceAttribute2);
            } catch (ParseException e) {
                LOGGER.error("ERROR", e);
            }
            break;
        default:
            LOGGER.info("Default Case");
        }
        return returnValue;
    }

    /**
     * compareAttributesDesc method
     *
     * @param dataType
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @param resourceServ
     * @return int
     */
    public int compareAttributesDesc(int dataType, ResourceAttribute resourceAttribute1,
            ResourceAttribute resourceAttribute2, ResourceServiceImpl resourceServ) {

        int returnValue = 0;

        switch (dataType) {
        case 1:
            returnValue = -resourceAttribute1.getValue().trim()
                    .compareToIgnoreCase(resourceAttribute2.getValue().trim());
            break;
        case 2:
            returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
            returnValue = -returnValue;
            break;
        case 3:
        case 4:
            returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
            returnValue = -returnValue;
            break;

        case 5:
            try {
                returnValue = resourceServ.sortDescResourceDateCase(resourceAttribute1, resourceAttribute2);
            } catch (ParseException e) {
                LOGGER.error("ERROR", e);
            }
            break;
        default:
            LOGGER.info("Default case");
        }
        return returnValue;
    }

    public static String getDirection() {
        return direction;
    }

    public static void setDirection(String direction) {
        ResourceComparator.direction = direction;
    }

    public static Short getAttrId() {
        return attrId;
    }

    public static void setAttrId(Short attrId) {
        ResourceComparator.attrId = attrId;
    }
}
