package pl.xcrafters.bungeeredisconfig.api.values.gson;

import com.google.gson.*;
import pl.xcrafters.bungeeredisconfig.api.values.BungeeConfigColor;

import java.lang.reflect.Type;

public class BungeeConfigColorHandler implements JsonDeserializer<BungeeConfigColor>, JsonSerializer<BungeeConfigColor> {

    public BungeeConfigColor deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new BungeeConfigColor(json.getAsString());
    }

    public JsonElement serialize(BungeeConfigColor color, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(color.toString());
    }

}
