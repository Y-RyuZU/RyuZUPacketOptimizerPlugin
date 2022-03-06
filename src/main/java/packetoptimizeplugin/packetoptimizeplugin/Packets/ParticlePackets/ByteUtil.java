package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteUtil {
    public static int getByte(byte[] packet) {
        return Unpooled.wrappedBuffer(packet).writerIndex();
    }

    public static int getByte(ByteBuf packet) {
        return packet.writerIndex();
    }
}
