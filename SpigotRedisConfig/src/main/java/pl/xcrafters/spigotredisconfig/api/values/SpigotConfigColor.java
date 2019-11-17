package pl.xcrafters.spigotredisconfig.api.values;

import org.bukkit.ChatColor;
import pl.xcrafters.redisconfig.api.values.ConfigColor;

public class SpigotConfigColor extends ConfigColor {

    private ChatColor color;

    public SpigotConfigColor(ChatColor color) {
        super(color.name());
        this.color = color;
    }

    public SpigotConfigColor(String name) {
        super(name);
        this.color = ChatColor.valueOf(name);
    }

    public ChatColor getChatColor() {
        return this.color;
    }

}
