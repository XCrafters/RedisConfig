package pl.xcrafters.spigotredisconfig.api.values.gson;

import com.google.gson.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigItemStack;

import java.lang.reflect.Type;

public class SpigotConfigItemStackHandler implements JsonSerializer<SpigotConfigItemStack>, JsonDeserializer<SpigotConfigItemStack> {
    public SpigotConfigItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        ItemStack item = new ItemStack(Material.valueOf(object.get("type").getAsString()), object.get("amount").getAsInt(), object.get("data") == null ? 0 : object.get("data").getAsByte());

        return new SpigotConfigItemStack(item);
    }

    public JsonElement serialize(SpigotConfigItemStack itemStack, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.add("type", RedisConfigAPI.getGson().toJsonTree(itemStack.getType()));
        object.addProperty("amount", itemStack.getAmount());
        object.addProperty("data", itemStack.getData());

        return object;
    }
}
