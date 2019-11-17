package pl.xcrafters.redisconfig.api.values;

import com.google.common.collect.ImmutableMap;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;

import java.util.HashMap;
import java.util.Map;

public class ConfigMap<K, V> {

    private Map<K, V> map;

    public ConfigMap() { this.map = new HashMap<K, V>(); }

    public ConfigMap(ImmutableMap<K, V> map) {
        this.map = map;
    }

    public ConfigMap(Map<K, V> map) {
        this.map = map;
    }

    public Map<K, V> getMap() {
        return this.map;
    }

    public void put(K key, V value) {
        this.map.put(key, value);
    }

    public V remove(K key) {
        return this.map.remove(key);
    }

    @Override
    public String toString() {
        return RedisConfigAPI.getGson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != getClass()) {
            return false;
        }

        ConfigMap list = (ConfigMap) o;
        return this.toString().equals(list.toString());
    }

}
