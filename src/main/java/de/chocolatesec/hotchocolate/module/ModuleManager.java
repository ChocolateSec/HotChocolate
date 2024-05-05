package de.chocolatesec.hotchocolate.module;

import java.util.ArrayList;
import java.util.logging.Level;

import de.chocolatesec.hotchocolate.HotChocolate;
import de.chocolatesec.hotchocolate.logging.PrefixedLogger;
import de.chocolatesec.hotchocolate.util.CommandMapMagic;

public class ModuleManager {

    private final PrefixedLogger logger;
    private final ArrayList<Module> modules;

    public ModuleManager() {
        this.logger = HotChocolate.getLogger("ModuleManager");
        this.modules = new ArrayList<>();
    }

    public void enableBuiltInModules() {
        // None yet
    }

    public void enable(Module module) {
        if (this.modules.contains(module)) {
            this.logger
                    .warning("Request to enable module " + module.name() + " was ignored, as it is already enabled.");
            return;
        }

        registerCommands(module);

        try {
            module.onEnable();
            this.modules.add(module);
        } catch (Exception e) {
            if (!module.silent()) {
                this.logger.log(Level.SEVERE, "Failed to enable module", e);
            }
        }
    }

    public void disable(Module module) {
        if (!this.modules.contains(module)) {
            this.logger.warning("Request to disable module " + module.name() + " was ignored, as it isn't enabled.");
            return;
        }

        unregisterCommands(module);

        try {
            module.onDisable();
            this.modules.remove(module);
        } catch (Exception e) {
            if (!module.silent()) {
                this.logger.log(Level.SEVERE, "Failed to disable module", e);
            }
        }
    }

    public void disableAll() {
        for (Module module : this.modules) {
            this.disable(module);
        }
    }

    public boolean isEnabled(Module module) {
        return this.modules.contains(module);
    }

    private void registerCommands(Module module) {
        for (Command command : module.commands()) {
            try {
                boolean registered = CommandMapMagic.injectCommand(module.name(), command);
                if (!registered && !module.silent()) {
                    this.logger
                            .severe("Failed to register command " + command.getName() + " for module " + module.name()
                                    + ": Command already exists (including fallbackPrefix)");
                }
            } catch (SecurityException | IllegalAccessException
                    | IllegalArgumentException | NoSuchFieldException e) {
                if (!module.silent()) {
                    this.logger.log(Level.SEVERE, "Failed to register command " + command.getName() + " for module "
                            + module.name(), e);
                }
            }
        }
    }

    private void unregisterCommands(Module module) {
        for (Command command : module.commands()) {
            try {
                CommandMapMagic.purgeCommand(module.name(), command);
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
                if (!module.silent()) {
                    this.logger.log(Level.SEVERE, "Failed to unregister command " + command.getName() + " for module "
                            + module.name(), e);
                }
            }
        }
    }

}
