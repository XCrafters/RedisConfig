package pl.xcrafters.spigotredisconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.spigotredisconfig.api.RedisConfigAPI;
import pl.xcrafters.spigotredisconfig.api.SpigotRedisConfigManager;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigColor;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigItemStack;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigMaterial;
import pl.xcrafters.spigotredisconfig.api.values.gson.SpigotConfigColorHandler;
import pl.xcrafters.spigotredisconfig.api.values.gson.SpigotConfigItemStackHandler;
import pl.xcrafters.spigotredisconfig.api.values.gson.SpigotConfigMaterialHandler;
import pl.xcrafters.spigotredisconfig.events.ConfigRefreshEvent;
import pl.xcrafters.spigotredisconfig.commands.ConfigCommand;
import pl.xcrafters.spigotredisconfig.config.SpigotConfigManager;
import pl.xcrafters.redisconfig.RedisConfigPlugin;
import pl.xcrafters.redisconfig.config.ConfigManager;
import pl.xcrafters.redisconfig.listeners.RedisListener;
import pl.xcrafters.redisconfig.redis.RedisManager;

public class SpigotRedisConfigPlugin extends JavaPlugin implements RedisConfigPlugin {

    public ConfigManager configManager;
    public RedisManager redisManager;

    RedisListener redisListener;

    ConfigCommand configCommand;

    public RedisConfigAPI api;

    public void onEnable() {
        this.configManager = new SpigotConfigManager(this);
        this.redisManager = new RedisManager(this);

        this.redisListener = new RedisListener(this);

        this.configCommand = new ConfigCommand(this);

        this.api = new RedisConfigAPI(this);

        registerTypeAdapters();
    }

    public void onDisable() {
        RedisManager.closeConnection();
    }

    public ConfigManager getConfigManager() { return this.configManager; }

    public RedisManager getRedisManager() { return this.redisManager; }

    public RedisConfigAPI getAPI() { return this.api; }

    public void runTaskAsynchronously(Runnable runnable) { Bukkit.getScheduler().runTaskAsynchronously(this, runnable); }

    public void callConfigRefreshEvent(String pluginName) {
        JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin(pluginName);

        ConfigRefreshEvent event = new ConfigRefreshEvent(plugin);
        Bukkit.getPluginManager().callEvent(event);
    }

    public SpigotRedisConfigManager createRedisConfigManager(String pluginName) {
        return new SpigotRedisConfigManager(pluginName);
    }

    public static void registerTypeAdapters() {
        RedisConfigAPI.getBuilder()
                .registerTypeAdapter(SpigotConfigColor.class, new SpigotConfigColorHandler())
                .registerTypeAdapter(SpigotConfigMaterial.class, new SpigotConfigMaterialHandler())
                .registerTypeAdapter(SpigotConfigItemStack.class, new SpigotConfigItemStackHandler());
    }

}
