package pl.xcrafters.bungeeredisconfig.api;

import com.google.gson.reflect.TypeToken;
import pl.xcrafters.bungeeredisconfig.api.values.BungeeConfigColor;
import pl.xcrafters.redisconfig.api.ConfigField;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.api.values.ConfigList;

public class BungeeRedisConfigManager extends RedisConfigManager {

    public BungeeRedisConfigManager(String pluginName) {
        super(pluginName);
    }

    @Override
    public BungeeConfigColor getColor(String name) {
        ConfigField field = super.getConfigFields().get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.COLOR) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.COLOR.name() + ".");
        }

        return new BungeeConfigColor(field.getValue());
    }

    public ConfigList<BungeeConfigColor> getBungeColorList(String name) {
        return super.getList(name, new TypeToken<ConfigList<BungeeConfigColor>>() {}.getType());
    }

}
