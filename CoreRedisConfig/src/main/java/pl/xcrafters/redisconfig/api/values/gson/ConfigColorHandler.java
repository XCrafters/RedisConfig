package pl.xcrafters.redisconfig.api.values.gson;

import com.google.gson.*;
import pl.xcrafters.redisconfig.api.values.ConfigColor;

import java.lang.reflect.Type;

public class ConfigColorHandler implements JsonDeserializer<ConfigColor>, JsonSerializer<ConfigColor> {

    public ConfigColor deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new ConfigColor(json.getAsString());
    }

    public JsonElement serialize(ConfigColor color, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(color.toString());
    }

}
