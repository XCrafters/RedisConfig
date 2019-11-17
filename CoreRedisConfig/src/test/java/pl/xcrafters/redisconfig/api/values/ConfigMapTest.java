package pl.xcrafters.redisconfig.api.values;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.api.RedisConfigManagerTest;

public class ConfigMapTest {

    @Test
    public void test() {
        RedisConfigManager configManager = RedisConfigManagerTest.getConfigManager();

        configManager.registerField("colorByStrings", FieldType.MAP, null, "test", false);

        ConfigMap<String, ConfigColor> colorByStrings = new ConfigMap<>(ImmutableMap.of("red", new ConfigColor("RED")));
        configManager.set("colorByStrings", colorByStrings);
        ConfigMap<String, ConfigColor> colorsByStringsDeserialized = configManager.getMap("colorByStrings", new TypeToken<ConfigMap<String, ConfigColor>>() {}.getType());
        Assert.assertEquals(colorByStrings, colorsByStringsDeserialized);
        Assert.assertEquals(colorsByStringsDeserialized.getMap().get("red"), new ConfigColor("RED"));
    }

}
