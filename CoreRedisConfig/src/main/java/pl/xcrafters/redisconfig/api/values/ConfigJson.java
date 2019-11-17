package pl.xcrafters.redisconfig.api.values;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConfigJson {

    private JsonElement json;

    public ConfigJson() {
        this.json = new JsonObject();
    }

    public ConfigJson(JsonElement json) {
        this.json = json;
    }

    public ConfigJson(String json) {
        Gson gson = new Gson();

        this.json = gson.fromJson(json, JsonElement.class);
    }

    public JsonElement getJson() { return this.json; }

    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this.json);
    }

}
