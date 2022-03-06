package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class ParticleColorPacket extends ParticleBasePacket {
    public static final byte ID = 3;

    protected final float r;
    protected final float g;
    protected final float b;
    protected final float scale;


    public ParticleColorPacket(int type, float r, float g, float b, float scale) {
        super(type);
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
    }

    public ParticleColorPacket(int type, float r, float g, float b, float scale, List<Double> x, List<Double> y, List<Double> z) {
        super(type, x, y, z);
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
    }

    public boolean isSimilar(float r, float g, float b, float scale) {
        return this.r == r && this.g == g && this.b == b && this.scale == scale && 25 + this.x.size() * 24 + 24 < 1000;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(scale);
        buf.writeInt(x.size());
        for (int i = 0; i < x.size(); i++) {
            buf.writeDouble(x.get(i));
            buf.writeDouble(y.get(i));
            buf.writeDouble(z.get(i));
        }

        return buf;
    }

    public static ParticleColorPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != ParticleColorPacket.ID) throw new UnsupportedOperationException();

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
        return new ParticleColorPacket(type, r, g, b, scale, x, y, z);
    }
}
