package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class BaseCommand implements CommandExecutor {

    public Map<String, AbstractCommand> subcommands = new HashMap<>();

    private CommandHelp commandHelp;

    public BaseCommand(GenBucket plugin) {
        this.commandHelp = new CommandHelp(this);
        addCommand(new CommandGui(), plugin);
        addCommand(new CommandGive(), plugin);
        addCommand(new CommandTest(), plugin);
        addCommand(new CommandPause(), plugin);
        plugin.getCommand("genbucket").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {

        if (args.length == 0) {
            if (commandSender.hasPermission(commandHelp.getPermission())) {
                commandHelp.execute(commandSender, args);
                return true;
            }
            commandSender.sendMessage(Message.ERROR_PERMISSION.getMessage());
            return true;
        }

        for (AbstractCommand subcommand : subcommands.values()) {

            if (!args[0].equalsIgnoreCase(subcommand.getLabel()) && !subcommand.getAlias().contains(args[0].toLowerCase()))
                continue;

            if (!(commandSender instanceof Player) && subcommand.isPlayerRequired()) {
                commandSender.sendMessage(Message.ERROR_NOT_PLAYER.getMessage());
                return true;
            }

            if (args[0].equalsIgnoreCase(subcommand.getLabel()) || subcommand.getAlias().contains(args[0].toLowerCase())) {
                if (!subcommand.hasPermission() || commandSender.hasPermission(subcommand.getPermission()) || commandSender.isOp()) {
                    subcommand.execute(commandSender, Arrays.copyOfRange(args, 1, args.length));
                } else {
                    commandSender.sendMessage(Message.ERROR_PERMISSION.getMessage());
                    return true;
                }
            }
        }

        return true;
    }

    public void addCommand(AbstractCommand command, GenBucket plugin) {
        command.setPlugin(plugin);
        this.subcommands.put(command.getLabel().toLowerCase(), command);
    }

    public Collection<AbstractCommand> getCommands() {
        return Collections.unmodifiableCollection(subcommands.values());
    }

}