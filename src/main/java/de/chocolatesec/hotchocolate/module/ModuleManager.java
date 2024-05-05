package de.chocolatesec.hotchocolate.module;

import java.util.ArrayList;
import java.util.logging.Level;

import de.chocolatesec.hotchocolate.HotChocolate;
import de.chocolatesec.hotchocolate.logging.PrefixedLogger;
import de.chocolatesec.hotchocolate.util.CommandMapMagic;

public class ModuleManager {

    private final PrefixedLogger logger;
    private final ArrayList<Module> modules;
    private final ResourceTracker resourceTracker;

    public ModuleManager() {
        this.logger = HotChocolate.getLogger("ModuleManager");
        this.modules = new ArrayList<>();
        this.resourceTracker = new ResourceTracker();
    }

    public void loadBuiltInModules() {
        // None yet
    }

    public void enable(Module module) {
        if (modules.contains(module) && !module.silent()) {
            logger.warning("Request to enable module " + module.name() + " was ignored, as it is already enabled.");
            return;
        }
        modules.add(module);

        registerCommands(module);
        resourceTracker.registerEventListeners(module);

        try {
            module.onEnable();
        } catch (Exception e) {
            if (!module.silent()) {
                logger.log(Level.SEVERE, "Failed to enable module", e);
            }
        }
    }

    public void disable(Module module) {
        if (!modules.contains(module)) {
            logger.warning("Request to disable module " + module.name() + " was ignored, as it isn't enabled.");
            return;
        }
        modules.remove(module);

        unregisterCommands(module);
        resourceTracker.unregisterEventListeners(module);

        try {
            module.onDisable();
        } catch (Exception e) {
            if (!module.silent()) {
                logger.log(Level.SEVERE, "Failed to disable module", e);
            }
        }
    }

    public void disableAll() {
        for (Module module : modules) {
            disable(module);
        }
    }

    public boolean isEnabled(Module module) {
        return modules.contains(module);
    }

    private void registerCommands(Module module) {
        if (module.commands() == null) {
            return;
        }

        for (Command command : module.commands()) {
            try {
                boolean registered = CommandMapMagic.injectCommand(module.name(), command);
                if (!registered && !module.silent()) {
                    logger.severe("Failed to register command " + command.getName() + " for module " + module.name()
                            + ": Command already exists (including fallbackPrefix)");
                }
            } catch (SecurityException | IllegalAccessException
                    | IllegalArgumentException | NoSuchFieldException e) {
                if (!module.silent()) {
                    logger.log(Level.SEVERE,
                            "Failed to register command " + command.getName() + " for module " + module.name(), e);
                }
            }
        }
    }

    private void unregisterCommands(Module module) {
        if (module.commands() == null) {
            return;
        }

        for (Command command : module.commands()) {
            try {
                CommandMapMagic.purgeCommand(module.name(), command);
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
                if (!module.silent()) {
                    logger.log(Level.SEVERE, "Failed to unregister command " + command.getName() + " for module "
                            + module.name(), e);
                }
            }
        }
    }

}
