package org.ai.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private JsonUtils() {}

    public static String toJson(Object object) {

        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
