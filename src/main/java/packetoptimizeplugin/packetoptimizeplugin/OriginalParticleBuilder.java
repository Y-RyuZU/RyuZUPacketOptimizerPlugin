package packetoptimizeplugin.packetoptimizeplugin;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Original.ParticleOriginalColorPacket;

import java.util.*;

public class OriginalParticleBuilder extends ParticleBuilder {
    public static final List<Particle> originalparticles = Arrays.asList(Particle.FLASH, Particle.FLAME, Particle.END_ROD, Particle.COMPOSTER, Particle.CRIT, Particle.FIREWORKS_SPARK);

    public OriginalParticleBuilder(@NotNull Particle particle) {
        super(particle);
    }

    public OriginalParticleBuilder(int i) {
        super(originalparticles.get(i));
    }

    @NotNull
    @Override
    public ParticleBuilder spawn() {
        if (location() == null) return this;
        if (originalparticles.contains(particle()) && (data() != null && data().getClass().equals(Particle.DustOptions.class))) {
            int type = type = originalparticles.indexOf(particle());
            Particle.DustOptions color = data();

            Collection<Player> players = receivers() != null ? receivers() : location().getWorld().getNearbyPlayers(location(), 512);
            Collection<Player> usingplayers = new ArrayList<>();
            for (Player p : players) {
                if (RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                    if (RyuZUPacketOptimizer.particleQueue.get(p) == null)
                        RyuZUPacketOptimizer.particleQueue.put(p, new ArrayDeque<>());
                    RyuZUPacketOptimizer.particleQueue.get(p).add(new ParticleOriginalColorPacket(type, count(), color.getColor().getRed() / 255f, color.getColor().getGreen() / 255f, color.getColor().getBlue() / 255f, color.getSize(), new Double(extra()).floatValue()
                            , new ArrayList<>(Collections.singletonList(location().getX())), new ArrayList<>(Collections.singletonList(location().getY())), new ArrayList<>(Collections.singletonList(location().getZ()))
                            , new ArrayList<>(Collections.singletonList(new Double(offsetX()).floatValue())), new ArrayList<>(Collections.singletonList(new Double(offsetY()).floatValue())), new ArrayList<>(Collections.singletonList(new Double(offsetZ()).floatValue())))
                    );
                    usingplayers.add(p);
                }
            }
            players.removeAll(usingplayers);
            particle().builder().location(location()).count(count()).extra(extra()).receivers(players).offset(offsetX(), offsetY(), offsetZ()).spawn();
        } else
            location().getWorld().spawnParticle(
                    particle(), receivers(), source(),
                    location().getX(), location().getY(), location().getZ(),
                    count(), offsetX(), offsetY(), offsetZ(), extra(), data(), true
            );
        return this;
    }

    @NotNull
    @Override
    public ParticleBuilder color(@Nullable Color color, float size) {
        if (color == null) {
            if (data() instanceof Particle.DustOptions) {
                return data(null);
            } else {
                return this;
            }
        }

        return data(new Particle.DustOptions(color, size));
    }
}
