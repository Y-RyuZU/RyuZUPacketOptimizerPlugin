package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Original;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleCompressionPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleVectorPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleOriginalColorPacket extends ParticleVectorPacket {
    public static final byte ID = 18;

    protected final int count;
    protected final float r;
    protected final float g;
    protected final float b;
    protected final float scale;


    public ParticleOriginalColorPacket(int type, int count, float r, float g, float b, float scale, float speed) {
        super(type, speed);
        this.count = count;
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
    }

    public ParticleOriginalColorPacket(int type, int count, float r, float g, float b, float scale, float speed, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, speed, x, y, z, offx, offy, offz);
        this.count = count;
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
    }

    public boolean isSimilar(int type, float speed, float r, float g, float b, float scale) {
        return this.type == type && this.speed == speed && this.r == r && this.g == g && this.b == b && this.scale == scale;
    }

    public boolean isSimilar(int type, float speed) {
        return this.type == type && this.speed == speed;
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
        buf.writeInt(count);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(scale);
        buf.writeFloat(speed);
        buf.writeInt(x.size());
        for (int i = 0; i < x.size(); i++) {
            buf.writeDouble(x.get(i));
            buf.writeDouble(y.get(i));
            buf.writeDouble(z.get(i));
            buf.writeFloat(offx.get(i));
            buf.writeFloat(offy.get(i));
            buf.writeFloat(offz.get(i));
        }
        return buf;
    }

    public static ParticleOriginalColorPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != ParticleCompressionPacket.ID) throw new UnsupportedOperationException();

        int type = buf.readInt();
        int count = buf.readInt();
        float r = buf.readFloat();
        float g = buf.readFloat();
        float b = buf.readFloat();
        float scale = buf.readFloat();
        float speed = buf.readFloat();
        int size = buf.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        List<Float> offx = new ArrayList<>();
        List<Float> offy = new ArrayList<>();
        List<Float> offz = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buf.readDouble());
            y.add(buf.readDouble());
            z.add(buf.readDouble());
            offx.add(buf.readFloat());
            offy.add(buf.readFloat());
            offz.add(buf.readFloat());
        }
        return new ParticleOriginalColorPacket(type, count, r, g, b, scale, speed, x, y, z, offx, offy, offz);
    }
}
