package com.reliableplugins.genbucket.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Method;

public class CompatUtils {

    private static boolean legacy = false;
    private static Method legacyGetMethod = null;
    private static Method legacySetMethod = null;

    static {
        try {
            // Modern method exists (1.9+)
            PlayerInventory.class.getMethod("getItemInMainHand");
            PlayerInventory.class.getMethod("setItemInMainHand", ItemStack.class);
            legacy = false;
        } catch (NoSuchMethodException e) {
            try {
                // Legacy methods (1.8)
                legacyGetMethod = PlayerInventory.class.getMethod("getItemInHand");
                legacySetMethod = PlayerInventory.class.getMethod("setItemInHand", ItemStack.class);
                legacy = true;
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ItemStack getItemInHand(Player player) {
        if (!legacy) {
            // Modern servers: direct call
            return player.getInventory().getItemInMainHand();
        } else {
            try {
                return (ItemStack) legacyGetMethod.invoke(player.getInventory());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void setItemInHand(Player player, ItemStack item) {
        if (!legacy) {
            // Modern servers: direct call
            player.getInventory().setItemInMainHand(item);
        } else {
            try {
                legacySetMethod.invoke(player.getInventory(), item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}