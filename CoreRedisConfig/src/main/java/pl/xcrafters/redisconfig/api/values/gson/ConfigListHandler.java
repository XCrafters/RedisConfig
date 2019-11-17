package pl.xcrafters.redisconfig.api.values.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.values.ConfigList;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfigListHandler<T> implements JsonSerializer<ConfigList<T>>, JsonDeserializer<ConfigList<T>> {

    @Override
    public ConfigList<T> deserialize(JsonElement element, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray array;

        if(element instanceof JsonObject) {
            //Stary typ (konwersja)
            JsonObject object = element.getAsJsonObject();
            array = object.get("list").getAsJsonArray();
        } else {
            array = element.getAsJsonArray();
        }

        List<T> list = new ArrayList<>();

        for(JsonElement el : array) {
            Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];

            if (!el.isJsonPrimitive() || !el.getAsJsonPrimitive().isNumber()) {
                list.add(RedisConfigAPI.getGson().fromJson(el, elementType));
                continue;
            }

            Number num = el.getAsJsonPrimitive().getAsNumber();

            if (elementType == Integer.class) {
                list.add((T) Integer.valueOf(num.intValue()));
            } else if (elementType == Double.class) {
                list.add((T) Double.valueOf(num.doubleValue()));
            } else if (elementType == Float.class) {
                list.add((T) Float.valueOf(num.floatValue()));
            } else if (elementType == Long.class) {
                list.add((T) Long.valueOf(num.longValue()));
            }
        }

        return new ConfigList<>(list);
    }

    @Override
    public JsonElement serialize(ConfigList<T> list, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray array = new JsonArray();

        for(T value : list.getList()) {
            array.add(RedisConfigAPI.getGson().toJsonTree(value));
        }

        return array;
    }

}
