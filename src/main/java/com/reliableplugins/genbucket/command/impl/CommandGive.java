package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.command.CommandBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandBuilder(label = "give", alias = "g", permission = "genbucket.give")
public class CommandGive extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        ItemStack itemStack = new ItemStack(Material.LAVA_BUCKET);
        player.getInventory().addItem(getPlugin().getNMSHandler().setGeneratorItem(itemStack, "CobbleVertical"));

        ItemStack itemStack2 = new ItemStack(Material.WATER_BUCKET);
        player.getInventory().addItem(getPlugin().getNMSHandler().setGeneratorItem(itemStack2, "CobbleHorizontal"));
        System.out.println(itemStack2.toString());
    }

}
