package packetoptimizeplugin.packetoptimizeplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedParticle;
import com.github.ryuzu.ryuzucommandsgenerator.RyuZUCommandsGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import packetoptimizeplugin.packetoptimizeplugin.Packets.CheckUsingModPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.*;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force.ParticleForceColorPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force.ParticleForcePacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleBlockPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleFallingDustPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleItemPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Offset.*;

import java.util.*;
import java.util.stream.Collectors;

public final class RyuZUPacketOptimizer extends JavaPlugin {
    public PacketListener listener;
    public static HashMap<Player, Integer> usingPlayers = new HashMap<>();
    public static HashMap<Player, Queue<ParticleBasePacket>> particleQueue = new HashMap<>();
    public static long limitcount;
    public static RyuZUPacketOptimizer plugin;
    public static RyuZUCommandsGenerator generator;

    @Override
    public void onEnable() {
        plugin = this;
        listener = new PacketListener();
        Commands.implement();
        generator = new RyuZUCommandsGenerator(this, ChatColor.RED + "ぽまえ権限ないやろ");

        getServer().getMessenger().registerOutgoingPluginChannel(this, "ryuzupacketoptimizer:main");
        getServer().getMessenger().registerIncomingPluginChannel(this, "ryuzupacketoptimizer:main", listener);
        getServer().getPluginManager().registerEvents(listener, this);

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            RyuZUPacketOptimizer.sendPacket(p, new CheckUsingModPacket().encode().array());
        }

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_PARTICLES) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player p = event.getPlayer();

                int count = packet.getIntegers().read(0);
                if (usingPlayers.containsKey(p)) {
                    Queue<ParticleBasePacket> packets = particleQueue.getOrDefault(p, new ArrayDeque<>());
                    WrappedParticle wrappedparticle = packet.getNewParticles().read(0);
                    Particle newparticle = wrappedparticle.getParticle();

                    int id = ParticleTypes.ParticleType.valueOf(newparticle.name()).getId();
                    double x = packet.getDoubles().read(0);
                    double y = packet.getDoubles().read(1);
                    double z = packet.getDoubles().read(2);
                    float offx = packet.getFloat().read(0);
                    float offy = packet.getFloat().read(1);
                    float offz = packet.getFloat().read(2);
                    float speed = packet.getFloat().read(3);
                    boolean hit = false;

                    switch (newparticle) {
                        case REDSTONE:
                            Particle.DustOptions options = (Particle.DustOptions) wrappedparticle.getData();
                            float r = (float) options.getColor().getRed() / 255f;
                            float g = (float) options.getColor().getGreen() / 255f;
                            float b = (float) options.getColor().getBlue() / 255f;
                            float scale = options.getSize();

                            if (count == 810) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleForceColorPacket.class.toString())) {
                                        ParticleForceColorPacket vec = (ParticleForceColorPacket) base;
                                        if (vec.isSimilar(r, g, b, scale)) {
                                            vec.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleForceColorPacket(id, r, g, b, scale
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                                    );
                                }
                            } else if (offx != 0 || offy != 0 || offz != 0) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleOffsetColorPacket.class.toString())) {
                                        ParticleOffsetColorPacket vec = (ParticleOffsetColorPacket) base;
                                        if (vec.isSimilar(r, g, b, scale)) {
                                            vec.addLocation(x, y, z, offx, offy, offz);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleOffsetColorPacket(id, count, r, g, b, scale
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                                    );
                                }
                            } else {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleColorPacket.class.toString())) {
                                        ParticleColorPacket vec = (ParticleColorPacket) base;
                                        if (vec.isSimilar(r, g, b, scale)) {
                                            vec.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleColorPacket(id, r, g, b, scale
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))));
                                }
                            }
                            break;
                        case BLOCK_CRACK:
                            WrappedBlockData block = (WrappedBlockData) wrappedparticle.getData();
                            int blockid = ParticleTypes.MaterialTypes.valueOf(block.getType().toString()).getId();

                            if (offx != 0 || offy != 0 || offz != 0) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleOffsetBlockPacket.class.toString())) {
                                        ParticleOffsetBlockPacket vec = (ParticleOffsetBlockPacket) base;
                                        if (vec.isSimilar(id, count, speed, blockid)) {
                                            vec.addLocation(x, y, z, offx, offy, offz);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleOffsetBlockPacket(id, count, speed, blockid
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                                    );
                                }
                            } else {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleBlockPacket.class.toString())) {
                                        ParticleBlockPacket vec = (ParticleBlockPacket) base;
                                        if (vec.isSimilar(id, count, speed, blockid)) {
                                            vec.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleBlockPacket(id, count, speed, blockid
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                                    );
                                }
                            }
                            break;
                        case FALLING_DUST:
                            WrappedBlockData fallingdust = (WrappedBlockData) wrappedparticle.getData();
                            int fallingdustid = ParticleTypes.MaterialTypes.valueOf(fallingdust.getType().toString()).getId();
                            if (offx != 0 || offy != 0 || offz != 0) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleOffsetFallingDustPacket.class.toString())) {
                                        ParticleOffsetFallingDustPacket vec = (ParticleOffsetFallingDustPacket) base;
                                        if (vec.isSimilar(id, count, speed, fallingdustid)) {
                                            vec.addLocation(x, y, z, offx, offy, offz);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleOffsetFallingDustPacket(id, count, speed, fallingdustid
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                                    );
                                }
                            } else {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleFallingDustPacket.class.toString())) {
                                        ParticleFallingDustPacket vec = (ParticleFallingDustPacket) base;
                                        if (vec.isSimilar(id, count, speed, fallingdustid)) {
                                            vec.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleFallingDustPacket(id, count, speed, fallingdustid
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                                    );
                                }
                            }
                            break;
                        case ITEM_CRACK:
                            ItemStack item = (ItemStack) wrappedparticle.getData();
                            int itemid = ParticleTypes.MaterialTypes.valueOf(item.getType().toString()).getId();
                            if (offx != 0 || offy != 0 || offz != 0) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleOffsetItemPacket.class.toString())) {
                                        ParticleOffsetItemPacket vec = (ParticleOffsetItemPacket) base;
                                        if (vec.isSimilar(id, count, speed, itemid)) {
                                            vec.addLocation(x, y, z, offx, offy, offz);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleOffsetItemPacket(id, count, speed, itemid
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                                    );
                                }
                            } else {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleItemPacket.class.toString())) {
                                        ParticleItemPacket vec = (ParticleItemPacket) base;
                                        if (vec.isSimilar(id, count, speed, itemid)) {
                                            vec.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleItemPacket(id, count, speed, itemid
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                                    );
                                }
                            }
                            break;
                        default:
                            if (count == 0) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleVectorPacket.class.toString())) {
                                        ParticleVectorPacket vec = (ParticleVectorPacket) base;
                                        if (vec.isSimilar(id, speed)) {
                                            vec.addLocation(x, y, z, offx, offy, offz);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleVectorPacket(id, speed
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                                    );
                                }
                            } else if (count == 810) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleForcePacket.class.toString())) {
                                        ParticleForcePacket forve = (ParticleForcePacket) base;
                                        if (forve.isSimilar(id)) {
                                            forve.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleForcePacket(id
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                                    );
                                }
                            } else if (offx != 0 || offy != 0 || offz != 0) {
                                try {
                                    for (ParticleBasePacket base : packets) {
                                        if (base.getClass().toString().equals(ParticleOffsetPacket.class.toString())) {
                                            ParticleOffsetPacket vec = (ParticleOffsetPacket) base;
                                            if (vec.isSimilar(id, count, speed)) {
                                                vec.addLocation(x, y, z, offx, offy, offz);
                                                hit = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!hit) {
                                        packets.add(
                                                new ParticleOffsetPacket(id, count, speed
                                                        , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                                        , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                                        );
                                    }
                                } catch (Exception error) {
                                    error.printStackTrace();
                                }
                            } else if (count > 1) {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleCountPacket.class.toString())) {
                                        ParticleCountPacket vec = (ParticleCountPacket) base;
                                        if (vec.isSimilar(id, count, speed)) {
                                            vec.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(
                                            new ParticleCountPacket(id, count, speed
                                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                                    );
                                }
                            } else {
                                for (ParticleBasePacket base : packets) {
                                    if (base.getClass().toString().equals(ParticleBasePacket.class.toString())) {
                                        if (base.isSimilar(id)) {
                                            base.addLocation(x, y, z);
                                            hit = true;
                                            break;
                                        }
                                    }
                                }
                                if (!hit) {
                                    packets.add(new ParticleBasePacket(id
                                            , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                                    );
                                }
                            }
                    }
                    particleQueue.put(p, packets);
                    event.setCancelled(true);
                } else if (limitcount > 500 && Math.random() >= 0.3) {
                    event.setCancelled(true);
                } else {
                    limitcount++;
                }
                if (count == 810) packet.getIntegers().writeSafely(0, 1);
            }
        });

        Bukkit.getScheduler().runTaskTimer(this, () -> limitcount = 0, 0L, 10L);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : particleQueue.keySet()) {
                Queue<ParticleBasePacket> queue = particleQueue.get(p);

                List<ParticleCompressionPacket> compressionPackets = new ArrayList<>();
                packet:
                while (queue.size() > 0) {
                    if (compressionPackets.size() == 0) compressionPackets.add(new ParticleCompressionPacket());
                    for (ParticleCompressionPacket compacket : compressionPackets) {
                        if (compacket.ableAdd(queue.peek().encode().copy())) {
                            compacket.addPacket(queue.poll().encode().copy());
                            continue packet;
                        }
                    }
                    compressionPackets.add(new ParticleCompressionPacket(new ArrayList<>(Collections.singletonList(queue.poll().encode()))));
                }

                for (ParticleCompressionPacket compacket : compressionPackets) {
                    sendPacket(p, compacket.encode().array());
                }
                particleQueue.put(p, queue);
            }
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }

    public static void sendParticlePacket(Player p, ParticleBasePacket packet) {
        p.sendPluginMessage(plugin, "ryuzupacketoptimizer:main", packet.encode().array());
    }

    public static void sendPacket(Player p, byte[] packet) {
        p.sendPluginMessage(plugin, "ryuzupacketoptimizer:main", packet);
    }
}
