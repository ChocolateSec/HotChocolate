package de.chocolatesec.hotchocolate;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import de.chocolatesec.hotchocolate.logging.PrefixedLogger;
import de.chocolatesec.hotchocolate.module.ModuleManager;

public class HotChocolate extends JavaPlugin {

    private static HotChocolate instance;

    private static ModuleManager moduleManager;

    public void onEnable() {
        HotChocolate.instance = this;

        HotChocolate.moduleManager = new ModuleManager();
        HotChocolate.moduleManager.loadBuiltInModules();

        getLogger().info("HotChocolate enabled!");
    }

    public void onDisable() {
        getLogger().info("HotChocolate disabled!");
    }

    public static HotChocolate getInstance() {
        return instance;
    }

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }

    public static Logger getPluginLogger() {
        return instance.getLogger();
    }

    public static PrefixedLogger getLogger(String prefix) {
        return new PrefixedLogger(getPluginLogger(), prefix);
    }
}
