package com.reliableplugins.genbucket.listener;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import com.reliableplugins.genbucket.util.CompatUtils;
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
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private GenBucket plugin;

    private boolean clearDrops;
    private boolean useClickMenu;
    private boolean replaceBucket;

    public PlayerListener(GenBucket plugin) {
        this.plugin = plugin;
        this.clearDrops = plugin.getConfig().getBoolean("settings.clear-drops", true);
        this.useClickMenu = plugin.getConfig().getBoolean("settings.click-menu", true);
        this.replaceBucket = plugin.getConfig().getBoolean("settings.replace-bucket", false);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {

        if (event.getItem() == null || !event.getItem().hasItemMeta()) return;

        Action action = event.getAction();
        Player player = event.getPlayer();

        Generator generator = GenBucketManager.getGeneratorByItemName(event.getItem().getItemMeta().getDisplayName());

        if (generator != null) {

            if ((action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) && useClickMenu) {
                player.openInventory(plugin.getMainMenu().getInventory());
            }

            if (action == Action.RIGHT_CLICK_BLOCK && !event.isCancelled()) {

                if (GenBucketManager.isPaused) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[!] &7Genbuckets have been temporarily disabled at this time!"));
                    return;
                }

                BlockFace blockFace = event.getBlockFace();
                Location location = event.getClickedBlock().getRelative(blockFace).getLocation();

                GeneratorData generatorData = new GeneratorData(location.getWorld().getName(), blockFace, player, location.getBlockX(), location.getBlockY(), location.getBlockZ());

                generator.addLocation(location.getChunk(), generatorData);
                generator.onPlace(generatorData, player, location);
                event.setCancelled(true);

                ItemStack itemInHand = CompatUtils.getItemInHand(player);

                if (replaceBucket && itemInHand != null) {
                    if (itemInHand.getAmount() > 1) {
                        itemInHand.setAmount(itemInHand.getAmount() - 1);
                        CompatUtils.setItemInHand(player, itemInHand);
                    } else {
                        if (itemInHand.getType() == Material.WATER_BUCKET || itemInHand.getType() == Material.LAVA_BUCKET) {
                            itemInHand = new ItemStack(Material.BUCKET);
                            CompatUtils.setItemInHand(player, itemInHand);
                        }
                    }
                }
            }
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

        ItemStack itemStack = event.getItemDrop().getItemStack();

        if (itemStack == null || !itemStack.hasItemMeta()) return;

        if (clearDrops && GenBucketManager.getGeneratorByItemName(itemStack.getItemMeta().getDisplayName()) != null) {
            event.getItemDrop().remove();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        ItemStack itemStack = CompatUtils.getItemInHand(event.getPlayer());
        if (itemStack != null && itemStack.hasItemMeta()) {
            if (GenBucketManager.getGeneratorByItemName(itemStack.getItemMeta().getDisplayName()) != null) {
                event.setCancelled(true);
            }
        }
    }

}
