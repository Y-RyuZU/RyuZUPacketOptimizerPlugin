package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Offset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleColorPacket;

import java.util.ArrayList;
import java.util.List;

public class ParticleOffsetColorPacket extends ParticleColorPacket {
    public static final byte ID = 13;

    protected List<Float> offx = new ArrayList<>();
    protected List<Float> offy = new ArrayList<>();
    protected List<Float> offz = new ArrayList<>();
    protected int count;

    public ParticleOffsetColorPacket(int type,int count, float r, float g, float b, float scale) {
        super(type, r, b, g, scale);
        this.count = count;
    }

    public ParticleOffsetColorPacket(int type,int count, float r, float g, float b, float scale, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, r, g, b, scale, x, y, z);
        this.count = count;
        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
    }

    public boolean isSimilar(float r, float g, float b, float scale) {
        return this.count == count && this.r == r && this.g == g && this.b == b && this.scale == scale && 25 + this.x.size() * 48 + 48 < 1024;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
        buf.writeInt(count);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(scale);
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

    public static ParticleOffsetColorPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != ParticleOffsetColorPacket.ID) throw new UnsupportedOperationException();

        int type = buf.readInt();
        int count = buf.readInt();
        float r = buf.readFloat();
        float g = buf.readFloat();
        float b = buf.readFloat();
        float scale = buf.readFloat();
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
        return new ParticleOffsetColorPacket(type,count, r, g, b, scale, x, y, z, offx, offy, offz);
    }

    public void addVector(float x, float y, float z) {
        this.offx.add(x);
        this.offy.add(y);
        this.offz.add(z);
    }

    public void addLocation(double x, double y, double z, float offx, float offy, float offz) {
        this.x.add(x);
        this.y.add(y);
        this.z.add(z);
        this.offx.add(offx);
        this.offy.add(offy);
        this.offz.add(offz);
    }
}
