package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.command.CommandBuilder;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "test", alias = "test", permission = "genbucket.test", playerRequired = true)
public class CommandTest extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        Location loc = player.getLocation();

        if(getPlugin().worldWhitelist.contains(loc.getWorld().getName())){
            player.sendMessage(Message.WORLD_NOT_WHITELISTED.getMessage());
            return;
        }

        Generator generator = getPlugin().getGeneratorMap().get("cobblevertical");

        long start = System.currentTimeMillis();

        int amount = 0;

        for (int x = loc.getBlockX() - 250; x < loc.getBlockX() + 250; x++) {
            for (int z = loc.getBlockZ() - 250; z < loc.getBlockZ() + 250; z++) {
                BlockFace blockFace = BlockFace.WEST;
                Location location = loc.getWorld().getBlockAt(x, loc.getBlockY() - 5, z).getLocation();
                GeneratorData generatorData = new GeneratorData(location.getWorld().getName(), blockFace, player, location.getBlockX(), location.getBlockY(), location.getBlockZ());
                generator.addLocation(location.getChunk(), generatorData);
                amount++;
            }
        }
        Bukkit.broadcastMessage(ChatColor.YELLOW + String.format("You created %d GenBuckets in " + (System.currentTimeMillis() - start) + "ms!", amount));
    }
}
