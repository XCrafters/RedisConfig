package pl.xcrafters.redisconfig.api.values;

import org.junit.Assert;
import org.junit.Test;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.api.RedisConfigManagerTest;

public class ConfigItemStackTest {

    @Test
    public void test() {
        RedisConfigManager configManager = RedisConfigManagerTest.getConfigManager();

        ConfigItemStack itemStack = new ConfigItemStack(new ConfigMaterial("STONE"), 1);
        configManager.registerField("testItemStack", FieldType.ITEM_STACK, itemStack, "test", false);
        Assert.assertEquals(itemStack, configManager.getItemStack("testItemStack"));
        configManager.set("testItemStack", itemStack);
        ConfigItemStack returned = configManager.getItemStack("testItemStack");
        Assert.assertEquals(itemStack, returned);
    }

}
