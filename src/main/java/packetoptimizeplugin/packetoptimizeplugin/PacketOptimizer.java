package packetoptimizeplugin.packetoptimizeplugin;

import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force.ParticleForceColorPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force.ParticleForcePacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleBlockPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleFallingDustPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material.ParticleItemPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Offset.*;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleBasePacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleColorPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleCountPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleVectorPacket;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

public class PacketOptimizer {

    public static void optimize(int id, double x, double y, double z, float offx, float offy, float offz, float speed, int count, Particle newparticle, Object data, Player p) {
        optimize(id, x, y, z, offx, offy, offz, speed, count, newparticle, data, RyuZUPacketOptimizer.particleQueue.computeIfAbsent(p, up -> new ArrayDeque<>()));
    }

    public static void optimize(int id, double x, double y, double z, float offx, float offy, float offz, float speed, int count, Particle newparticle, Object data, Queue<ParticleBasePacket> packets) {
        boolean hit = false;

        switch (newparticle) {
            case REDSTONE -> {
                Particle.DustOptions options = (Particle.DustOptions) data;
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
            }
            case BLOCK_CRACK -> {
                WrappedBlockData block = (WrappedBlockData) data;
                int blockid = ParticleTypes.MaterialTypes.valueOf(block.getType().toString()).getId();
                int blockdata = block.getData();
                if (offx != 0 || offy != 0 || offz != 0) {
                    for (ParticleBasePacket base : packets) {
                        if (base.getClass().toString().equals(ParticleOffsetBlockPacket.class.toString())) {
                            ParticleOffsetBlockPacket vec = (ParticleOffsetBlockPacket) base;
                            if (vec.isSimilar(id, count, speed, blockid, blockdata)) {
                                vec.addLocation(x, y, z, offx, offy, offz);
                                hit = true;
                                break;
                            }
                        }
                    }


                    if (!hit) {
                        packets.add(
                                new ParticleOffsetBlockPacket(id, count, speed, blockid, blockdata
                                        , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                        , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                        );
                    }
                } else {
                    for (ParticleBasePacket base : packets) {
                        if (base.getClass().toString().equals(ParticleBlockPacket.class.toString())) {
                            ParticleBlockPacket vec = (ParticleBlockPacket) base;
                            if (vec.isSimilar(id, count, speed, blockid, blockdata)) {
                                vec.addLocation(x, y, z);
                                hit = true;
                                break;
                            }
                        }
                    }
                    if (!hit) {
                        packets.add(
                                new ParticleBlockPacket(id, count, speed, blockid, blockdata
                                        , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                        );
                    }
                }
            }
            case FALLING_DUST -> {
                WrappedBlockData fallingdust = (WrappedBlockData) data;
                int fallingdustid = ParticleTypes.MaterialTypes.valueOf(fallingdust.getType().toString()).getId();
                int fallingdustdata = fallingdust.getData();
                if (offx != 0 || offy != 0 || offz != 0) {
                    for (ParticleBasePacket base : packets) {
                        if (base.getClass().toString().equals(ParticleOffsetFallingDustPacket.class.toString())) {
                            ParticleOffsetFallingDustPacket vec = (ParticleOffsetFallingDustPacket) base;
                            if (vec.isSimilar(id, count, speed, fallingdustid, fallingdustdata)) {
                                vec.addLocation(x, y, z, offx, offy, offz);
                                hit = true;
                                break;
                            }
                        }
                    }
                    if (!hit) {
                        packets.add(
                                new ParticleOffsetFallingDustPacket(id, count, speed, fallingdustid, fallingdustdata
                                        , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                        , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                        );
                    }
                } else {
                    for (ParticleBasePacket base : packets) {
                        if (base.getClass().toString().equals(ParticleFallingDustPacket.class.toString())) {
                            ParticleFallingDustPacket vec = (ParticleFallingDustPacket) base;
                            if (vec.isSimilar(id, count, speed, fallingdustid, fallingdustdata)) {
                                vec.addLocation(x, y, z);
                                hit = true;
                                break;
                            }
                        }
                    }
                    if (!hit) {
                        packets.add(
                                new ParticleFallingDustPacket(id, count, speed, fallingdustid, fallingdustdata
                                        , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                        );
                    }
                }
            }
            case ITEM_CRACK -> {
                ItemStack item = (ItemStack) data;
                int itemid = ParticleTypes.MaterialTypes.valueOf(item.getType().toString()).getId();
                int itemdata = item.getItemMeta().getCustomModelData();
                if (offx != 0 || offy != 0 || offz != 0) {
                    for (ParticleBasePacket base : packets) {
                        if (base.getClass().toString().equals(ParticleOffsetItemPacket.class.toString())) {
                            ParticleOffsetItemPacket vec = (ParticleOffsetItemPacket) base;
                            if (vec.isSimilar(id, count, speed, itemid, itemdata)) {
                                vec.addLocation(x, y, z, offx, offy, offz);
                                hit = true;
                                break;
                            }
                        }
                    }
                    if (!hit) {
                        packets.add(
                                new ParticleOffsetItemPacket(id, count, speed, itemid, itemdata
                                        , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                        , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                        );
                    }
                } else {
                    for (ParticleBasePacket base : packets) {
                        if (base.getClass().toString().equals(ParticleItemPacket.class.toString())) {
                            ParticleItemPacket vec = (ParticleItemPacket) base;
                            if (vec.isSimilar(id, count, speed, itemid, itemdata)) {
                                vec.addLocation(x, y, z);
                                hit = true;
                                break;
                            }
                        }
                    }
                    if (!hit) {
                        packets.add(
                                new ParticleItemPacket(id, count, speed, itemid, itemdata
                                        , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z)))
                        );
                    }
                }
            }
            default -> {
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
        }
    }
}
