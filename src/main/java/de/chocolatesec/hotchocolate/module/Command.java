package de.chocolatesec.hotchocolate.module;

import java.util.List;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.google.common.base.Preconditions;

public abstract class Command extends org.bukkit.command.Command implements CommandExecutor, TabCompleter {

    public Command(String name, String description, String usage, String[] aliases) {
        super(name, description, usage, List.of(aliases));
    }

    // Copied from org.bukkit.command.PluginCommand
    @Override
    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        boolean success = false;
        if (!testPermission(arg0)) {
            return true;
        } else {
            try {
                success = onCommand(arg0, this, arg1, arg2);
            } catch (Throwable var9) {
                throw new CommandException("Unhandled exception executing command '" + arg1 + "'", var9);
            }

            if (!success && usageMessage.length() > 0) {
                String[] var8;
                int var7 = (var8 = usageMessage.replace("<command>", arg1).split("\n")).length;

                for (int var6 = 0; var6 < var7; ++var6) {
                    String line = var8[var6];
                    arg0.sendMessage(line);
                }
            }

            return success;
        }
    }

    // Copied from org.bukkit.command.PluginCommand
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Preconditions.checkArgument(sender != null, "Sender cannot be null");
        Preconditions.checkArgument(args != null, "Arguments cannot be null");
        Preconditions.checkArgument(alias != null, "Alias cannot be null");
        List<String> completions = null;

        try {
            completions = onTabComplete(sender, this, alias, args);
        } catch (Throwable var11) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            String[] var10 = args;
            int var9 = args.length;

            for (int var8 = 0; var8 < var9; ++var8) {
                String arg = var10[var8];
                message.append(arg).append(' ');
            }

            message.deleteCharAt(message.length() - 1);
            throw new CommandException(message.toString(), var11);
        }

        return completions == null ? super.tabComplete(sender, alias, args) : completions;
    }
}
