package pl.xcrafters.redisconfig.api.values;

import com.google.gson.*;
import org.junit.Test;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.RedisConfigManager;
import pl.xcrafters.redisconfig.api.RedisConfigManagerTest;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

public class ConfigCustomClassTest {

    @Test
    public void test() {
        RedisConfigManager configManager = RedisConfigManagerTest.getConfigManager();

        configManager.registerCustomClass(TestClass.class, new TestClassHandler());
        configManager.registerField("testClass", FieldType.CUSTOM_CLASS, null, "test", false);

        TestClass test = new TestClass(1, "2", 3.0);
        configManager.set("testClass", test);
        TestClass returned = configManager.getCustomClass("testClass", TestClass.class);
        assertEquals(test, returned);
    }

    public static class TestClass implements ConfigCustomClass {
        private int i;
        private String s;
        private double d;

        public TestClass(int i, String s, double d) {
            this.i = i;
            this.s = s;
            this.d = d;
        }

        public TestClass(JsonObject object) {
            this.i = object.get("i").getAsInt();
            this.s = object.get("s").getAsString();
            this.d = object.get("d").getAsDouble();
        }

        @Override
        public boolean equals(Object object) {
            if(!(object instanceof TestClass)) {
                return false;
            }

            TestClass test = (TestClass) object;

            return i == test.i && s.equals(test.s) && d == test.d;
        }
    }

    public static class TestClassHandler implements JsonSerializer<TestClass>, JsonDeserializer<TestClass> {

        @Override
        public TestClass deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();

            if(object.keySet().size() == 0) {
                return null;
            }

            int i = object.get("i").getAsInt();
            String s = object.get("s").getAsString();
            double d = object.get("d").getAsDouble();

            return new TestClass(i, s, d);
        }

        @Override
        public JsonElement serialize(TestClass test, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject object = new JsonObject();

            object.addProperty("i", test.i);
            object.addProperty("s", test.s);
            object.addProperty("d", test.d);

            return object;
        }
    }

}
