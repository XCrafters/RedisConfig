package pl.xcrafters.bungeeredisconfig;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import pl.xcrafters.bungeeredisconfig.api.BungeeRedisConfigManager;
import pl.xcrafters.bungeeredisconfig.api.RedisConfigAPI;
import pl.xcrafters.bungeeredisconfig.api.values.BungeeConfigColor;
import pl.xcrafters.bungeeredisconfig.api.values.gson.BungeeConfigColorHandler;
import pl.xcrafters.bungeeredisconfig.commands.BungeeConfigCommand;
import pl.xcrafters.bungeeredisconfig.config.BungeeConfigManager;
import pl.xcrafters.bungeeredisconfig.events.ConfigRefreshEvent;
import pl.xcrafters.redisconfig.RedisConfigPlugin;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.config.ConfigManager;
import pl.xcrafters.redisconfig.listeners.RedisListener;
import pl.xcrafters.redisconfig.redis.RedisManager;

public class BungeeRedisConfigPlugin extends Plugin implements RedisConfigPlugin {

    private ConfigManager configManager;
    private RedisManager redisManager;

    RedisListener redisListener;

    BungeeConfigCommand bungeeConfigCommand;

    private RedisConfigAPI api;

    public void onEnable() {
        this.configManager = new BungeeConfigManager(this);
        this.redisManager = new RedisManager(this);

        this.redisListener = new RedisListener(this);

        this.bungeeConfigCommand = new BungeeConfigCommand(this);

        this.api = new RedisConfigAPI(this);

        registerTypeAdapters();
    }

    public void onDisable() {

    }

    public ConfigManager getConfigManager() { return this.configManager; }

    public RedisManager getRedisManager() { return this.redisManager; }

    public RedisConfigAPI getAPI() { return this.api; }

    public void runTaskAsynchronously(Runnable runnable) { ProxyServer.getInstance().getScheduler().runAsync(this, runnable); }

    public void callConfigRefreshEvent(String pluginName) {
        ConfigRefreshEvent event = new ConfigRefreshEvent(pluginName);
        ProxyServer.getInstance().getPluginManager().callEvent(event);
    }

    public RedisConfigManager createRedisConfigManager(String pluginName) { return new BungeeRedisConfigManager(pluginName); }

    public static void registerTypeAdapters() {
        RedisConfigAPI.getBuilder()
                .registerTypeAdapter(BungeeConfigColor.class, new BungeeConfigColorHandler());
    }
}
