package pl.xcrafters.bungeeredisconfig.api;

import pl.xcrafters.redisconfig.RedisConfigPlugin;

public class RedisConfigAPI extends pl.xcrafters.redisconfig.api.RedisConfigAPI {

    public RedisConfigAPI(RedisConfigPlugin plugin) {
        super(plugin);
    }

    public static BungeeRedisConfigManager registerPlugin(String pluginName) {
        BungeeRedisConfigManager config = new BungeeRedisConfigManager(pluginName);

        pl.xcrafters.redisconfig.api.RedisConfigAPI.registeredConfigs.put(pluginName.toLowerCase(), config);

        return config;
    }

}
