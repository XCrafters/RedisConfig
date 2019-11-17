package pl.xcrafters.redisconfig.api.values.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.values.ConfigMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConfigMapHandler<K, V> implements JsonSerializer<ConfigMap<K, V>>, JsonDeserializer<ConfigMap<K, V>> {

    @Override
    public ConfigMap<K, V> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        if(object.has("keyType") && object.has("valueType") && object.has("map")) {
            //Stary typ (konwersja)
            object = object.get("map").getAsJsonObject();
        }

        HashMap<K, V> map = new HashMap<>();

        for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Type keyType = ((ParameterizedType) type).getActualTypeArguments()[0];
            Type valueType = ((ParameterizedType) type).getActualTypeArguments()[1];

            K key;
            V value;

            if(keyType == String.class) {
                key = (K) entry.getKey();
            } else if(keyType == Integer.class) {
                key = (K) Integer.valueOf(entry.getKey());
            } else if(keyType == Double.class) {
                key = (K) Double.valueOf(entry.getKey());
            } else if(keyType == Float.class) {
                key = (K) Float.valueOf(entry.getKey());
            } else if(keyType == Long.class) {
                key = (K) Long.valueOf(entry.getKey());
            } else {
                key = RedisConfigAPI.getGson().fromJson(entry.getKey(), keyType);
            }

            if (!entry.getValue().isJsonPrimitive() || !entry.getValue().getAsJsonPrimitive().isNumber()) {
                value = RedisConfigAPI.getGson().fromJson(entry.getValue(), valueType);
            } else {
                Number num = entry.getValue().getAsJsonPrimitive().getAsNumber();

                if (valueType == Integer.class) {
                    value = (V) Integer.valueOf(num.intValue());
                } else if (valueType == Double.class) {
                    value = (V) Double.valueOf(num.doubleValue());
                } else if (valueType == Float.class) {
                    value = (V) Float.valueOf(num.floatValue());
                } else if (valueType == Long.class) {
                    value = (V) Long.valueOf(num.longValue());
                } else {
                    value = RedisConfigAPI.getGson().fromJson(entry.getValue(), valueType);
                }
            }

            map.put(key, value);
        }

        return new ConfigMap<>(map);
    }

    @Override
    public JsonElement serialize(ConfigMap<K, V> map, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        for(Map.Entry<K, V> entry : map.getMap().entrySet()) {
            object.add(entry.getKey().toString(), RedisConfigAPI.getGson().toJsonTree(entry.getValue()));
        }

        return object;
    }
}
