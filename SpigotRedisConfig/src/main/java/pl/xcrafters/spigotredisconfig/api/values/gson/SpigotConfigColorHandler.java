package pl.xcrafters.spigotredisconfig.api.values.gson;

import com.google.gson.*;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigColor;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigMaterial;

import java.lang.reflect.Type;

public class SpigotConfigColorHandler implements JsonDeserializer<SpigotConfigColor>, JsonSerializer<SpigotConfigColor> {

    public SpigotConfigColor deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new SpigotConfigColor(json.getAsString());
    }

    public JsonElement serialize(SpigotConfigColor color, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(color.toString());
    }

}
