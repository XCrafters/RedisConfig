package pl.xcrafters.redisconfig.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import pl.xcrafters.redisconfig.RedisConfigPlugin;
import pl.xcrafters.redisconfig.api.ConfigField;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RedisManager {

    private RedisConfigPlugin plugin;

    static Gson gson = new Gson();
    private static JedisPool pool;
    private Jedis subscriber;
    public static String instance;

    public RedisManager(RedisConfigPlugin plugin) {
        this.plugin = plugin;

        pool = new JedisPool(new JedisPoolConfig(), plugin.getConfigManager().getRedisHost(), plugin.getConfigManager().getRedisPort(), 10000);
        subscriber = pool.getResource();

        instance = UUID.randomUUID().toString();
    }

    public void subscribe(final JedisPubSub pubSub, final String... channels) {
        plugin.runTaskAsynchronously(() -> subscriber.subscribe(pubSub, channels));
    }

    public static void updateFields(String pluginName, Map<String, ConfigField> fields) {
        try(Jedis jedis = pool.getResource()) {
            Map<String, String> data = new HashMap<String, String>();

            for(Map.Entry<String, ConfigField> entry : fields.entrySet()) {
                JsonObject object = new JsonObject();

                object.addProperty("defaultValue", entry.getValue().getDefaultValue() == null ? null : entry.getValue().getDefaultValue().toString());
                object.addProperty("description", entry.getValue().getDescription());
                object.addProperty("secret", entry.getValue().getSecret());

                data.put(entry.getValue().getName(), gson.toJson(object));
            }

            jedis.del("fields:" + pluginName);
            jedis.hmset("fields:" + pluginName, data);
        }
    }

    public static Map<String, String> loadPlugin(String pluginName) {
        Map<String, String> fields;

        try(Jedis jedis = pool.getResource()) {
            fields = jedis.hgetAll("config:" + pluginName);
        }

        return fields;
    }

    public static void savePlugin(String pluginName, HashMap<String, ConfigField> fields) {
        if(pool == null) {
            return;
        }

        try(Jedis jedis = pool.getResource()) {
            Map<String, String> data = new HashMap<>();

            for(Map.Entry<String, ConfigField> entry : fields.entrySet()) {
                data.put(entry.getValue().getName(), entry.getValue().getValue());
            }

            jedis.hmset("config:" + pluginName, data);

            jedis.sadd("plugins", pluginName);

            Map<String, String> plugin = new HashMap<>();
            plugin.put("name", pluginName);
            jedis.hmset(String.format("plugin:%s", pluginName), plugin);

            JsonObject object = new JsonObject();
            object.addProperty("instance", instance);
            object.addProperty("plugin", pluginName);
            jedis.publish("UpdateConfig", gson.toJson(object));
        }
    }

    public static void closeConnection(){
        pool.destroy();
    }

}
