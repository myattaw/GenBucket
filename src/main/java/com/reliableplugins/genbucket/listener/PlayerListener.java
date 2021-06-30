package com.reliableplugins.genbucket.listener;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    private GenBucket plugin;

    public PlayerListener(GenBucket plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {

        if (event.getPlayer().getItemInHand() == null) return;

        Action action = event.getAction();

        if (action == Action.PHYSICAL) return;

        if (!plugin.getConfig().getBoolean("settings.click-menu")) return;

        if (action == Action.LEFT_CLICK_BLOCK)
            return;

        if (event.hasItem() && (event.getItem().getType() == Material.LAVA_BUCKET || event.getItem().getType() == Material.WATER_BUCKET))
            return;

        String generatorType = plugin.getNMSHandler().getGeneratorType(event.getItem());
        if (generatorType != null) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            if (action == Action.RIGHT_CLICK_AIR) {
                player.updateInventory();
            }
            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (GenBucketManager.isPaused) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[!] &7Genbuckets have been temporarily disabled at this time!"));
                    return;
                }

                BlockFace blockFace = event.getBlockFace();
                Location location = event.getClickedBlock().getRelative(blockFace).getLocation();

                Generator generator = plugin.getGeneratorMap().get(generatorType);
                GeneratorData generatorData = new GeneratorData(location.getWorld().getName(), blockFace, player, location.getBlockX(), location.getBlockY(), location.getBlockZ());

                generator.addLocation(location.getChunk(), generatorData);
                generator.onPlace(generatorData, player, location);

                player.updateInventory();
            } else {
                player.openInventory(plugin.getMainMenu().getInventory());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {

        if (event.getPlayer().getItemInHand() == null) return;

        String generatorType = plugin.getNMSHandler().getGeneratorType(event.getPlayer().getItemInHand());
        if (generatorType != null) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            if (GenBucketManager.isPaused) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[!] &7Genbuckets have been temporarily disabled at this time!"));
                return;
            }

            BlockFace blockFace = event.getBlockFace();
            Location location = event.getBlockClicked().getRelative(blockFace).getLocation();

            Generator generator = plugin.getGeneratorMap().get(generatorType);
            GeneratorData generatorData = new GeneratorData(location.getWorld().getName(), blockFace, player, location.getBlockX(), location.getBlockY(), location.getBlockZ());

            generator.addLocation(location.getChunk(), generatorData);
            generator.onPlace(generatorData, player, location);
            player.updateInventory();
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/gb") || e.getMessage().startsWith("/genbucket") && !e.getPlayer().isOp() && plugin.getConfig().getBoolean("settings.use-replace-command")) {
            e.setCancelled(true);
            e.getPlayer().openInventory(plugin.getMainMenu().getInventory());
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (plugin.getConfig().getBoolean("settings.clear-drops") && plugin.getNMSHandler().getGeneratorType(event.getItemDrop().getItemStack()) != null) {
            event.getItemDrop().remove();
        }
    }

}
