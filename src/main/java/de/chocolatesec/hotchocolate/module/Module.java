package de.chocolatesec.hotchocolate.module;

import org.bukkit.event.Listener;

import de.chocolatesec.hotchocolate.HotChocolate;
import de.chocolatesec.hotchocolate.logging.PrefixedLogger;

public abstract class Module {

    public abstract String name();

    public abstract String version();

    public abstract String author();

    public abstract String description();

    public abstract String[] aliases();

    public abstract Category category();

    public abstract boolean silent();

    public abstract Command[] commands();

    public abstract Listener[] eventListeners();

    private final PrefixedLogger logger;

    private final ResourceBridge resourceBridge;

    public Module(ResourceTracker resourceTracker) {
        this.logger = HotChocolate.getLogger(name());
        this.resourceBridge = resourceTracker.createResourceBridge(this);
    }

    public void onEnable() {
        if (!silent()) {
            logger.info("Version " + version() + " by " + author() + " has been enabled.");
        }
    }

    public void onDisable() {
        if (!silent()) {
            logger.info("Version " + version() + " by " + author() + " has been disabled.");
        }
    }

    protected PrefixedLogger getLogger() {
        return logger;
    }

    protected ResourceBridge getResourceBridge() {
        return resourceBridge;
    }
}