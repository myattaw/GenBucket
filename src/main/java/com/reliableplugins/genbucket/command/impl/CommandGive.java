package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.command.CommandBuilder;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandBuilder(label = "give", alias = "g", permission = "genbucket.give")
public class CommandGive extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        // [player] [type] [amount]

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "/genbucket give [player] [type] [amount]");
        } else {

            int amount = 1;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {}

            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                Generator generator = plugin.getGeneratorMap().get(args[1].toLowerCase());
                if (generator != null) {
                    ItemStack itemStack = generator.getItemType().parseItem();
                    Util.setNameAndLore(itemStack, generator.getName(), generator.getLore());
                } else {
                    player.sendMessage(String.format(ChatColor.RED + "Could not find GenBucket type '%s' ", args[1]));
                }
            } else {
                player.sendMessage(String.format(ChatColor.RED + "Could not find the player '%s'", args[0]));
            }

        }

    }

}
