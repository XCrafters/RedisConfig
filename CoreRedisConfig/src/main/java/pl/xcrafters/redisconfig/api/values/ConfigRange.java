package pl.xcrafters.redisconfig.api.values;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.util.RedisConfigUtil;

public class ConfigRange<T> {

    private T min, max;

    public ConfigRange(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() { return this.min; }
    public T getMax() { return this.max; }
    public boolean checkInRange(T value) {
        FieldType rangeType = FieldType.getFieldType(this.min);
        FieldType type = FieldType.getFieldType(value);
        if(type != rangeType) {
            throw new RuntimeException("Nie mozna sprawdzić czy wartość o typie " + type.name() + " mieści się w zakresie o typie " + rangeType.name());
        }

        switch(rangeType) {
            case INTEGER:
                return (Integer) value >= (Integer) this.min && (Integer) value <= (Integer) this.max;
            case DOUBLE:
                return (Double) value >= (Double) this.min && (Double) value <= (Double) this.max;
            case FLOAT:
                return (Float) value >= (Float) this.min && (Float) value <= (Float) this.max;
            case LONG:
                return (Long) value >= (Long) this.min && (Long) value <= (Long) this.max;
            default:
                return false;
        }
    }
    public boolean checkHoursInRange(T value) {
        FieldType rangeType = FieldType.getFieldType(this.min);
        FieldType type = FieldType.getFieldType(value);
        if(type != rangeType) {
            throw new RuntimeException("Nie mozna sprawdzić czy wartość o typie " + type.name() + " mieści się w zakresie o typie " + rangeType.name());
        }

        switch(rangeType) {
            case INTEGER:
                return (Integer) this.max < (Integer) this.min ? ((Integer) value >= (Integer) this.min || (Integer) value <= (Integer) this.max) : (Integer) value >= (Integer) this.min && (Integer) value <= (Integer) this.max;
            case DOUBLE:
                return (Double) this.max < (Double) this.min ? ((Double) value >= (Double) this.min || (Double) value <= (Double) this.max) : (Double) value >= (Double) this.min && (Double) value <= (Double) this.max;
            case FLOAT:
                return (Float) this.max < (Float) this.min ? ((Float) value >= (Float) this.min || (Float) value <= (Float) this.max) : (Float) value >= (Float) this.min && (Float) value <= (Float) this.max;
            case LONG:
                return (Long) this.max < (Long) this.min ? ((Long) value >= (Long) this.min || (Long) value <= (Long) this.max) : (Long) value >= (Long) this.min && (Long) value <= (Long) this.max;
            default:
                return false;
        }
    }

    public String toString() {
        return RedisConfigAPI.getGson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != getClass()) {
            return false;
        }

        ConfigRange range = (ConfigRange) o;
        return this.toString().equals(range.toString());
    }

}
