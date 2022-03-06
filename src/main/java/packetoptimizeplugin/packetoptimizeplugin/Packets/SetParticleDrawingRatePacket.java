package packetoptimizeplugin.packetoptimizeplugin.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SetParticleDrawingRatePacket {
    public static final byte ID = 15;

    protected final int value;

    public SetParticleDrawingRatePacket(int value) {
        this.value = value;
    }

    public byte[] encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(value);

        return buf.array();
    }

    public static SetParticleDrawingRatePacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != SetParticleDrawingRatePacket.ID) throw new UnsupportedOperationException();

        int value = buf.readInt();
        return new SetParticleDrawingRatePacket(value);
    }
}
