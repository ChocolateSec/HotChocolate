package de.chocolatesec.hotchocolate.util;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;

import de.chocolatesec.hotchocolate.HotChocolate;
import de.chocolatesec.hotchocolate.module.Command;

public class CommandMapMagic {

    public static boolean injectCommand(String fallbackPrefix, Command command)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return injectCommand(fallbackPrefix, command.getName(), command);
    }

    public static boolean injectCommand(String fallbackPrefix, String label, Command command)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        SimpleCommandMap simpleCommandMap = getSimpleCommandMap();
        return simpleCommandMap.register(label, fallbackPrefix, command);
    }

    public static void purgeCommand(String fallbackPrefix, Command command)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        purgeCommand(fallbackPrefix, command.getName());
    }

    public static synchronized void purgeCommand(String fallbackPrefix, String label)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        label = label.toLowerCase(Locale.ENGLISH).trim();
        fallbackPrefix = fallbackPrefix.toLowerCase(Locale.ENGLISH).trim();

        Map<String, org.bukkit.command.Command> knownCommands = getKnownCommands();

        knownCommands.remove(label);
        knownCommands.remove(fallbackPrefix + ":" + label);
    }

    public static org.bukkit.command.Command getCommand(String fallbackPrefix, Command command)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return getCommand(fallbackPrefix, command.getName());
    }

    public static synchronized org.bukkit.command.Command getCommand(String fallbackPrefix, String label)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        label = label.toLowerCase(Locale.ENGLISH).trim();
        fallbackPrefix = fallbackPrefix.toLowerCase(Locale.ENGLISH).trim();

        Map<String, org.bukkit.command.Command> knownCommands = getKnownCommands();

        return knownCommands.get(label);
    }

    public static PluginCommand getPluginCommand(String fallbackPrefix, Command command) {
        return getPluginCommand(fallbackPrefix, command.getName());
    }

    public static PluginCommand getPluginCommand(String fallbackPrefix, String label) {
        label = label.toLowerCase(Locale.ENGLISH).trim();
        fallbackPrefix = fallbackPrefix.toLowerCase(Locale.ENGLISH).trim();

        PluginCommand pluginCommand = HotChocolate.getInstance().getServer().getPluginCommand(label);
        if (pluginCommand == null) {
            pluginCommand = HotChocolate.getInstance().getServer().getPluginCommand(fallbackPrefix + ":" + label);
        }

        return pluginCommand;
    }

    public static SimpleCommandMap getSimpleCommandMap()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        SimpleCommandMap simpleCommandMap = ((CraftServer) Bukkit.getServer()).getCommandMap();

        Field knownCommandField = SimpleCommandMap.class.getDeclaredField("knownCommands");
        knownCommandField.setAccessible(true);
        knownCommandField.get(simpleCommandMap);

        return simpleCommandMap;
    }

    public static Map<String, org.bukkit.command.Command> getKnownCommands()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        SimpleCommandMap simpleCommandMap = getSimpleCommandMap();

        Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");

        @SuppressWarnings("unchecked")
        Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) knownCommandsField
                .get(simpleCommandMap);
        return knownCommands;
    }

}
