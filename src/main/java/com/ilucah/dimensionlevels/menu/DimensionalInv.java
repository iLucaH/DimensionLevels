package com.ilucah.dimensionlevels.menu;

import org.bukkit.inventory.Inventory;

public class DimensionalInv {

    private int page;
    private Inventory inventory;

    public DimensionalInv(int page, Inventory inventory) {
        this.page = page;
        this.inventory = inventory;
    }

    public int getPage() { return page; }

    public Inventory getInventory() { return inventory; }
}
