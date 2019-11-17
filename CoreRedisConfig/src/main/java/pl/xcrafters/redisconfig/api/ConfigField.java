package pl.xcrafters.redisconfig.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigField<T> {

    private String name, description;
    private FieldType type;
    private String value;
    private T defaultValue;
    private boolean secret;

    ConfigField(String name, FieldType type, T defaultValue, String description, boolean secret) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.description = description;
        this.secret = secret;

        if(defaultValue == null) {
            return;
        }

        this.setValue(defaultValue);
    }

    void setValue(T value) {
        if(value == null) {
            if(this.defaultValue instanceof Boolean || this.defaultValue instanceof String || this.defaultValue instanceof Number) {
                this.value = String.valueOf(this.defaultValue);
                return;
            }

            this.value = RedisConfigAPI.getGson().toJson(this.defaultValue);
            return;
        }

        if(value instanceof Boolean || value instanceof String || value instanceof Number) {
            this.value = String.valueOf(value);
            return;
        }

        this.value = RedisConfigAPI.getGson().toJson(value);
    }

    void loadValue(String value) {
        if(value == null) {
            if(this.defaultValue instanceof Boolean || this.defaultValue instanceof String || this.defaultValue instanceof Number) {
                this.value = String.valueOf(this.defaultValue);
                return;
            }

            this.value = RedisConfigAPI.getGson().toJson(this.defaultValue);
            return;
        }

        this.value = value;
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public FieldType getType() { return type; }
    public String getValue() { return this.value; }
    public T getDefaultValue() { return this.defaultValue; }
    public boolean getSecret() { return this.secret; }
    public JsonElement toJSON() {
        switch(this.type) {
            case BOOLEAN:
                return new JsonPrimitive(Boolean.parseBoolean(this.value));
            case STRING:
            case COLOR:
            case MATERIAL:
                return new JsonPrimitive(this.value);
            case INTEGER:
                return new JsonPrimitive(Integer.parseInt(this.value));
            case DOUBLE:
                return new JsonPrimitive(Double.parseDouble(this.value));
            case FLOAT:
                return new JsonPrimitive(Float.parseFloat(this.value));
            case LONG:
                return new JsonPrimitive(Long.parseLong(this.value));
            default:
                return RedisConfigAPI.getGson().fromJson(this.value, JsonElement.class);
        }
    }

}
