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

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            RyuZUPacketOptimizer.sendPacket(p, new CheckUsingModPacket().encode().array());
        }

//        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_PARTICLES) {
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                PacketContainer packet = event.getPacket();
//                new PacketContainer(PacketType.Play.Client.BLOCK_PLACE).getModifier().writeDefaults();
////                Player p = event.getPlayer();
//
//                int count = packet.getIntegers().read(0);
////                if (usingPlayers.containsKey(p)) {
////                    Queue<ParticleBasePacket> packets = particleQueue.getOrDefault(p, new ArrayDeque<>());
////                    WrappedParticle wrappedparticle = packet.getNewParticles().read(0);
////                    Particle newparticle = wrappedparticle.getParticle();
////
////                    int id = ParticleTypes.ParticleType.valueOf(newparticle.name()).getId();
////                    double x = packet.getDoubles().read(0);
////                    double y = packet.getDoubles().read(1);
////                    double z = packet.getDoubles().read(2);
////                    float offx = packet.getFloat().read(0);
////                    float offy = packet.getFloat().read(1);
////                    float offz = packet.getFloat().read(2);
////                    float speed = packet.getFloat().read(3);
////
////                    PacketOptimizer.optimize(id, x, y, z, offx, offy, offz, speed, count, newparticle, wrappedparticle, packets);
////
////                    particleQueue.put(p, packets);
////                    event.setCancelled(true);
////                } else
//                if (limitcount > 500 && Math.random() >= 0.3) {
//                    event.setCancelled(true);
//                } else {
//                    limitcount++;
//                }
//                if (count == 810) packet.getIntegers().writeSafely(0, 1);
//            }
//        });

//        Bukkit.getScheduler().runTaskTimer(this, () -> limitcount = 0, 0L, 10L);

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
