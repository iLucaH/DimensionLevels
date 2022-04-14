package com.ilucah.dimensionlevels.levels.model;

import com.ilucah.dimensionlevels.utils.xutils.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;

public class LevelRequirement {

    private int playTime;
    private long coins;
    private double money;
    private HashMap<Material, Integer> blockRequirement;
    private HashMap<EntityType, Integer> mobRequirement;

    public LevelRequirement(int playTime, List<String> blocksMined, List<String> mobsKilled, long coins, double money) {
        this.playTime = playTime;
        this.coins = coins;
        this.money = money;

        blockRequirement = new HashMap<>();
        mobRequirement = new HashMap<>();
        for (String string : blocksMined) {
            Material material = XMaterial.valueOf(string.split(" ")[0].toUpperCase()).parseMaterial();
            int amount = Integer.valueOf(string.split(" ")[1]);
            blockRequirement.put(material, amount);
        }
        for (String string : mobsKilled) {
            EntityType entity = EntityType.valueOf(string.split(" ")[0]);
            int amount = Integer.valueOf(string.split(" ")[1]);
            mobRequirement.put(entity, amount);
        }
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getBlocksBrokenRequirement(Material material) {
        return blockRequirement.get(material);
    }

    public void setBlocKsBrokenRequirement(HashMap<Material, Integer> blocKsBrokenRequirement) {
        blockRequirement = blocKsBrokenRequirement;
    }

    public HashMap<Material, Integer> getBlockRequirement() {
        return blockRequirement;
    }

    public int getMobsKilledRequirement(EntityType entityType) {
        return mobRequirement.get(entityType);
    }

    public HashMap<EntityType, Integer> getMobsKilledRequirement() {
        return mobRequirement;
    }

    public void setMobsKilled(HashMap<EntityType, Integer> mobsKilled) {
        this.mobRequirement = mobsKilled;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
