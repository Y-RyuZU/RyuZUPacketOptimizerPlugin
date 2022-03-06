package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleCompressionPacket {
    public static final byte ID = 16;

    protected final List<ByteBuf> packets;

    public ParticleCompressionPacket() {
        this.packets = new ArrayList<>();
    }

    public ParticleCompressionPacket(List<ByteBuf> packets) {
        this.packets = packets;
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(packets.size());
        for(ByteBuf packet : packets) {
            buf.writeInt(packet.copy().readableBytes());
            buf.writeBytes(packet.copy());
        }
        return buf;
    }

    public static ParticleCompressionPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != ParticleCompressionPacket.ID) throw new UnsupportedOperationException();

        int size = buf.readInt();
        List<ByteBuf> packets = new ArrayList<>();
        for(int i = 0 ; i < size ; i++) {
            int length = buf.readInt();
            packets.add(buf.readBytes(length));
        }
        return new ParticleCompressionPacket(packets);
    }

    public void addPacket(byte[] packet) {
        this.packets.add(Unpooled.wrappedBuffer(packet));
    }

    public void addPacket(ByteBuf packet) {
        this.packets.add(packet);
    }

    public int getSize() {
        return packets.stream().mapToInt(ByteUtil::getByte).sum();
    }

    public boolean ableAdd(byte[] packet) {
        return getSize() + ByteUtil.getByte(packet) <= 1000;
    }

    public boolean ableAdd(ByteBuf packet) {
        return getSize() + ByteUtil.getByte(packet) <= 1000;
    }
}
