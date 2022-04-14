package com.ilucah.dimensionlevels.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleEffect;

public class ParticleAnimation {

    public static void frostLord(final Plugin plugin, final Player player, ParticleEffect particle) {
        new BukkitRunnable() {
            double t = 0;
            double pi = Math.PI;

            public void run(){
                t += pi / 16;
                Location loc = player.getLocation();

                for(double phi = 0; phi <= 2 * pi; phi += pi / 2) {
                    double x = 0.3 * (4 * pi - t) * Math.cos(t + phi);
                    double y = 0.2 * t;
                    double z = 0.3 * (4 * pi - t) * Math.sin(t + phi);
                    loc.add(x,y,z);
                    particle.display(loc, player);
                    loc.subtract(x,y,z);


                    if(t >= 4 * pi){
                        this.cancel();
                        loc.add(x,y,z);
                        particle.display(loc, player);
                        loc.subtract(x,y,z);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public static void frostLord(final Plugin plugin, final Player player) {
        new BukkitRunnable() {
            double t = 0;
            double pi = Math.PI;

            public void run(){
                t += pi / 16;
                Location loc = player.getLocation();

                for(double phi = 0; phi <= 2 * pi; phi += pi / 2) {
                    double x = 0.3 * (4 * pi - t) * Math.cos(t + phi);
                    double y = 0.2 * t;
                    double z = 0.3 * (4 * pi - t) * Math.sin(t + phi);
                    loc.add(x,y,z);
                    ParticleEffect.SPELL_WITCH.display(loc, player);
                    loc.subtract(x,y,z);


                    if(t >= 4 * pi){
                        this.cancel();
                        loc.add(x,y,z);
                        ParticleEffect.SPELL_WITCH.display(loc, player);
                        loc.subtract(x,y,z);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

}
