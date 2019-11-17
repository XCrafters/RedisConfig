package pl.xcrafters.redisconfig.api.values.gson;

import com.google.gson.*;
import pl.xcrafters.redisconfig.api.values.ConfigMaterial;

import java.lang.reflect.Type;

public class ConfigMaterialHandler implements JsonDeserializer<ConfigMaterial>, JsonSerializer<ConfigMaterial> {

    public ConfigMaterial deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new ConfigMaterial(json.getAsString());
    }

    public JsonElement serialize(ConfigMaterial material, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(material.getName());
    }
}
