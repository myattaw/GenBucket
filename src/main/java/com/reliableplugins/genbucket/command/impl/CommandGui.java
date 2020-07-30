package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.command.CommandBuilder;
import com.reliableplugins.genbucket.menu.MainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "gui", alias = "shop", permission = "genbucket.use", playerRequired = true)
public class CommandGui extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Opening gui");
        MainMenu mainMenu = new MainMenu(getPlugin()).init();
        Player player = (Player) sender;
        player.openInventory(mainMenu.getInventory());
    }
}
