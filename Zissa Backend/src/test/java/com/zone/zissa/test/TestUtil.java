package com.zone.zissa.test;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class converts Object to Json Bytes
 * 
 * 
 *
 */
public class TestUtil {

    private TestUtil() {
        throw new IllegalAccessError(" Test Utility class");
    }

    public static String convertObjectToJsonString(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }
}
