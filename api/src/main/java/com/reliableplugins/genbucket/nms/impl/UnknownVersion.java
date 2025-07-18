package com.reliableplugins.genbucket.nms.impl;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.nms.NMSAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Optional;

/**
 * @author : Michael
 * @since : 6/30/2021, Wednesday
 **/
public class UnknownVersion implements NMSAdapter {

    @Override
    public void setBlock(World world, int x, int y, int z, Material material, byte data) {
        // Validate inputs
        if (world == null || material == null) {
            return; // Silently return for null inputs
        }
        if (y > world.getMaxHeight() || y < world.getMinHeight()) {
            return; // Out-of-bounds Y coordinate
        }

        Block block = world.getBlockAt(x, y, z);

        // Handle AIR explicitly
        if (material == Material.AIR) {
            block.setType(Material.AIR, false);
            return;
        }

        try {
            // Convert Material to legacy ID for XMaterial v9
            int materialId = XMaterial.matchXMaterial(material).getId();
            if (materialId == -1) {
                Bukkit.getLogger().warning("Unsupported material: " + material.name() + " at (" + x + ", " + y + ", " + z + ")");
                return;
            }

            // Use XMaterial to resolve the material
            Optional<XMaterial> xmat = XMaterial.matchXMaterial(materialId, data);
            if (xmat.isPresent()) {
                Material resolvedMaterial = xmat.get().parseMaterial();
                if (resolvedMaterial != null) {
                    block.setType(resolvedMaterial, false);
                } else {
                    Bukkit.getLogger().warning("XMaterial resolved to null for ID: " + materialId + ", data: " + data + " at (" + x + ", " + y + ", " + z + ")");
                }
            } else {
                Bukkit.getLogger().warning("No XMaterial match for ID: " + materialId + ", data: " + data + " at (" + x + ", " + y + ", " + z + ")");
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Error setting block at (" + x + ", " + y + ", " + z + ") to " + material.name() + ":" + data + ": " + e.getMessage());
        }
    }

}