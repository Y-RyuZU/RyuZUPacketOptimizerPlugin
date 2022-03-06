package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleColorPacket;

import java.util.ArrayList;
import java.util.List;

public class ParticleForceColorPacket extends ParticleColorPacket {
    public static final byte ID = 14;

    public ParticleForceColorPacket(int type, float r, float g, float b, float scale) {
        super(type, r, b, g, scale);
    }

    public ParticleForceColorPacket(int type, float r, float g, float b, float scale, List<Double> x, List<Double> y, List<Double> z) {
        super(type, r, b, g, scale, x, y, z);
    }

    public static ParticleForceColorPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != ParticleForceColorPacket.ID) throw new UnsupportedOperationException();

        int type = buf.readInt();
        float r = buf.readFloat();
        float g = buf.readFloat();
        float b = buf.readFloat();
        float scale = buf.readFloat();
        int size = buf.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buf.readDouble());
            y.add(buf.readDouble());
            z.add(buf.readDouble());
        }
        return new ParticleForceColorPacket(type, r, g, b, scale, x, y, z);
    }
}
