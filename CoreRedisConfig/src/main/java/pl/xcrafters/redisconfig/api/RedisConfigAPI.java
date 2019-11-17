package pl.xcrafters.redisconfig.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.xcrafters.redisconfig.RedisConfigPlugin;
import pl.xcrafters.redisconfig.api.values.*;
import pl.xcrafters.redisconfig.api.values.gson.*;

import java.util.HashMap;

public class RedisConfigAPI {

    static RedisConfigPlugin plugin;

    protected static HashMap<String, RedisConfigManager> registeredConfigs = new HashMap<String, RedisConfigManager>();

    public RedisConfigAPI(RedisConfigPlugin redisConfigPlugin) {
        plugin = redisConfigPlugin;
    }

    public static HashMap<String, RedisConfigManager> getRegisteredConfigs() {
        return (HashMap<String, RedisConfigManager>) registeredConfigs.clone();
    }

    public void updateConfig(String pluginName) {
        if(pluginName == null) {
            return;
        }

        RedisConfigManager configManager = registeredConfigs.get(pluginName.toLowerCase());

        if(configManager == null) {
            return;
        }

        plugin.callConfigRefreshEvent(pluginName);

        configManager.load(false);
    }

    static GsonBuilder builder;

    public static GsonBuilder getBuilder() {
        if(builder == null) {
            builder = new GsonBuilder()
                    .registerTypeAdapter(ConfigColor.class, new ConfigColorHandler())
                    .registerTypeAdapter(ConfigMaterial.class, new ConfigMaterialHandler())
                    .registerTypeAdapter(ConfigItemStack.class, new ConfigItemStackHandler())
                    .registerTypeAdapter(ConfigJson.class, new ConfigJsonHandler())
                    .registerTypeAdapter(ConfigRange.class, new ConfigRangeHandler<>())
                    .registerTypeAdapter(ConfigList.class, new ConfigListHandler<>())
                    .registerTypeAdapter(ConfigMap.class, new ConfigMapHandler<>());
        }

        return builder;
    }

    public static Gson getGson() {
        return getBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

}
