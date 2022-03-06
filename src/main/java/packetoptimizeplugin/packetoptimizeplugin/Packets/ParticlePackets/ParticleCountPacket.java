package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class ParticleCountPacket extends ParticleBasePacket {
    public static final byte ID = 4;

    protected final int count;
    protected final float speed;

    public ParticleCountPacket(int type, int count, float speed) {
        super(type);
        this.count = count;
        this.speed = speed;
    }

    public ParticleCountPacket(int type, int count, float speed, List<Double> x, List<Double> y, List<Double> z) {
        super(type, x, y, z);
        this.count = count;
        this.speed = speed;
    }

    public boolean isSimilar(int type, int count, float speed) {
        return this.type == type && this.count == count && this.speed == speed && 17 + this.x.size() * 24 + 24 < 1024;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
        buf.writeInt(count);
        buf.writeFloat(speed);
        buf.writeInt(x.size());
        for (int i = 0; i < x.size(); i++) {
            buf.writeDouble(x.get(i));
            buf.writeDouble(y.get(i));
            buf.writeDouble(z.get(i));
        }

        return buf;
    }

    public static ParticleCountPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);

        byte ID = buf.readByte();
        if (ID != ParticleCountPacket.ID) throw new UnsupportedOperationException();

        int type = buf.readInt();
        int count = buf.readInt();
        float speed = buf.readFloat();
        int size = buf.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buf.readDouble());
            y.add(buf.readDouble());
            z.add(buf.readDouble());
        }
        return new ParticleCountPacket(type, count, speed, x, y, z);
    }
}
