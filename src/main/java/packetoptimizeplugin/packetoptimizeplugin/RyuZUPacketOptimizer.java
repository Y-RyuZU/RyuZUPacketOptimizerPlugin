package packetoptimizeplugin.packetoptimizeplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedParticle;
import com.github.ryuzu.ryuzucommandgenerator.RyuZUCommandsGenerator;
import com.sun.jmx.remote.internal.ArrayQueue;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.*;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force.ParticleForceColorPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force.ParticleForcePacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleBlockPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleFallingDustPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleItemPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Offset.*;
import packetoptimizeplugin.packetoptimizeplugin.Packets.SetParticleDrawingRatePacket;

import java.util.*;
import java.util.function.Supplier;
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
        generator = new RyuZUCommandsGenerator(this, ChatColor.RED + "ぽまえ権限ないやろ");

        getServer().getMessenger().registerOutgoingPluginChannel(this, "ryuzupacketoptimizer:main");
        getServer().getMessenger().registerIncomingPluginChannel(this, "ryuzupacketoptimizer:main", listener);
        getServer().getPluginManager().registerEvents(listener, this);

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
                } else if (limitcount > 400 && Math.random() >= 0.1) {
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

                Queue<ParticleBasePacket> packetqueue = new ArrayDeque<>(queue.stream().filter(q -> q instanceof ParticleColorPacket || q instanceof ParticleVectorPacket).collect(Collectors.toList()));
                queue.removeAll(packetqueue);

                List<ParticleCompressionPacket> compressionPackets = new ArrayList<>();
                packet:
                while (queue.size() > 0) {
                    if (compressionPackets.size() == 0) compressionPackets.add(new ParticleCompressionPacket());
                    for (ParticleCompressionPacket compacket : compressionPackets) {
                        if (compacket.ableAdd(queue.peek().encode().copy())) {
                            compacket.addPacket(queue.poll().encode().copy());
                            break packet;
                        }
                    }
                    compressionPackets.add(new ParticleCompressionPacket(Collections.singletonList(queue.poll().encode())));
                }

                for (ParticleCompressionPacket compacket : compressionPackets) {
                    sendPacket(p, compacket.encode().array());
                }

                for (ParticleBasePacket packet : packetqueue) {
                    sendParticlePacket(p, packet);
                }
                particleQueue.put(p, queue);
            }
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }

    public void sendParticlePacket(Player p, ParticleBasePacket packet) {
        p.sendPluginMessage(this, "ryuzupacketoptimizer:main", packet.encode().array());
    }

    public void sendPacket(Player p, byte[] packet) {
        p.sendPluginMessage(this, "ryuzupacketoptimizer:main", packet);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rpo")) {
            if (args.length <= 0) {
                sender.sendMessage(ChatColor.RED + "/" + label + " [list/particledrawingrate/remove]");
                return true;
            }
            if (args[0].equals("list") || args[0].equals("l")) {
                if (!sender.hasPermission("rpo.op")) {
                    sender.sendMessage(ChatColor.RED + "ぽまえ権限ないやろ");
                    return true;
                }
                sender.sendMessage(ChatColor.GREEN + "使用者一覧:");
                for (Player p : usingPlayers.keySet()) {
                    sender.sendMessage(ChatColor.GREEN + p.getName() + ": " + usingPlayers.get(p));
                }
            }

            if (args[0].equals("remove")) {
                if (!sender.hasPermission("rpo.op")) {
                    sender.sendMessage(ChatColor.RED + "ぽまえ権限ないやろ");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーしか実行できません");
                    return true;
                }
                Player p = (Player) sender;
                usingPlayers.remove(p);
                particleQueue.remove(p);
                sender.sendMessage(ChatColor.GREEN + "使用者リストから除外しました");
            }

            if (args[0].equals("particledrawingrate")) {
                if (args.length <= 1) {
                    sender.sendMessage(ChatColor.RED + "/" + label + " particledrawingrate [0～100]");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーしか実行できません");
                    return true;
                }
                Player p = (Player) sender;
                if (!usingPlayers.containsKey(p)) {
                    sender.sendMessage(ChatColor.RED + "RyuZUPacketOptimizerの使用者以外は使用できません");
                    return true;
                }
                int i;
                try {
                    i = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "0～100の値を使用してください");
                    return true;
                }
                if (0 <= i && i <= 100) {
                    sendPacket(p, new SetParticleDrawingRatePacket(i).encode());
                    sender.sendMessage(ChatColor.GREEN + "パーティクル描画率を適用しました");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "0～100の値を使用してください");
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("rpo")) {
            if (args.length == 1) {
                if (sender.hasPermission("rpc.op")) {
                    list.addAll(Arrays.asList("list"));
                    if (sender instanceof Player && usingPlayers.containsKey((Player) sender)) {
                        list.addAll(Arrays.asList("remove"));
                    }
                }

                if (sender instanceof Player && usingPlayers.containsKey((Player) sender)) {
                    list.addAll(Arrays.asList("particledrawingrate"));
                }
            }
        }
        return list;
    }
}
