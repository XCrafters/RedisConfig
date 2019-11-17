package pl.xcrafters.redisconfig.api.values.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.values.ConfigMaterial;
import pl.xcrafters.redisconfig.api.values.ConfigRange;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ConfigRangeHandler<T> implements JsonDeserializer<ConfigRange<T>>, JsonSerializer<ConfigRange<T>> {

    public ConfigRange<T> deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        Type valueType = ((ParameterizedType) type).getActualTypeArguments()[0];

        JsonElement minElement = object.get("min");
        JsonElement maxElement = object.get("max");

        T min;
        if(!FieldType.isNumber(minElement)) {
            min = RedisConfigAPI.getGson().fromJson(minElement, valueType);
        } else {
            Number num = minElement.getAsNumber();

            if (valueType == Integer.class) {
                min = (T) Integer.valueOf(num.intValue());
            } else if (valueType == Double.class) {
                min = (T) Double.valueOf(num.doubleValue());
            } else if (valueType == Float.class) {
                min = (T) Float.valueOf(num.floatValue());
            } else if (valueType == Long.class) {
                min = (T) Long.valueOf(num.longValue());
            } else {
                min = RedisConfigAPI.getGson().fromJson(maxElement, valueType);
            }
        }

        T max;
        if(!FieldType.isNumber(maxElement)) {
            max = RedisConfigAPI.getGson().fromJson(maxElement, valueType);
        } else {
            Number num = maxElement.getAsNumber();

            if (valueType == Integer.class) {
                max = (T) Integer.valueOf(num.intValue());
            } else if (valueType == Double.class) {
                max = (T) Double.valueOf(num.doubleValue());
            } else if (valueType == Float.class) {
                max = (T) Float.valueOf(num.floatValue());
            } else if (valueType == Long.class) {
                max = (T) Long.valueOf(num.longValue());
            } else {
                max = RedisConfigAPI.getGson().fromJson(maxElement, valueType);
            }
        }

        return new ConfigRange<>(min, max);
    }

    public JsonElement serialize(ConfigRange<T> range, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.add("min", RedisConfigAPI.getGson().toJsonTree(range.getMin()));
        object.add("max", RedisConfigAPI.getGson().toJsonTree(range.getMax()));

        return object;
    }

}
