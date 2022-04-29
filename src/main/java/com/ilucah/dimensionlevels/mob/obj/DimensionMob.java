package com.ilucah.dimensionlevels.mob.obj;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.List;

public class DimensionMob {

    private String mobTypeName;
    private int obtainChance;
    private long experience, levelRequired;
    private List<String> messages;
    private Sound receiveSound;
    private String[] title;
    boolean isSendingMessages, isReceivingSound, isSendingTitle;

    private EntityType mob;

    public DimensionMob(String mobName, int chance, long experience, List<String> messages, Sound sound, String[] title, int levelRequired) {
        mob = EntityType.valueOf(mobName);
        mobTypeName = mobName;
        obtainChance = chance;
        this.experience = experience;
        isReceivingSound = false;
        isSendingMessages = false;
        isSendingTitle = false;
        if (messages != null) {
            this.messages = messages;
            isSendingMessages = true;
        }
        if (sound != null) {
            receiveSound = sound;
            isReceivingSound = true;
        }
        if (title != null) {
            this.title = title;
            isSendingTitle = true;
        }
        this.levelRequired = levelRequired;
    }

    public String getMobTypeName() {
        return mobTypeName;
    }

    public void setMobTypeName(String mobTypeName) {
        this.mobTypeName = mobTypeName;
    }

    public int getObtainChance() {
        return obtainChance;
    }

    public void setObtainChance(int obtainChance) {
        this.obtainChance = obtainChance;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Sound getReceiveSound() {
        return receiveSound;
    }

    public void setReceiveSound(Sound receiveSound) {
        this.receiveSound = receiveSound;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public boolean isSendingMessages() {
        return isSendingMessages;
    }

    public void setSendingMessages(boolean sendingMessages) {
        isSendingMessages = sendingMessages;
    }

    public boolean isReceivingSound() {
        return isReceivingSound;
    }

    public void setReceivingSound(boolean receivingSound) {
        isReceivingSound = receivingSound;
    }

    public boolean isSendingTitle() {
        return isSendingTitle;
    }

    public void setSendingTitle(boolean sendingTitle) {
        isSendingTitle = sendingTitle;
    }

    public EntityType getMob() {
        return mob;
    }

    public void setMob(EntityType mob) {
        this.mob = mob;
    }

    public long getLevelRequired() {
        return levelRequired;
    }

    public void setLevelRequired(long level) {
        this.levelRequired = level;
    }
}
