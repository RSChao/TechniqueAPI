package com.rschao.plugins.techniqueAPI.item;

import com.rschao.plugins.techniqueAPI.TechAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Items {


    public static ItemStack abyss(String name) {
        ItemStack item = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = item.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(
                new NamespacedKey(TechAPI.INSTANCE, "channeler"),
                org.bukkit.persistence.PersistentDataType.BOOLEAN, true
        );
        meta.setItemName(name);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack description(String name) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.getPersistentDataContainer().set(
                new NamespacedKey(TechAPI.INSTANCE, "descripstin"),
                org.bukkit.persistence.PersistentDataType.BOOLEAN, true
        );
        meta.setLore(List.of("Right click to read technique description.", "The descripted technique will be your chosen technique(s)"));
        meta.setItemName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack abyssContainer(String id){
        ItemStack item = new ItemStack(Material.GUNPOWDER);
        ItemMeta meta = item.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.setMaxStackSize(1);
        meta.getPersistentDataContainer().set(
                new NamespacedKey(TechAPI.INSTANCE, "abyss_id"),
                org.bukkit.persistence.PersistentDataType.STRING, id
        );
        meta.setItemName(ChatColor.DARK_PURPLE + "Abyss ID: " + id);
        item.setItemMeta(meta);
        return item;
    }
}
