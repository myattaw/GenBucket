package com.reliableplugins.genbucket.menu;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.util.Util;
import com.reliableplugins.genbucket.util.XMaterial;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends MenuBuilder {

    private GenBucket plugin;

    public MainMenu(GenBucket plugin) {
        super(plugin.getConfig().getString("menu-design.title"), plugin.getConfig().getInt("menu-design.rows"));
        this.plugin = plugin;
    }

    @Override
    public MainMenu init() {
        ItemStack background = Util.setName(XMaterial.valueOf(plugin.getConfig().getString("menu-design.background.item").toUpperCase()).parseItem(), " ");

        for (Generator generator : plugin.getGeneratorMap().values()) {
            ItemStack genbucket = Util.setNameAndLore(new ItemStack(generator.getMaterial()), generator.getName(), generator.getLore());
            getInventory().setItem(generator.getSlot(), genbucket);
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

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
