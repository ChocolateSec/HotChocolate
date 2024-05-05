package de.chocolatesec.hotchocolate.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import de.chocolatesec.hotchocolate.HotChocolate;

public class ResourceTracker {

    private final HashMap<Module, List<Listener>> eventListeners;
    private final HashMap<Module, List<Integer>> tasks;

    public ResourceTracker() {
        this.eventListeners = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    public ResourceBridge createResourceBridge(Module module) {
        return new ResourceBridge(module, this);
    }

    void registerEventListener(Module module, Listener listener) {
        Plugin hotChocolate = HotChocolate.getInstance();
        registerEventListener(module, hotChocolate, listener);
    }

    void registerEventListener(Module module, Plugin plugin, Listener listener) {
        HotChocolate.getInstance().getServer().getPluginManager().registerEvents(listener, plugin);
        eventListeners.computeIfAbsent(module, k -> new ArrayList<>()).add(listener);
    }

    void registerEventListeners(Module module) {
        for (Listener eventListeners : module.eventListeners()) {
            registerEventListener(module, eventListeners);
        }
    }

    void registerEventListeners(Module module, Plugin plugin) {
        for (Listener eventListeners : module.eventListeners()) {
            registerEventListener(module, plugin, eventListeners);
        }
    }

    void unregisterEventListener(Module module, Listener listener) {
        if (!eventListeners.containsKey(module) || !eventListeners.get(module).contains(listener)) {
            return;
        }

        HandlerList.unregisterAll(listener);
        eventListeners.computeIfAbsent(module, k -> new ArrayList<>()).remove(listener);
    }

    void unregisterEventListeners(Module module) {
        if (!eventListeners.containsKey(module)) {
            return;
        }

        for (Listener listener : eventListeners.get(module)) {
            HandlerList.unregisterAll(listener);
        }
        eventListeners.remove(module);
    }

    BukkitTask runTask(Module module, Runnable task) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return runTask(module, hotChocolate, task);
    }

    BukkitTask runTask(Module module, Plugin plugin, Runnable task) {
        BukkitTask bukkitTask = HotChocolate.getInstance().getServer().getScheduler().runTask(plugin, task);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(bukkitTask.getTaskId());
        return bukkitTask;
    }

    BukkitTask runTaskAsynchronously(Module module, Runnable task) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return runTaskAsynchronously(module, hotChocolate, task);
    }

    BukkitTask runTaskAsynchronously(Module module, Plugin plugin, Runnable task) {
        BukkitTask bukkitTask = HotChocolate.getInstance().getServer().getScheduler().runTaskAsynchronously(plugin,
                task);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(bukkitTask.getTaskId());
        return bukkitTask;
    }

    BukkitTask runTaskLater(Module module, Runnable task, long delay) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return runTaskLater(module, hotChocolate, task, delay);
    }

    BukkitTask runTaskLater(Module module, Plugin plugin, Runnable task, long delay) {
        BukkitTask bukkitTask = HotChocolate.getInstance().getServer().getScheduler().runTaskLater(plugin, task, delay);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(bukkitTask.getTaskId());
        return bukkitTask;
    }

    BukkitTask runTaskLaterAsynchronously(Module module, Runnable task, long delay) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return runTaskLaterAsynchronously(module, hotChocolate, task, delay);
    }

    BukkitTask runTaskLaterAsynchronously(Module module, Plugin plugin, Runnable task, long delay) {
        BukkitTask bukkitTask = HotChocolate.getInstance().getServer().getScheduler()
                .runTaskLaterAsynchronously(plugin, task, delay);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(bukkitTask.getTaskId());
        return bukkitTask;
    }

    BukkitTask runTaskTimer(Module module, Runnable task, long delay, long period) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return runTaskTimer(module, hotChocolate, task, delay, period);
    }

    BukkitTask runTaskTimer(Module module, Plugin plugin, Runnable task, long delay, long period) {
        BukkitTask bukkitTask = HotChocolate.getInstance().getServer().getScheduler().runTaskTimer(plugin, task, delay,
                period);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(bukkitTask.getTaskId());
        return bukkitTask;
    }

    BukkitTask runTaskTimerAsynchronously(Module module, Runnable task, long delay, long period) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return runTaskTimerAsynchronously(module, hotChocolate, task, delay, period);
    }

    BukkitTask runTaskTimerAsynchronously(Module module, Plugin plugin, Runnable task, long delay, long period) {
        BukkitTask bukkitTask = HotChocolate.getInstance().getServer().getScheduler()
                .runTaskTimerAsynchronously(plugin, task, delay, period);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(bukkitTask.getTaskId());
        return bukkitTask;
    }

    int scheduleSyncDelayedTask(Module module, Runnable task) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return scheduleSyncDelayedTask(module, hotChocolate, task);
    }

    int scheduleSyncDelayedTask(Module module, Plugin plugin, Runnable task) {
        int taskId = HotChocolate.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(plugin, task);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(taskId);
        return taskId;
    }

    int scheduleSyncDelayedTask(Module module, Runnable task, long delay) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return scheduleSyncDelayedTask(module, hotChocolate, task, delay);
    }

    int scheduleSyncDelayedTask(Module module, Plugin plugin, Runnable task, long delay) {
        int taskId = HotChocolate.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, delay);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(taskId);
        return taskId;
    }

    int scheduleSyncRepeatingTask(Module module, Runnable task, long delay, long period) {
        Plugin hotChocolate = HotChocolate.getInstance();
        return scheduleSyncRepeatingTask(module, hotChocolate, task, delay, period);
    }

    int scheduleSyncRepeatingTask(Module module, Plugin plugin, Runnable task, long delay, long period) {
        int taskId = HotChocolate.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(plugin, task,
                delay, period);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).add(taskId);
        return taskId;
    }

    void cancelTask(Module module, int taskId) {
        if (!tasks.containsKey(module) || !tasks.get(module).contains(taskId)) {
            return;
        }

        HotChocolate.getInstance().getServer().getScheduler().cancelTask(taskId);
        tasks.computeIfAbsent(module, k -> new ArrayList<>()).remove(taskId);
    }

    void cancelTasks(Module module) {
        if (!tasks.containsKey(module)) {
            return;
        }

        for (int taskId : tasks.get(module)) {
            HotChocolate.getInstance().getServer().getScheduler().cancelTask(taskId);
        }
        tasks.remove(module);
    }

}
