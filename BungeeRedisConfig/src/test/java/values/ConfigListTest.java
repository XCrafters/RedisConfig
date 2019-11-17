package values;

import net.md_5.bungee.api.ChatColor;
import org.junit.Test;
import pl.xcrafters.bungeeredisconfig.BungeeRedisConfigPlugin;
import pl.xcrafters.bungeeredisconfig.api.BungeeRedisConfigManager;
import pl.xcrafters.bungeeredisconfig.api.values.BungeeConfigColor;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.values.ConfigList;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ConfigListTest {

    @Test
    public void testList() {
        BungeeRedisConfigPlugin.registerTypeAdapters();

        BungeeRedisConfigManager configManager = new BungeeRedisConfigManager("test");

        configManager.registerField("colors", FieldType.LIST, null, "test", false);

        ConfigList<BungeeConfigColor> colors = new ConfigList<BungeeConfigColor>(Arrays.asList(new BungeeConfigColor(ChatColor.AQUA), new BungeeConfigColor(ChatColor.BLACK), new BungeeConfigColor(ChatColor.BLUE)));
        configManager.set("colors", colors);
        ConfigList<BungeeConfigColor> colorsDeserialized = configManager.getBungeColorList("colors");
        assertEquals(colors, colorsDeserialized);
    }

}
