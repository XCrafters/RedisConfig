package pl.xcrafters.bungeeredisconfig.events;

import net.md_5.bungee.api.plugin.Event;

public class ConfigRefreshEvent extends Event {

    private String pluginName;

    public ConfigRefreshEvent(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() { return this.pluginName; }

}

