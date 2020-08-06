package com.reliableplugins.genbucket.menu;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.util.Util;
import com.reliableplugins.genbucket.util.XMaterial;
import org.bukkit.Bukkit;
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
        this.background = Util.setName(XMaterial.valueOf(plugin.getConfig().getString("menu-design.background.item").toUpperCase()).parseItem(), " ");
    }

    @Override
    public MainMenu init() {

        for (Generator generator : plugin.getGeneratorMap().values()) {
            itemSlots.put(generator.getSlot(), generator);
            getInventory().setItem(generator.getSlot(), Util.setNameAndLore(new ItemStack(generator.getMaterial()), generator.getName(), generator.getLore()));
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
        if (!event.getCurrentItem().equals(background)) {
            Player player = (Player) event.getWhoClicked();
            Generator generator = itemSlots.get(event.getSlot());
            ItemStack itemStack = Util.setNameAndLore(new ItemStack(generator.getItemType()), generator.getName(), generator.getLore());
            player.getInventory().addItem(plugin.getNMSHandler().setGeneratorItem(itemStack, generator.getKey()));
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
