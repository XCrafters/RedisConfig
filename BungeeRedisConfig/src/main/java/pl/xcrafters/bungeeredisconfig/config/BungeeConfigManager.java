package pl.xcrafters.bungeeredisconfig.config;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.xcrafters.bungeeredisconfig.BungeeRedisConfigPlugin;
import pl.xcrafters.redisconfig.config.ConfigManager;

import java.io.*;

public class BungeeConfigManager implements ConfigManager {

    private BungeeRedisConfigPlugin plugin;

    public BungeeConfigManager(BungeeRedisConfigPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    private String redisHost, redisPass;
    private int redisPort, redisBase;
    private String exportPath;

    public void load() {
        Configuration config = null;

        saveDefaultConfig();
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(config == null) {
            return;
        }

        redisHost = config.getString("config.redis.host");
        redisPort = config.getInt("config.redis.port");
        redisPass = config.getString("config.redis.pass");
        redisBase = config.getInt("config.redis.base");
        exportPath = config.getString("config.export-path");
    }

    private void saveDefaultConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File configFile = new File(plugin.getDataFolder(), "config.yml");

        if(configFile.exists()) {
            return;
        }

        try {
            configFile.createNewFile();
            InputStream is = plugin.getResourceAsStream("config.yml");
            OutputStream os = new FileOutputStream(configFile);
            ByteStreams.copy(is, os);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getRedisHost() { return this.redisHost; }
    public int getRedisPort() { return this.redisPort; }
    public String getRedisPass() { return this.redisPass; }
    public int getRedisBase() { return this.redisBase; }
    public String getExportPath() { return this.exportPath; }

}
