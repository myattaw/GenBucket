package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.command.CommandBuilder;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import com.reliableplugins.genbucket.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.meta.BundleMeta;

/**
 * GenBucket - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 9/14/2020
 */
@CommandBuilder(label = "pause", alias = "stop", permission = "genbucket.pause")
public class CommandPause extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        GenBucketManager.isPaused = !GenBucketManager.isPaused;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[GENBUCKET] &7You have {action} &7genbucket iterations!").replace("{action}", GenBucketManager.isPaused ? Util.color("&c&lPAUSED") : Util.color("&a&lUNPAUSED")));
    }

}
