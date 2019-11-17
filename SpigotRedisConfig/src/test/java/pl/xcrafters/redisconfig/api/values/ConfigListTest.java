package pl.xcrafters.redisconfig.api.values;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.spigotredisconfig.SpigotRedisConfigPlugin;
import pl.xcrafters.spigotredisconfig.api.RedisConfigAPI;
import pl.xcrafters.spigotredisconfig.api.SpigotRedisConfigManager;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigColor;
import pl.xcrafters.spigotredisconfig.api.values.SpigotConfigMaterial;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConfigListTest {

    @Test
    public void testList() {
        SpigotRedisConfigPlugin.registerTypeAdapters();

        SpigotRedisConfigManager configManager = new SpigotRedisConfigManager("test");

        configManager.registerField("colors", FieldType.LIST, null, "test", false);
        configManager.registerField("materials", FieldType.LIST, null, "test", false);

        ConfigList<SpigotConfigColor> colors = new ConfigList<SpigotConfigColor>(Arrays.asList(new SpigotConfigColor(ChatColor.AQUA), new SpigotConfigColor(ChatColor.BLACK), new SpigotConfigColor(ChatColor.BLUE)));
        configManager.set("colors", colors);
        ConfigList<SpigotConfigColor> colorsDeserialized = configManager.getSpigotColorList("colors");
        assertEquals(colors, colorsDeserialized);

        ConfigList<SpigotConfigMaterial> materials = new ConfigList<SpigotConfigMaterial>(Arrays.asList(new SpigotConfigMaterial(Material.ACACIA_DOOR), new SpigotConfigMaterial(Material.ACACIA_DOOR_ITEM), new SpigotConfigMaterial(Material.ACACIA_FENCE)));
        configManager.set("materials", materials);
        ConfigList<SpigotConfigMaterial> materialsDeserialized = configManager.getSpigotMaterialList("materials");
        assertEquals(materials, materialsDeserialized);
    }

}
