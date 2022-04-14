package com.ilucah.dimensionlevels.menu;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.utils.color.IridiumColorAPI;
import com.ilucah.dimensionlevels.utils.xutils.SkullCreator;
import com.ilucah.dimensionlevels.utils.xutils.XMaterial;
import com.ilucah.dimensionlevels.utils.xutils.XSound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class MenuManager {

    private DimensionLevels plugin;
    private Menu menu;

    private Sound openSound;

    public MenuManager(DimensionLevels plugin) {
        this.plugin = plugin;
        loadMenu();
    }

    public void loadMenu() {
        FileConfiguration config = plugin.getFileModule().getMenusYML();
        openSound = XSound.valueOf(config.getString("levels-menu.open-sound")).parseSound();
        String menuName = config.getString("levels-menu.display-name");
        int size = config.getInt("levels-menu.size");
        ItemStack filler = filler(XMaterial.valueOf(config.getString("levels-menu.filler-item")).parseMaterial());
        int pages = config.getInt("levels-menu.pages");
        int firstOpenPage = config.getInt("levels-menu.first-open-page");
        int nexPageSlot = config.getInt("levels-menu.next-page-item.slot");
        int previousPageSlot = config.getInt("levels-menu.previous-page-item.slot");
        menu = new Menu(plugin, size, menuName, filler, pages, firstOpenPage, previousPageSlot, nexPageSlot, buildNavigationItem(config, "previous-page-item"), buildNavigationItem(config, "next-page-item"));
        for (String string : config.getConfigurationSection("levels-menu.items").getKeys(false)) {
            String path = "levels-menu.items." + string + ".";
            String displayName = IridiumColorAPI.process(config.getString(path + "display-name"));
            List<String> displayLore = IridiumColorAPI.process(config.getStringList(path + "display-lore"));
            ItemStack item;
            if (config.getString(path + "skull-owner-or-base64") != null)
                item = SkullCreator.createSkullFromString(config.getString(path + "skull-owner-or-base64"));
            else
                item = new ItemStack(XMaterial.valueOf(config.getString(path + "material")).parseMaterial());
            boolean glowing = config.getBoolean(path + "glowing");
            int slot = config.getInt(path + "slot");
            int page = config.getInt(path + "page");
            menu.getPages().get(page).getInventory().setItem(slot, buildItem(displayName, displayLore, item, glowing));
        }
    }

    private ItemStack buildItem(String name, List<String> lore, ItemStack baseItem, boolean glowing) {
        ItemStack item = baseItem.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if (glowing)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        if (glowing)
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    private ItemStack buildNavigationItem(FileConfiguration config, String path) {
        Material material = XMaterial.valueOf(config.getString("levels-menu." + path + ".material")).parseMaterial();
        String name = IridiumColorAPI.process(config.getString("levels-menu." + path + ".display-name"));
        List<String> lore = IridiumColorAPI.process(config.getStringList("levels-menu." + path + ".display-lore"));
        boolean glowing = config.getBoolean("levels-menu." + path + ".glowing");
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if (glowing)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        if (glowing)
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    private ItemStack filler(Material material) {
        ItemStack filler = new ItemStack(material);
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);
        return filler;
    }

    public Inventory getMenu() {
        return menu.getMenu();
    }

    public Sound getOpenSound() {
        return openSound;
    }
}
