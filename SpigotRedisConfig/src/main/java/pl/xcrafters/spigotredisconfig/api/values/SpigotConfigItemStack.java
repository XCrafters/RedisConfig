package pl.xcrafters.spigotredisconfig.api.values;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.values.ConfigItemStack;

public class SpigotConfigItemStack extends ConfigItemStack {

    private ItemStack itemStack;

    public SpigotConfigItemStack(ItemStack itemStack) {
        super(new SpigotConfigMaterial(itemStack.getType()), itemStack.getAmount(), itemStack.getData().getData());
        this.itemStack = itemStack;
    }

    public SpigotConfigItemStack(Material type, int amount) {
        super(new SpigotConfigMaterial(type), amount);
        this.itemStack = new ItemStack(type, amount);
    }

    public SpigotConfigItemStack(Material type, int amount, byte data) {
        super(new SpigotConfigMaterial(type), amount, data);
        this.itemStack = new ItemStack(type, amount, data);
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

}
