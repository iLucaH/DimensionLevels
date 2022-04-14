package com.ilucah.dimensionlevels.levels.model;

import org.bukkit.Sound;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;

public class DimensionLevel {

    private long level, experienceRequired;
    private List<String> messages, commands;
    private Sound sound;
    private String[] title;
    private ParticleEffect particle;
    private boolean particles, isSendingTitle, isPlayingSound, isReceivingMessages;
    private LevelRequirement requirement;

    public DimensionLevel(long level, long experienceRequired, List<String> messages, Sound sound, String[] title, ParticleEffect particle, LevelRequirement requirement, List<String> commands) {
        this.level = level;
        this.experienceRequired = experienceRequired;
        isSendingTitle = false;
        isPlayingSound = false;
        isReceivingMessages = false;
        particles = false;
        if (messages != null) {
            this.messages = messages;
            isReceivingMessages = true;
        }
        if (sound != null) {
            this.sound = sound;
            this.isPlayingSound = true;
        }
        if (title != null) {
            this.title = title;
            this.isSendingTitle = true;
        }
        if (particle != null) {
            this.particle = particle;
            this.particles = true;
        }
        this.requirement = requirement;
        this.commands = commands;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(long experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public boolean isUsingParticles() {
        return particles;
    }

    public void setUsingParticles(boolean particles) {
        this.particles = particles;
    }

    public boolean isSendingTitle() {
        return isSendingTitle;
    }

    public void setSendingTitle(boolean sendingTitle) {
        isSendingTitle = sendingTitle;
    }

    public boolean isPlayingSound() {
        return isPlayingSound;
    }

    public void setPlayingSound(boolean playingSound) {
        isPlayingSound = playingSound;
    }

    public boolean isReceivingMessages() {
        return isReceivingMessages;
    }

    public void setReceivingMessages(boolean receivingMessages) {
        isReceivingMessages = receivingMessages;
    }

    public LevelRequirement getRequirement() {
        return requirement;
    }

    public void setRequirement(LevelRequirement requirement) {
        this.requirement = requirement;
    }

    public List<String> getCommands() {
        return commands;
    }

    public ParticleEffect getParticle() {
        return particle;
    }

    public void setParticle(ParticleEffect particle) {
        this.particle = particle;
    }
}
