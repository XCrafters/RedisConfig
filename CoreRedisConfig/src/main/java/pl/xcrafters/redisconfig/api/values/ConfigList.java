package pl.xcrafters.redisconfig.api.values;

import pl.xcrafters.redisconfig.api.RedisConfigAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigList<T> implements Iterable<T> {

    private List<T> list;

    public ConfigList() { this.list = new ArrayList<T>(); }

    public ConfigList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() { return list; }

    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    public void add(T value) {
        this.list.add(value);
    }

    public boolean remove(T value) {
        return this.list.remove(value);
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

        ConfigList list = (ConfigList) o;
        return this.toString().equals(list.toString());
    }

}
