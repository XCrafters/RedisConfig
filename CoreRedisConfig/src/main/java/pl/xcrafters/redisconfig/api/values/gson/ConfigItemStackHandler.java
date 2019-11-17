package pl.xcrafters.redisconfig.api.values.gson;

import com.google.gson.*;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.values.ConfigItemStack;
import pl.xcrafters.redisconfig.api.values.ConfigMaterial;

import java.lang.reflect.Type;

public class ConfigItemStackHandler implements JsonDeserializer<ConfigItemStack>, JsonSerializer<ConfigItemStack> {

    public ConfigItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        ConfigMaterial material = new ConfigMaterial(object.get("type").getAsString());
        int amount = object.get("amount").getAsInt();
        byte data = object.get("data").getAsByte();

        return new ConfigItemStack(material, amount, data);
    }

    public JsonElement serialize(ConfigItemStack itemStack, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.add("type", RedisConfigAPI.getGson().toJsonTree(itemStack.getType()));
        object.addProperty("amount", itemStack.getAmount());
        object.addProperty("data", itemStack.getData());

        return object;
    }

}
