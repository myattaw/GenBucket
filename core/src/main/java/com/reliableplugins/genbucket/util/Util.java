package com.reliableplugins.genbucket.util;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> string) {
        return string.stream().map(Util::color).collect(Collectors.toList());
    }

    @SafeVarargs
    public static List<String> updateLore(List<String> lore, Map.Entry<String, String>... placeholders) {
        List<String> newLore = new ArrayList<>();
        for (String line : lore) {
            for (Map.Entry<String, String> placeholder : placeholders) {
                if (line.toUpperCase().contains(placeholder.getKey().toUpperCase())) {
                    line = line.replace(String.format("[%s]", placeholder.getKey().toUpperCase()), placeholder.getValue());
                }
            }
            newLore.add(line);
        }
        return newLore;
    }

    public static ItemStack setName(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(color(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(color(lore));
        itemStack.setItemMeta(itemMeta);
    }

    public static ItemStack setNameAndLore(ItemStack itemStack, String name, List<String> lore) {
        setName(itemStack, name);
        setLore(itemStack, lore);
        return itemStack;
    }

    public static void addGlow(ItemStack generatorItem) {
        ItemMeta itemMeta = generatorItem.getItemMeta();
        itemMeta.addEnchant(Enchantment.LURE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        generatorItem.setItemMeta(itemMeta);
    }

}