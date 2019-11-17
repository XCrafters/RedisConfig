package pl.xcrafters.spigotredisconfig.api;

import com.google.gson.reflect.TypeToken;
import pl.xcrafters.redisconfig.api.ConfigField;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.api.values.ConfigList;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigColor;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigItemStack;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigMaterial;

public class SpigotRedisConfigManager extends RedisConfigManager {

    public SpigotRedisConfigManager(String pluginName) {
        super(pluginName);
    }

    @Override
    public SpigotConfigColor getColor(String name) {
        ConfigField field = super.getConfigFields().get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.COLOR) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.COLOR.name() + ".");
        }

        return new SpigotConfigColor(field.getValue());
    }

    @Override
    public SpigotConfigMaterial getMaterial(String name) {
        ConfigField field = super.getConfigFields().get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.MATERIAL) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.MATERIAL.name() + ".");
        }

        return new SpigotConfigMaterial(field.getValue());
    }

    @Override
    public SpigotConfigItemStack getItemStack(String name) {
        ConfigField field = super.getConfigFields().get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.ITEM_STACK) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.ITEM_STACK.name() + ".");
        }

        return RedisConfigAPI.getGson().fromJson(field.getValue(), SpigotConfigItemStack.class);
    }

    public ConfigList<SpigotConfigColor> getSpigotColorList(String name) {
        return super.getList(name, new TypeToken<ConfigList<SpigotConfigColor>>() {}.getType());
    }

    public ConfigList<SpigotConfigMaterial> getSpigotMaterialList(String name) {
        return super.getList(name, new TypeToken<ConfigList<SpigotConfigMaterial>>() {}.getType());
    }

    public ConfigList<SpigotConfigItemStack> getSpigotItemStackList(String name) {
        return super.getList(name, new TypeToken<ConfigList<SpigotConfigItemStack>>() {}.getType());
    }

}
