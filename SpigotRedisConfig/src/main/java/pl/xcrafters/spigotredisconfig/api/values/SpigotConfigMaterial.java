package pl.xcrafters.spigotredisconfig.api.values;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import pl.xcrafters.redisconfig.api.values.ConfigMaterial;

public class SpigotConfigMaterial extends ConfigMaterial {

    private Material material;

    public SpigotConfigMaterial(Material material) {
        super(material.name());
        this.material = material;
    }

    public SpigotConfigMaterial(String name) {
        super(name);
        this.material = Material.valueOf(name);
    }

    public Material getMaterial() {
        return this.material;
    }

}
