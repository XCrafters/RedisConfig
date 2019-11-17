package pl.xcrafters.redisconfig.api.values;

import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.api.RedisConfigManagerTest;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigListTest {

    @Test
    public void testList() {
        RedisConfigManager configManager = RedisConfigManagerTest.getConfigManager();
        
        configManager.registerField("booleans", FieldType.LIST, null, "test", false);
        configManager.registerField("strings", FieldType.LIST, null, "test", false);
        configManager.registerField("integers", FieldType.LIST, null, "test", false);
        configManager.registerField("doubles", FieldType.LIST, null, "test", false);
        configManager.registerField("longs", FieldType.LIST, null, "test", false);
        configManager.registerField("colors", FieldType.LIST, null, "test", false);
        configManager.registerField("ranges", FieldType.LIST, null, "test", false);
        configManager.registerField("custom", FieldType.LIST, null, "test", false);

        ConfigList<Boolean> booleans = new ConfigList<Boolean>(Arrays.asList(true, false, true));
        configManager.set("booleans", booleans);
        ConfigList<Boolean> booleansDeserialized = configManager.getBooleanList("booleans");
        assertEquals(booleans, booleansDeserialized);

        ConfigList<String> strings = new ConfigList<String>(Arrays.asList("1", "2", "3"));
        configManager.set("strings", strings);
        ConfigList<String> stringsDeserialized = configManager.getStringList("strings");
        assertEquals(strings, stringsDeserialized);

        ConfigList<Integer> integers = new ConfigList<Integer>(Arrays.asList(1, 2, 3));
        configManager.set("integers", integers);
        ConfigList<Integer> integersDeserialized = configManager.getIntegerList("integers");
        assertTrue(1 == integersDeserialized.getList().get(0));
        assertEquals(integers, integersDeserialized);

        ConfigList<Double> doubles = new ConfigList<Double>(Arrays.asList(1.1D, 2.2D, 3.3D));
        configManager.set("doubles", doubles);
        ConfigList<Double> doublesDeserialized = configManager.getDoubleList("doubles");
        assertEquals(doubles, doublesDeserialized);

        ConfigList<Long> longs = new ConfigList<Long>(Arrays.asList(1L, 2L, 3L));
        configManager.set("longs", longs);
        ConfigList<Long> longsDeserialized = configManager.getLongList("longs");
        assertEquals(longs, longsDeserialized);

        ConfigList<ConfigColor> colors = new ConfigList<>(Arrays.asList(new ConfigColor("RED"), new ConfigColor("BLUE")));
        configManager.set("colors", colors);
        ConfigList<ConfigColor> colorsDeserialized = configManager.getColorList("colors");
        assertEquals(colors, colorsDeserialized);
        assertEquals(colors.getList().get(0), colorsDeserialized.getList().get(0));

        ConfigList<ConfigRange<Integer>> ranges = new ConfigList<>(Arrays.asList(new ConfigRange<>(1, 2), new ConfigRange<>(2, 3)));
        configManager.set("ranges", ranges);
        ConfigList<ConfigRange<Integer>> rangesDeserialized = configManager.getList("ranges", new TypeToken<ConfigList<ConfigRange<Integer>>>() {}.getType());
        assertEquals(ranges, rangesDeserialized);

        ConfigList<ConfigCustomClassTest.TestClass> custom = new ConfigList<>(Collections.singletonList(new ConfigCustomClassTest.TestClass(1, "2", 3.0)));
        configManager.set("custom", custom);
        ConfigList<ConfigCustomClassTest.TestClass> customDeserialized = configManager.getList("custom", new TypeToken<ConfigList<ConfigCustomClassTest.TestClass>>() {}.getType());
        assertEquals(custom, customDeserialized);
    }

}