package pl.xcrafters.redisconfig.api.values;

import com.google.gson.JsonObject;
import lombok.Getter;

public class ConfigItemStack {

    @Getter private ConfigMaterial type;
    @Getter private int amount;
    @Getter private byte data;

    public ConfigItemStack(ConfigMaterial type, int amount) {
        this(type, amount, (byte) 0);
    }

    public ConfigItemStack(ConfigMaterial type, int amount, byte data) {
        this.type = type;
        this.amount = amount;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != getClass()) {
            return false;
        }

        ConfigItemStack itemStack = (ConfigItemStack) o;
        return this.type.getName().equals(itemStack.getType().getName()) && this.amount == itemStack.getAmount() && this.data == itemStack.getData();
    }

}
