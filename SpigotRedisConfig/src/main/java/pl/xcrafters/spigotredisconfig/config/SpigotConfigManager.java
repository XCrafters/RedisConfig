package pl.xcrafters.spigotredisconfig.config;

import pl.xcrafters.spigotredisconfig.SpigotRedisConfigPlugin;
import pl.xcrafters.redisconfig.config.ConfigManager;

public class SpigotConfigManager implements ConfigManager {

    private SpigotRedisConfigPlugin plugin;

    public SpigotConfigManager(SpigotRedisConfigPlugin plugin) {
        this.plugin = plugin;

        load();
    }

    private String redisHost, redisPass;
    private int redisPort, redisBase;
    private String exportPath;

    public void load() {
        plugin.saveDefaultConfig();

        redisHost = plugin.getConfig().getString("config.redis.host");
        redisPort = plugin.getConfig().getInt("config.redis.port");
        redisPass = plugin.getConfig().getString("config.redis.pass");
        redisBase = plugin.getConfig().getInt("config.redis.base");
        exportPath = plugin.getConfig().getString("config.export-path");
    }

    public String getRedisHost() { return this.redisHost; }
    public int getRedisPort() { return this.redisPort; }
    public String getRedisPass() { return this.redisPass; }
    public int getRedisBase() { return this.redisBase; }
    public String getExportPath() { return this.exportPath; }

}
