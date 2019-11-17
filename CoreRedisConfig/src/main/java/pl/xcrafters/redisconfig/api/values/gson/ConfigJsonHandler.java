package pl.xcrafters.redisconfig.api.values.gson;

import com.google.gson.*;
import pl.xcrafters.redisconfig.api.values.ConfigJson;

import java.lang.reflect.Type;

public class ConfigJsonHandler implements JsonSerializer<ConfigJson>, JsonDeserializer<ConfigJson> {

    @Override
    public ConfigJson deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new ConfigJson(jsonElement);
    }

    @Override
    public JsonElement serialize(ConfigJson configJson, Type type, JsonSerializationContext jsonSerializationContext) {
        return configJson.getJson();
    }

}
