package packetoptimizeplugin.packetoptimizeplugin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import packetoptimizeplugin.packetoptimizeplugin.Packets.CheckUsingModPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleBasePacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleCompressionPacket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class PacketListener implements PluginMessageListener, Listener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        ByteBuf buf = Unpooled.wrappedBuffer(message);
        byte id = buf.readByte();
        switch (id) {
            case 0:
                if (!RyuZUPacketOptimizer.usingPlayers.containsKey(player)) {
                    int version = buf.readInt();
                    if (version == 6) RyuZUPacketOptimizer.usingPlayers.put(player, version);
                    else player.sendMessage("======================================\n" +
                                                    "【お知らせ】\n" +
                                                    "・使用されているMODのバージョンが古いです。\n" +
                                                    "・更新よろしくお願いします。\n" +
                                                    "https://github.com/azisaba/RyuZUPacketOptimizerMod/releases/latest/download/RyuZUPacketOptimizer.jar\n" +
                                                    "=============================");
                }
                break;
        }
    }

    @EventHandler
    public void onQuite(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        RyuZUPacketOptimizer.usingPlayers.remove(p);
        RyuZUPacketOptimizer.particleQueue.remove(p);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskTimer(RyuZUPacketOptimizer.plugin, (task) -> {
            RyuZUPacketOptimizer.sendPacket(p, new CheckUsingModPacket().encode().array());
            task.cancel();

            Bukkit.getScheduler().runTaskTimer(RyuZUPacketOptimizer.plugin, (task2) -> {
                if (!RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                    p.sendMessage("======================================\n" +
                                          "【お知らせ】\n" +
                                          "・使用すると現在欠けているparticleがすべて表示されるようになるmodです\n" +
                                          "・パケット削減を行うとPingが安定するので、一人でも使用者が増えると助かります\n" +
                                          "ダウンロードはこちらから↓\n" +
                                          "https://github.com/azisaba/RyuZUPacketOptimizerMod/releases/latest/download/RyuZUPacketOptimizer.jar\n" +
                                          "=============================");
                }
                task2.cancel();
            }, 20 * 2L, 0L);

        }, 20 * 5L, 0L);
    }
}
