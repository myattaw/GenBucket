package com.reliableplugins.genbucket.menu;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import com.reliableplugins.genbucket.util.CompatUtils;
import com.reliableplugins.genbucket.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MainMenu extends MenuBuilder {

    private GenBucket plugin;

    private ItemStack background;
    private Map<Integer, Generator> itemSlots = new HashMap<>();

    public MainMenu(GenBucket plugin) {
        super(plugin.getConfig().getString("menu-design.title"), plugin.getConfig().getInt("menu-design.rows"));
        this.plugin = plugin;
        this.background = Util.setName(XMaterial.valueOf(plugin.getConfig().getString("menu-design.background").toUpperCase()).parseItem(), " ");
    }

    @Override
    public MainMenu init() {

        for (Generator generator : plugin.getGeneratorMap().values()) {
            itemSlots.put(generator.getSlot(), generator);

            ItemStack generatorItem = Util.setNameAndLore(
                    generator.getItemType().parseItem(),
                    generator.getName(), generator.getLore()
            );

            if (generator.isGlow() && generatorItem != null) {
                Util.addGlow(generatorItem);
            }

            getInventory().setItem(generator.getSlot(), generatorItem);
        }

        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, background);
            }
        }

        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (item == null || item.equals(background)) return;

        if (itemSlots.containsKey(event.getSlot())) {
            Player player = (Player) event.getWhoClicked();
            Generator generator = itemSlots.get(event.getSlot());

            ItemStack itemInHand = CompatUtils.getItemInHand(player);

            ItemStack itemStack = Util.setNameAndLore(generator.getItemType().parseItem(), generator.getName(), generator.getLore());

            if (itemInHand != null && itemInHand.hasItemMeta() && GenBucketManager.getGeneratorByItemName(itemInHand.getItemMeta().getDisplayName()) != null) {
                CompatUtils.setItemInHand(player, item);
            } else {
                player.getInventory().addItem(itemStack);
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {
    }
}
