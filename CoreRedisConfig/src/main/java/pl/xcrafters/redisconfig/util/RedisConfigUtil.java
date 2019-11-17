package pl.xcrafters.redisconfig.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import pl.xcrafters.redisconfig.api.ConfigField;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RedisConfigUtil {

    public static boolean exportPlugin(String pluginName, HashMap<String, ConfigField> fields, String exportPathURI, boolean yaml) {
        if(yaml) {

        } else {
            JsonObject object = new JsonObject();

            for(Map.Entry<String, ConfigField> entry : fields.entrySet()) {
                object.add(entry.getValue().getName(), RedisConfigAPI.getGson().toJsonTree(entry.getValue().toJSON()));
            }

            Path exportPath = Paths.get(exportPathURI);
            Path path = exportPath.resolve(String.format("%s.config.json", pluginName));

            try(PrintWriter writer = new PrintWriter(path.toFile())) {
                writer.write(RedisConfigAPI.getGson().toJson(object));
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public static HashMap<String, String> importPlugin(String pluginName, String exportPathURI, boolean yaml) {
        if(yaml) {

        } else {
            try {
                Path exportPath = Paths.get(exportPathURI);
                Path path = exportPath.resolve(String.format("%s.config.json", pluginName));

                String json = new String(Files.readAllBytes(path));
                JsonObject object = new Gson().fromJson(json, JsonObject.class);

                HashMap<String, String> fields = new HashMap<>();

                for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    if(entry.getValue() instanceof JsonPrimitive) {
                        JsonPrimitive primitive = (JsonPrimitive) entry.getValue();
                        fields.put(entry.getKey(), primitive.getAsString());
                    } else {
                        fields.put(entry.getKey(), RedisConfigAPI.getGson().toJson(entry.getValue()));
                    }
                }

                return fields;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
