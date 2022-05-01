package com.ilucah.dimensionlevels.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Optional;

public class Menu implements Listener {

    private HashMap<Integer, DimensionalInv> pages;
    private int startingPage, nextPageSlot, previousPageSlot;

    public Menu(Plugin plugin, int size, String title, ItemStack fillerItem, int pages, int startingPage, int previousInvSlot, int nexInvSlot, ItemStack previousInvItem, ItemStack nexInvItem) {
        this.pages = new HashMap<>();
        this.startingPage = startingPage;
        this.nextPageSlot = nexInvSlot;
        this.previousPageSlot = previousInvSlot;
        for (int i = 0; i < pages; i++) {
            this.pages.put(i + 1, new DimensionalInv(i + 1, Bukkit.createInventory(null, size, title.replace("{page}", String.valueOf(i + 1)))));
        }
        for (int p = 0; p < pages; p++) {
            for (int i = 0; i < size; i++) {
                this.pages.get(p + 1).getInventory().setItem(i, fillerItem);
            }
            this.pages.get(p + 1).getInventory().setItem(previousInvSlot, previousInvItem);
            this.pages.get(p + 1).getInventory().setItem(nextPageSlot, nexInvItem);
        }
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
            return;
        Optional<DimensionalInv> inv = pages.values().stream().filter(i -> event.getInventory().equals(i.getInventory())).findFirst();
        if (!inv.isPresent())
            return;
        event.setCancelled(true);
        if (event.getSlot() != nextPageSlot && event.getSlot() != previousPageSlot)
            return;
        if (event.getSlot() == nextPageSlot) {
            if (!pages.containsKey(inv.get().getPage() + 1))
                return;
            event.getWhoClicked().openInventory(pages.get(inv.get().getPage() + 1).getInventory());
        } else {
            if (!pages.containsKey(inv.get().getPage() - 1))
                return;
            event.getWhoClicked().openInventory(pages.get(inv.get().getPage() - 1).getInventory());
        }
    }

    public HashMap<Integer, DimensionalInv> getPages() {
        return pages;
    }

    public Inventory getMenu() {
        return pages.get(startingPage).getInventory();
    }

}
