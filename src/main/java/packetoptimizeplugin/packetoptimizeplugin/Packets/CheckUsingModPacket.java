package packetoptimizeplugin.packetoptimizeplugin.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CheckUsingModPacket {
    public static final byte ID = 17;

    public byte[] encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);

        return buf.array();
    }

    public static CheckUsingModPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != CheckUsingModPacket.ID) throw new UnsupportedOperationException();

        return new CheckUsingModPacket();
    }
}
