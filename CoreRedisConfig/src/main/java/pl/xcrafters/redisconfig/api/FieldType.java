package pl.xcrafters.redisconfig.api;

import com.google.gson.JsonElement;
import pl.xcrafters.redisconfig.api.values.*;

public enum FieldType {
    BOOLEAN,
    STRING,
    INTEGER,
    DOUBLE,
    FLOAT,
    LONG,
    COLOR,
    MATERIAL,
    ITEM_STACK,
    JSON,
    RANGE,
    LIST,
    MAP,
    CUSTOM_CLASS;

    public static FieldType getFieldType(Object object) {
        if(object instanceof Boolean) {
            return FieldType.BOOLEAN;
        } else if(object instanceof String) {
            return FieldType.STRING;
        } else if(object instanceof Integer) {
            return FieldType.INTEGER;
        } else if(object instanceof Double) {
            return FieldType.DOUBLE;
        } else if(object instanceof Float) {
            return FieldType.FLOAT;
        } else if(object instanceof Long) {
            return FieldType.LONG;
        } else if(object instanceof ConfigColor) {
            return FieldType.COLOR;
        } else if(object instanceof ConfigMaterial) {
            return FieldType.MATERIAL;
        } else if(object instanceof ConfigItemStack) {
            return FieldType.ITEM_STACK;
        } else if(object instanceof ConfigJson) {
            return FieldType.JSON;
        } else if(object instanceof ConfigRange) {
            return FieldType.RANGE;
        } else if(object instanceof ConfigList) {
            return FieldType.LIST;
        } else if(object instanceof ConfigMap) {
            return FieldType.MAP;
        }

        return FieldType.CUSTOM_CLASS;
    }

    public static boolean isNumber(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber();
    }

    public static boolean isInteger(Number number) {
        return Math.ceil(number.doubleValue()) == number.longValue();
    }

}
