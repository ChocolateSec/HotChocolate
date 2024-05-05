package de.chocolatesec.hotchocolate.module;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class ResourceBridge {

    private final Module module;
    private final ResourceTracker resourceTracker;

    public ResourceBridge(Module module, ResourceTracker resourceTracker) {
        this.module = module;
        this.resourceTracker = resourceTracker;
    }

    void registerEventListener(Listener listener) {
        resourceTracker.registerEventListener(module, listener);
    }

    void registerEventListener(Plugin plugin, Listener listener) {
        resourceTracker.registerEventListener(module, plugin, listener);
    }

    void registerEventListeners() {
        resourceTracker.registerEventListeners(module);
    }

    void registerEventListeners(Plugin plugin) {
        resourceTracker.registerEventListeners(module, plugin);
    }

    void unregisterEventListener(Listener listener) {
        resourceTracker.unregisterEventListener(module, listener);
    }

    void unregisterEventListeners() {
        resourceTracker.unregisterEventListeners(module);
    }

    BukkitTask runTask(Runnable task) {
        return resourceTracker.runTask(module, task);
    }

    BukkitTask runTask(Plugin plugin, Runnable task) {
        return resourceTracker.runTask(module, plugin, task);
    }

    BukkitTask runTaskAsynchronously(Runnable task) {
        return resourceTracker.runTaskAsynchronously(module, task);
    }

    BukkitTask runTaskAsynchronously(Plugin plugin, Runnable task) {
        return resourceTracker.runTaskAsynchronously(module, plugin, task);
    }

    BukkitTask runTaskLater(Runnable task, long delay) {
        return resourceTracker.runTaskLater(module, task, delay);
    }

    BukkitTask runTaskLater(Plugin plugin, Runnable task, long delay) {
        return resourceTracker.runTaskLater(module, plugin, task, delay);
    }

    BukkitTask runTaskLaterAsynchronously(Runnable task, long delay) {
        return resourceTracker.runTaskLaterAsynchronously(module, task, delay);
    }

    BukkitTask runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay) {
        return resourceTracker.runTaskLaterAsynchronously(module, plugin, task, delay);
    }

    BukkitTask runTaskTimer(Runnable task, long delay, long period) {
        return resourceTracker.runTaskTimer(module, task, delay, period);
    }

    BukkitTask runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {
        return resourceTracker.runTaskTimer(module, plugin, task, delay, period);
    }

    BukkitTask runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        return resourceTracker.runTaskTimerAsynchronously(module, task, delay, period);
    }

    BukkitTask runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period) {
        return resourceTracker.runTaskTimerAsynchronously(module, plugin, task, delay, period);
    }

    int scheduleSyncDelayedTask(Runnable task) {
        return resourceTracker.scheduleSyncDelayedTask(module, task);
    }

    int scheduleSyncDelayedTask(Plugin plugin, Runnable task) {
        return resourceTracker.scheduleSyncDelayedTask(module, plugin, task);
    }

    int scheduleSyncDelayedTask(Runnable task, long delay) {
        return resourceTracker.scheduleSyncDelayedTask(module, task, delay);
    }

    int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        return resourceTracker.scheduleSyncDelayedTask(module, plugin, task, delay);
    }

    int scheduleSyncRepeatingTask(Runnable task, long delay, long period) {
        return resourceTracker.scheduleSyncRepeatingTask(module, task, delay, period);
    }

    int scheduleSyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
        return resourceTracker.scheduleSyncRepeatingTask(module, plugin, task, delay, period);
    }

    void cancelTask(int taskId) {
        resourceTracker.cancelTask(module, taskId);
    }

    void cancelTasks() {
        resourceTracker.cancelTasks(module);
    }

    public Module getModule() {
        return this.module;
    }

    public ResourceTracker getResourceTracker() {
        return this.resourceTracker;
    }
}
