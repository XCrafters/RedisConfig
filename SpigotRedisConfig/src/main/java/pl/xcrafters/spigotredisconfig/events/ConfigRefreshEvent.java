package pl.xcrafters.spigotredisconfig.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigRefreshEvent extends Event {

    private JavaPlugin plugin;

    public ConfigRefreshEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public JavaPlugin getPlugin() { return this.plugin; }

}
