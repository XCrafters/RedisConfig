package pl.xcrafters.bungeeredisconfig.api.values;

import net.md_5.bungee.api.ChatColor;
import pl.xcrafters.redisconfig.api.values.ConfigColor;

public class BungeeConfigColor extends ConfigColor {

    private ChatColor color;

    public BungeeConfigColor(ChatColor color) {
        super(color.name());
        this.color = color;
    }

    public BungeeConfigColor(String name) {
        super(name);
        this.color = ChatColor.valueOf(name);
    }

    public ChatColor getChatColor() {
        return this.color;
    }

}
