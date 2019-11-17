package pl.xcrafters.spigotredisconfig.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpigotRedisConfigUtil {

    public static String serializeItemStack(ItemStack item) {
        return item.getType().name() + (item.getData().getData() > 0 ? ":" + item.getData().getData() : "") + " " + item.getAmount();
    }

    public static ItemStack deserializeItemStack(String string) {
        String[] board = string.split(" ");
        Material material = Material.matchMaterial(board[0].split(":")[0]);
        short data = 0;
        if (board[0].split(":").length > 1) {
            data = Short.valueOf(board[0].split(":")[1]);
        }
        int amount = Integer.parseInt(board[1]);
        return new ItemStack(material, amount, data);
    }

}
