package de.chocolatesec.hotchocolate.module;

import de.chocolatesec.hotchocolate.HotChocolate;
import de.chocolatesec.hotchocolate.logging.PrefixedLogger;

public abstract class Module {

    private final PrefixedLogger logger;

    public Module() {
        this.logger = HotChocolate.getLogger(this.name());
    }

    public void onEnable() {
        if (!this.silent()) {
            this.logger.info("Version " + this.version() + " by " + this.author() + " has been enabled.");
        }
    }

    public void onDisable() {
        if (!this.silent()) {
            this.logger.info("Version " + this.version() + " by " + this.author() + " has been disabled.");
        }
    }

    public abstract String name();

    public abstract String version();

    public abstract String author();

    public abstract String description();

    public abstract String[] aliases();

    public abstract Category category();

    public abstract boolean silent();

    public abstract Command[] commands();

    protected PrefixedLogger getLogger() {
        return this.logger;
    }
}