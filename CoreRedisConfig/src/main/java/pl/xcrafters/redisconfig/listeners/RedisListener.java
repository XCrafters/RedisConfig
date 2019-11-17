package pl.xcrafters.redisconfig.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import redis.clients.jedis.JedisPubSub;
import pl.xcrafters.redisconfig.RedisConfigPlugin;
import pl.xcrafters.redisconfig.redis.RedisManager;

public class RedisListener extends JedisPubSub {

    RedisConfigPlugin plugin;

    public RedisListener(RedisConfigPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getRedisManager().subscribe(this, "UpdateConfig");
    }

    Gson gson = new Gson();

    public void onMessage(String channel, String json) {
        JsonObject object = gson.fromJson(json, JsonObject.class);

        if(object.get("instance").getAsString().equals(RedisManager.instance)) {
            return;
        }

        if(channel.equals("UpdateConfig")) {
            String pluginName = object.get("plugin").getAsString();
            this.plugin.getAPI().updateConfig(pluginName);
        }
    }

}
