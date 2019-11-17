package pl.xcrafters.spigotredisconfig.api;

import org.bukkit.plugin.java.JavaPlugin;
import pl.xcrafters.redisconfig.RedisConfigPlugin;

public class RedisConfigAPI extends pl.xcrafters.redisconfig.api.RedisConfigAPI {

    public RedisConfigAPI(RedisConfigPlugin plugin) {
        super(plugin);
    }

    public static SpigotRedisConfigManager registerPlugin(JavaPlugin plugin) {
        SpigotRedisConfigManager config = new SpigotRedisConfigManager(plugin.getName());

        pl.xcrafters.redisconfig.api.RedisConfigAPI.registeredConfigs.put(plugin.getName().toLowerCase(), config);

        return config;
    }

}
