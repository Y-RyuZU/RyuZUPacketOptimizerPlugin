package packetoptimizeplugin.packetoptimizeplugin;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Original.ParticleOriginalColorPacket;

import java.util.*;

public class OriginalParticleBuilder extends ParticleBuilder {
    public static final List<Particle> originalparticles = Arrays.asList(Particle.FLASH, Particle.FLAME, Particle.END_ROD, Particle.COMPOSTER, Particle.CRIT, Particle.FIREWORKS_SPARK);
//    private Location origin;
//    private static final Map<Location, List<Player>> targetsMap = new HashMap<>();

    public OriginalParticleBuilder(@NotNull Particle particle) {
        super(particle);
    }

    public OriginalParticleBuilder(int i) {
        super(originalparticles.get(i));
    }

    @NotNull
    @Override
    public OriginalParticleBuilder spawn() {
        if (location() == null) return this;
        boolean original = originalparticles.contains(particle());
        boolean hasData = data() != null;
        List<Player> players = new ArrayList<>(receivers() != null ? receivers() : location().getWorld().getNearbyPlayers(location(), 512));
        List<Player> notUsingPlayers = new ArrayList<>();
        List<Player> usingPlayers = new ArrayList<>();
        for(Player p : players) {
            if (RyuZUPacketOptimizer.usingPlayers.containsKey(p))
                usingPlayers.add(p);
             else
                notUsingPlayers.add(p);
        }
        int id = ParticleTypes.getId(particle());
        if (original && hasData && data().getClass().equals(Particle.DustOptions.class)) {
            Particle.DustOptions color = data();
            for (Player p : usingPlayers)
                RyuZUPacketOptimizer.particleQueue.computeIfAbsent(p, up -> new ArrayDeque<>()).add(new ParticleOriginalColorPacket(originalparticles.indexOf(particle()), count(), color.getColor().getRed() / 255f, color.getColor().getGreen() / 255f, color.getColor().getBlue() / 255f, color.getSize(), (float) extra()
                        , new ArrayList<>(Collections.singletonList(location().getX())), new ArrayList<>(Collections.singletonList(location().getY())), new ArrayList<>(Collections.singletonList(location().getZ()))
                        , new ArrayList<>(Collections.singletonList((float) offsetX())), new ArrayList<>(Collections.singletonList((float) offsetY())), new ArrayList<>(Collections.singletonList((float) offsetZ())))
                );
        } else {
            for (Player p : usingPlayers)
                PacketOptimizer.optimize(id, location().getX(), location().getY(), location().getZ(), (float) offsetX(), (float) offsetY(), (float) offsetZ(), (float) extra(), count(), particle(), data(), p);
        }
        location().getWorld().spawnParticle(particle(), receivers(), source(),
                                          location().getX(), location().getY(), location().getZ(),
                                          count(), offsetX(), offsetY(), offsetZ(), extra(), original ? null : data(), force()
        );
//        location().getWorld().spawnParticle(
//                particle(), players, source(),
//                location().getX(), location().getY(), location().getZ(),
//                count(), offsetX(), offsetY(), offsetZ(), extra(), data(), true
//        );
        return this;
    }

    @NotNull
    @Override
    public ParticleBuilder color(@Nullable Color color, float size) {
        if (color == null) {
            if (data() instanceof Particle.DustOptions)
                return this.data(null);
            else
                return this;
        }

        return data(new Particle.DustOptions(color, size));
    }

//    @NotNull
//    public ParticleBuilder location(@NotNull Location location) {
//        super.location(location);
//        if(origin == null) origin = location.clone();
//        return this;
//    }
//
//    @NotNull
//    public ParticleBuilder location(@NotNull World world, double x, double y, double z) {
//        Location location = new Location(world, x, y, z);
//        super.location(location);
//        if(origin == null) origin = location.clone();
//        return this;
//    }
//
//    @Nullable
//    public Location origin() {
//        return origin;
//    }
}
