package pl.xcrafters.bungeeredisconfig.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import pl.xcrafters.bungeeredisconfig.BungeeRedisConfigPlugin;
import pl.xcrafters.bungeeredisconfig.api.RedisConfigAPI;
import pl.xcrafters.redisconfig.api.ConfigField;
import pl.xcrafters.redisconfig.api.FieldType;
import pl.xcrafters.redisconfig.api.RedisConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BungeeConfigCommand extends Command implements TabExecutor {

    BungeeRedisConfigPlugin plugin;

    public BungeeConfigCommand(BungeeRedisConfigPlugin plugin) {
        super("bungeeconfig", "bungeeconfig.manage");
        this.plugin = plugin;
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<String>();

        if(args.length == 2) {
            for(CommandAction action : CommandAction.values()) {
                if(action.name().toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(action.name().toLowerCase());
                }
            }
        } else if(args.length == 1) {
            for(Map.Entry<String, RedisConfigManager> entry : RedisConfigAPI.getRegisteredConfigs().entrySet()) {
                if(entry.getKey().startsWith(args[0].toLowerCase())) {
                    completions.add(entry.getValue().getPluginName());
                }
            }

            if("all".startsWith(args[0].toLowerCase())) {
                completions.add("all");
            }
        }

        return completions;
    }

    private enum CommandAction {
        RELOAD,
        EXPORT,
        IMPORT
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }

        String pluginName = args[0].toLowerCase();

        if(pluginName.equals("all")) {
            for(Map.Entry<String, RedisConfigManager> entry : pl.xcrafters.redisconfig.api.RedisConfigAPI.getRegisteredConfigs().entrySet()) {
                handlePlugin(entry.getValue(), sender, args);
            }
            return;
        }

        RedisConfigManager config = pl.xcrafters.redisconfig.api.RedisConfigAPI.getRegisteredConfigs().get(pluginName);
        if (config == null) {
            sender.sendMessage("§cNie znaleziono konfiguracji pluginu o podanej nazwie.");
            return;
        }
        handlePlugin(config, sender, args);
    }

    private void handlePlugin(RedisConfigManager config, CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("§7### §9" + config.getPluginName() + " §7###");

            for (Map.Entry<String, ConfigField> entry : config.getConfigFields().entrySet()) {
                ConfigField field = entry.getValue();

                if (field.getSecret()) {
                    continue;
                }

                if (field.getType() == FieldType.JSON) {
                    sender.sendMessage("§8- §c" + field.getName() + " §9(" + field.getType().name() + ")");
                    continue;
                }

                String defaultValue = null;
                if (field.getDefaultValue() != null) {
                    defaultValue = field.getDefaultValue().toString();
                }

                sender.sendMessage("§8- §c" + field.getName() + " §9(" + field.getType().name() + ")§8: §7" + field.getValue() + " §8(def: " + defaultValue + ")");
            }
            return;
        } else if (args.length == 2) {
            CommandAction action = null;

            try {
                action = CommandAction.valueOf(args[1].toUpperCase());
            } catch(IllegalArgumentException ex) {
                sender.sendMessage("§cMozliwe akcje: ");

                for(CommandAction ac : CommandAction.values()) {
                    sender.sendMessage(String.format("§7 - %s", ac.name().toLowerCase()));
                }
            }

            if(action == null) {
                sendUsage(sender);
                return;
            }

            switch (action) {
                case RELOAD:
                    config.load();
                    sender.sendMessage(String.format("§7Konfiguracja pluginu §9%s §7zostala przeladowana.", config.getPluginName()));
                    break;
                case IMPORT:
                    boolean success = config.importFromFile(plugin.getConfigManager().getExportPath());
                    if(success) {
                        sender.sendMessage(String.format("§7Konfiguracja pluginu §9%s §7zostala zaimportowana z pliku.", config.getPluginName()));
                    } else {
                        sender.sendMessage("§cWystapil blad podczas importowania konfiguracji.");
                    }
                    break;
                case EXPORT:
                    success = config.exportToFile(plugin.getConfigManager().getExportPath());
                    if(success) {
                        sender.sendMessage(String.format("§7Konfiguracja pluginu §9%s §7zostala wyeksportowana do pliku.", config.getPluginName()));
                    } else {
                        sender.sendMessage("§cWystapil blad podczas eksportowania konfiguracji.");
                    }
                    break;
            }
            return;
        }

        sendUsage(sender);
    }

    void sendUsage(CommandSender sender) {
        sender.sendMessage("§c/bungeeconfig <plugin/all> §7- wyswietlanie informacji o konfiguracji pluginu.");
        sender.sendMessage("§c/bungeeconfig <plugin/all> reload §7- przeladowuje konfiguracje pluginu.");
        sender.sendMessage("§c/bungeeconfig <plugin/all> import §7- importuje .");
        sender.sendMessage("§c/bungeeconfig <plugin/all> export §7- przeladowuje konfiguracje pluginu.");
    }

}

