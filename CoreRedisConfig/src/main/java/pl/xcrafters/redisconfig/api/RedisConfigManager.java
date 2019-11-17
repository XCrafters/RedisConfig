package pl.xcrafters.redisconfig.api;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import pl.xcrafters.redisconfig.api.values.*;
import pl.xcrafters.redisconfig.redis.RedisManager;
import pl.xcrafters.redisconfig.util.RedisConfigUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class RedisConfigManager {

    private String pluginName;
    private HashMap<String, ConfigField> fields = new HashMap<>();

    protected RedisConfigManager(String pluginName) {
        this.pluginName = pluginName;
    }

    public void registerField(String name, FieldType type, Object defaultValue, String description, boolean secret) {
        FieldType defaultValueType = FieldType.getFieldType(defaultValue);

        if(defaultValue != null && type != FieldType.CUSTOM_CLASS && type != defaultValueType) {
            throw new RuntimeException("Brak zgodności w typie przy rejestracji pola w konfiguracji.");
        }

        ConfigField field = new ConfigField(name, type, defaultValue, description, secret);
        fields.put(name.toLowerCase(), field);
    }

    public void registerCustomClass(Type type, Object adapter) {
        if(!(adapter instanceof JsonSerializer) || !(adapter instanceof JsonDeserializer)) {
            throw new RuntimeException("Wprowadzono nieprawidłowy adapter.");
        }

        RedisConfigAPI.getBuilder().registerTypeAdapter(type, adapter);
    }

    public void load() {
        load(true);
    }

    void load(boolean save) {
        RedisManager.updateFields(this.pluginName, fields);

        Map<String, String> config = RedisManager.loadPlugin(this.pluginName);
        if(config == null) {
            return;
        }

        for(Map.Entry<String, ConfigField> entry : fields.entrySet()) {
            entry.getValue().loadValue(config.get(entry.getValue().getName()));
        }

        if(save) {
            this.save();
        }
    }

    public void save() {
        RedisManager.savePlugin(pluginName, fields);
    }

    public boolean exportToFile(String exportPath) {
        return RedisConfigUtil.exportPlugin(pluginName, fields, exportPath, false);
    }

    public boolean importFromFile(String exportPath) {
        HashMap<String, String> fields = RedisConfigUtil.importPlugin(pluginName, exportPath, false);
        if(fields == null) {
            return false;
        }

        for(Map.Entry<String, String> entry : fields.entrySet()) {
            ConfigField field = this.fields.get(entry.getKey().toLowerCase());
            if(field == null) {
                continue;
            }

            field.loadValue(entry.getValue());
        }

        this.save();

        return true;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public HashMap<String, ConfigField> getConfigFields() {
        return (HashMap<String, ConfigField>) this.fields.clone();
    }

    public boolean getBoolean(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.BOOLEAN) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.BOOLEAN.name() + ".");
        }

        return Boolean.valueOf(field.getValue());
    }

    public String getString(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.STRING) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.STRING.name() + ".");
        }

        return field.getValue();
    }

    public int getInteger(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.INTEGER) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.INTEGER.name() + ".");
        }

        return Integer.valueOf(field.getValue());
    }

    public double getDouble(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.DOUBLE) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.DOUBLE.name() + ".");
        }

        return Double.valueOf(field.getValue());
    }

    public long getLong(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.LONG) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.LONG.name() + ".");
        }

        return Long.valueOf(field.getValue());
    }

    public ConfigColor getColor(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.COLOR) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.COLOR.name() + ".");
        }

        return new ConfigColor(field.getValue());
    }

    public ConfigMaterial getMaterial(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.MATERIAL) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.MATERIAL.name() + ".");
        }

        return new ConfigMaterial(field.getValue());
    }

    public ConfigItemStack getItemStack(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.ITEM_STACK) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.MATERIAL.name() + ".");
        }

        return RedisConfigAPI.getGson().fromJson(field.getValue(), ConfigItemStack.class);
    }

    public String getJSON(String name) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.JSON) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.JSON.name() + ".");
        }

        return field.getValue();
    }

    public ConfigRange<Integer> getIntegerRange(String name) {
        return getRange(name, new TypeToken<ConfigRange<Integer>>() {}.getType());
    }

    public ConfigRange<Double> getDoubleRange(String name) {
        return getRange(name, new TypeToken<ConfigRange<Double>>() {}.getType());
    }

    public ConfigRange<Float> getFloatRange(String name) {
        return getRange(name, new TypeToken<ConfigRange<Float>>() {}.getType());
    }

    public ConfigRange<Long> getLongRange(String name) {
        return getRange(name, new TypeToken<ConfigRange<Long>>() {}.getType());
    }

    public <T> ConfigRange<T> getRange(String name, Type type) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.RANGE) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.RANGE.name() + ".");
        }

        return RedisConfigAPI.getGson().fromJson(field.getValue(), type);
    }

    public ConfigList<Boolean> getBooleanList(String name) {
        return getList(name, new TypeToken<ConfigList<Boolean>>() {}.getType());
    }

    public ConfigList<String> getStringList(String name) {
        return getList(name, new TypeToken<ConfigList<String>>() {}.getType());
    }

    public ConfigList<Integer> getIntegerList(String name) {
        return getList(name, new TypeToken<ConfigList<Integer>>() {}.getType());
    }

    public ConfigList<Double> getDoubleList(String name) {
        return getList(name, new TypeToken<ConfigList<Double>>() {}.getType());
    }

    public ConfigList<Double> getFloatList(String name) {
        return getList(name, new TypeToken<ConfigList<Float>>() {}.getType());
    }

    public ConfigList<Long> getLongList(String name) {
        return getList(name, new TypeToken<ConfigList<Long>>() {}.getType());
    }

    public ConfigList<ConfigColor> getColorList(String name) {
        return getList(name, new TypeToken<ConfigList<ConfigColor>>() {}.getType());
    }

    public ConfigList<ConfigMaterial> getMaterialList(String name) {
        return getList(name, new TypeToken<ConfigList<ConfigMaterial>>() {}.getType());
    }

    public ConfigList<ConfigItemStack> getItemStackList(String name) {
        return getList(name, new TypeToken<ConfigList<ConfigItemStack>>() {}.getType());
    }

    public ConfigList<ConfigJson> getJsonList(String name) {
        return getList(name, new TypeToken<ConfigList<ConfigJson>>() {}.getType());
    }

    public <T> ConfigList<T> getList(String name, Type type) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.LIST) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.LIST.name() + ".");
        }

        return RedisConfigAPI.getGson().fromJson(field.getValue(), type);
    }

    public ConfigMap<String, String> getStringMap(String name) {
        return getMap(name, new TypeToken<ConfigMap<String, String>>() {}.getType());
    }

    public <K, V> ConfigMap<K, V> getMap(String name, Type type) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.MAP) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.MAP.name() + ".");
        }

        return RedisConfigAPI.getGson().fromJson(field.getValue(), type);
    }

    public <T> T getCustomClass(String name, Class<T> customClass) {
        ConfigField field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        if(field.getType() != FieldType.CUSTOM_CLASS) {
            throw new RuntimeException("Nie mozna przekonwertowac wartosci " + field.getType().name() + " na " + FieldType.MAP.name() + ".");
        }

        return RedisConfigAPI.getGson().fromJson(field.getValue(), customClass);
    }

    public <T> void set(String name, T value) {
        ConfigField<T> field = fields.get(name.toLowerCase());

        if(field == null) {
            throw new RuntimeException("Nie znaleziono pola " + name + ".");
        }

        FieldType type = FieldType.getFieldType(value);

        if(type != field.getType()) {
            throw new RuntimeException("Nie mozna ustawic wartosci o typie " + type.name() + " dla pola o typie " + field.getType().name() + ".");
        }

        field.setValue(value);
        this.save();
    }

}
