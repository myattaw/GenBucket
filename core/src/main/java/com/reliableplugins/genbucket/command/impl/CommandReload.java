package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.command.CommandBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * GenBucket - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 9/20/2020
 */
@CommandBuilder(label = "reload", alias = "rl", permission = "genbucket.reload")
public class CommandReload extends AbstractCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        getPlugin().reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[GENBUCKET] &7Configuration Reloaded!"));
    }
}
