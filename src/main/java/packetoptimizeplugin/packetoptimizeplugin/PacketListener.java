package packetoptimizeplugin.packetoptimizeplugin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class PacketListener implements PluginMessageListener,Listener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        ByteBuf buf = Unpooled.wrappedBuffer(message);
        byte id = buf.readByte();
        switch (id) {
            case 0:
                if(!RyuZUPacketOptimizer.usingPlayers.containsKey(player)) RyuZUPacketOptimizer.usingPlayers.put(player , buf.readInt());
                break;
            case 1:

                break;
        }
    }

    @EventHandler
    public void onQuite(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        RyuZUPacketOptimizer.usingPlayers.remove(p);
        RyuZUPacketOptimizer.particleQueue.remove(p);
    }
}
