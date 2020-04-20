package com.zone.zissa.svcs.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;

@Service
public class DisposedResourceComparator implements Comparator<Resourcebin> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisposedResourceComparator.class);
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
    public int compare(Resourcebin resource1, Resourcebin resource2) {
        List<ResourcebinAttribute> resourceArr1 = resource1.getResourcebinAttributes();
        List<ResourcebinAttribute> resourceArr2 = resource2.getResourcebinAttributes();
        ResourceServiceImpl resourceService = new ResourceServiceImpl();
        ResourcebinAttribute resourceAttribute1 = new ResourcebinAttribute();
        ResourcebinAttribute resourceAttribute2 = null;
        for (ResourcebinAttribute resourceAttribute : resourceArr1) {
            if (resourceAttribute.getAttribute().getAttribute_ID() == attrId) {
                resourceAttribute1 = resourceAttribute;
            }
        }
        for (ResourcebinAttribute resourceAttribute : resourceArr2) {
            if (resourceAttribute.getAttribute().getAttribute_ID() == attrId) {
                resourceAttribute2 = resourceAttribute;
            }
        }

        String dataType = resourceAttribute1.getAttribute().getAttrDataType().getData_Type_Name();

        if ("ASC".equalsIgnoreCase(direction)) {

            return this.compareAttributesAsc(dataType, resourceAttribute1, resourceAttribute2, resourceService);

        } else {
            return this.compareAttributesDesc(dataType, resourceAttribute1, resourceAttribute2, resourceService);
        }
    }

    /**
     * compareAttributesAsc method
     *
     * @param dataType
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @param resourceService
     * @return int
     */
    public int compareAttributesAsc(String dataType, ResourcebinAttribute resourceAttribute1,
            ResourcebinAttribute resourceAttribute2, ResourceServiceImpl resourceService) {

        int returnValue = 0;
        switch (dataType) {
        case "String":
            returnValue = resourceAttribute1.getValue().trim()
                    .compareToIgnoreCase(resourceAttribute2.getValue().trim());
            break;
        case "Integer":

            returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
            break;
        case "Float":
        case "Currency":
            returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
            break;
        case "Date":
            try {
                returnValue = resourceService.sortAscDisposedResourceDateCase(resourceAttribute1, resourceAttribute2);
            } catch (ParseException e) {
                LOGGER.error("ERROR", e);
            }
            break;
        default:
            LOGGER.info("Default");
        }
        return returnValue;
    }

    /**
     * compareAttributesDesc method
     *
     * @param dataType
     * @param resourceAttribute1
     * @param resourceAttribute2
     * @param resourceService
     * @return int
     */
    public int compareAttributesDesc(String dataType, ResourcebinAttribute resourceAttribute1,
            ResourcebinAttribute resourceAttribute2, ResourceServiceImpl resourceService) {

        int returnValue = 0;
        switch (dataType) {
        case "String":
            returnValue = -resourceAttribute1.getValue().trim()
                    .compareToIgnoreCase(resourceAttribute2.getValue().trim());
            break;
        case "Integer":

            returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
            returnValue = -returnValue;
            break;
        case "Float":
        case "Currency":
            returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
                    .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
            returnValue = -returnValue;
            break;
        case "Date":
            try {
                returnValue = resourceService.sortDescDisposedResourceDateCase(resourceAttribute1, resourceAttribute2);
            } catch (ParseException e) {
                LOGGER.error("Error", e);
            }
            break;
        default:
            LOGGER.info("Default");
        }
        return returnValue;
    }

    public static String getDirection() {
        return direction;
    }

    public static void setDirection(String direction) {
        DisposedResourceComparator.direction = direction;
    }

    public static Short getAttrId() {
        return attrId;
    }

    public static void setAttrId(Short attrId) {
        DisposedResourceComparator.attrId = attrId;
    }
}
