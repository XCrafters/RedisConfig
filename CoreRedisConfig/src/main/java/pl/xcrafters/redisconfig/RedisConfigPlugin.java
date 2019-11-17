package pl.xcrafters.redisconfig;

import com.google.gson.GsonBuilder;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.config.ConfigManager;
import pl.xcrafters.redisconfig.redis.RedisManager;

public interface RedisConfigPlugin {

    ConfigManager getConfigManager();
    RedisManager getRedisManager();
    RedisConfigAPI getAPI();
    void runTaskAsynchronously(Runnable runnable);
    void callConfigRefreshEvent(String pluginName);
    RedisConfigManager createRedisConfigManager(String pluginName);

}
