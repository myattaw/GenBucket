package com.reliableplugins.genbucket.listener;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerListener implements Listener {

    private GenBucket plugin;

    public PlayerListener(GenBucket plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBucketPlace(PlayerBucketEmptyEvent event) {
        String type;
        if ((type = plugin.getNMSHandler().getGeneratorType(event.getPlayer().getItemInHand())) != null) {
            Generator generator = GenBucketManager.getGeneratorMap().get(type);

            BlockFace blockFace = event.getBlockFace();
            Location location = event.getBlockClicked().getRelative(blockFace).getLocation();

            generator.getLocations().add(new GeneratorData(location.getWorld(), blockFace, location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            generator.onPlace(location);

            event.setCancelled(true);
        }
    }

}
