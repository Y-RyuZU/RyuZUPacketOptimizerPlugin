package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class ParticleVectorPacket extends ParticleBasePacket {
    public static final byte ID = 6;

    protected final float speed;
    protected List<Float> offx = new ArrayList<>();
    protected List<Float> offy = new ArrayList<>();
    protected List<Float> offz = new ArrayList<>();

    public ParticleVectorPacket(int type, float speed) {
        super(type);
        this.speed = speed;
    }

    public ParticleVectorPacket(int type, float speed, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, x, y, z);
        this.speed = speed;
        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
    }

    public boolean isSimilar(int type, float speed) {
        return this.type == type && this.speed == speed && 13 + this.x.size() * 48 + 48 < 1000;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
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

    public static ParticleVectorPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != ParticleVectorPacket.ID) throw new UnsupportedOperationException();

        int type = buf.readInt();
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
        return new ParticleVectorPacket(type, speed, x, y, z, offx, offy, offz);
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
