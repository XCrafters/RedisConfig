package pl.xcrafters.spigotredisconfig.api.values.gson;

import com.google.gson.*;
import pl.xcrafters.redisconfig.api.values.ConfigMaterial;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigMaterial;
import pl.xcrafters.spigotredisconfig.config.SpigotConfigManager;

import java.lang.reflect.Type;

public class SpigotConfigMaterialHandler implements JsonDeserializer<SpigotConfigMaterial>, JsonSerializer<SpigotConfigMaterial> {

    public SpigotConfigMaterial deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new SpigotConfigMaterial(json.getAsString());
    }

    public JsonElement serialize(SpigotConfigMaterial material, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(material.getName());
    }
}
