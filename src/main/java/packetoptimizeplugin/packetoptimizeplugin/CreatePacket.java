package packetoptimizeplugin.packetoptimizeplugin;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;

public class CreatePacket {
    public static void spawnRPOParticle(Particle particle , double x, double y, double z, int count, double speed, float r, float g, float b, float scale, double offx, double offy, double offz, World world) {
        new OriginalParticleBuilder(particle)
                .location(world, x, y, z)
                .count(count)
                .extra(speed)
                .color(Color.fromRGB(Math.round(r), Math.round(g), Math.round(b)), scale)
                .offset(offx, offy, offz)
                .spawn();
    }

    public static void spawnRPOParticle(Particle particle , double x, double y, double z, int count, double speed, Material material, double offx, double offy, double offz, World world) {
        new OriginalParticleBuilder(particle)
                .location(world, x, y, z)
                .count(count)
                .extra(speed)
                .data(material.createBlockData())
                .offset(offx, offy, offz)
                .spawn();
    }

    public static void spawnRPOParticle(Particle particle , double x, double y, double z, int count, double speed, double offx, double offy, double offz, World world) {
        new OriginalParticleBuilder(particle)
                .location(world, x, y, z)
                .count(count)
                .extra(speed)
                .offset(offx, offy, offz)
                .spawn();
    }

    public static void spawnRPOParticle(Particle particle , double x, double y, double z, double speed, double offx, double offy, double offz, World world) {
        new OriginalParticleBuilder(particle)
                .location(world, x, y, z)
                .count(1)
                .extra(speed)
                .offset(offx, offy, offz)
                .spawn();
    }

    public static void spawnRPOParticle(Particle particle , double x, double y, double z, double offx, double offy, double offz, World world) {
        new OriginalParticleBuilder(particle)
                .location(world, x, y, z)
                .count(1)
                .extra(0)
                .offset(offx, offy, offz)
                .spawn();
    }
}
