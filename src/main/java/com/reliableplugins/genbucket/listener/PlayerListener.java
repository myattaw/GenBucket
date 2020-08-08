package com.reliableplugins.genbucket.listener;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    private GenBucket plugin;

    public PlayerListener(GenBucket plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getPlayer().getItemInHand() == null) return;

        Action action = event.getAction();

        if (action == Action.PHYSICAL || action == Action.RIGHT_CLICK_AIR) return;

        if (!plugin.getConfig().getBoolean("settings.click-menu") && (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK))
            return;

        String generatorType = plugin.getNMSHandler().getGeneratorType(event.getPlayer().getItemInHand());
        if (generatorType != null) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            if (action == Action.RIGHT_CLICK_BLOCK) {
                BlockFace blockFace = event.getBlockFace();
                Location location = event.getClickedBlock().getRelative(blockFace).getLocation();

                Generator generator = plugin.getGeneratorMap().get(generatorType);
                GeneratorData generatorData = new GeneratorData(location.getWorld(), blockFace, player, location.getBlockX(), location.getBlockY(), location.getBlockZ());

                generator.getLocations().add(generatorData);
                generator.onPlace(generatorData, player, location);
            } else {
                player.openInventory(plugin.getMainMenu().getInventory());
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (plugin.getConfig().getBoolean("settings.clear-drops") && plugin.getNMSHandler().getGeneratorType(event.getItemDrop().getItemStack()) != null) {
            event.getItemDrop().remove();
        }
    }

}
