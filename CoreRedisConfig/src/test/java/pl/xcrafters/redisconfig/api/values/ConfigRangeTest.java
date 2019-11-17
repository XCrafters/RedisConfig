package pl.xcrafters.redisconfig.api.values;

import org.junit.Test;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.api.RedisConfigManagerTest;

import static org.junit.Assert.*;

public class ConfigRangeTest {

    @Test
    public void testRange() {
        RedisConfigManager configManager = RedisConfigManagerTest.getConfigManager();
        
        configManager.registerField("range", FieldType.RANGE, null, "test", false);

        ConfigRange<Integer> integerRange = new ConfigRange<Integer>(1, 3);
        configManager.set("range", integerRange);
        ConfigRange<Integer> integerRangeDeserialized = configManager.getIntegerRange("range");
        assertTrue(integerRange.checkInRange(2));
        assertEquals(integerRange, integerRangeDeserialized);

        ConfigRange<Double> doubleRange = new ConfigRange<Double>(1.1D, 3.3D);
        configManager.set("range", doubleRange);
        ConfigRange<Double> doubleRangeDeserialized = configManager.getDoubleRange("range");
        assertTrue(doubleRange.checkInRange(2.2D));
        assertEquals(doubleRange, doubleRangeDeserialized);

        ConfigRange<Float> floatRange = new ConfigRange<Float>(1.1F, 3.3F);
        configManager.set("range", floatRange);
        ConfigRange<Float> floatRangeDeserialized = configManager.getFloatRange("range");
        assertTrue(floatRange.checkInRange(2.2F));
        assertEquals(floatRange, floatRangeDeserialized);

        ConfigRange<Long> longRange = new ConfigRange<Long>(1L, 3L);
        configManager.set("range", longRange);
        ConfigRange<Long> longRangeDeserialized = configManager.getLongRange("range");
        assertTrue(longRange.checkInRange(2L));
        assertEquals(longRange, longRangeDeserialized);
    }

}