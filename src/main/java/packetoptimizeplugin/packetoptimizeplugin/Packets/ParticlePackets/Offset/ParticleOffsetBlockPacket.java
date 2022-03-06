package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Offset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class ParticleOffsetBlockPacket extends ParticleOffsetPacket {
    public static final byte ID = 10;

    protected final int blockid;

    public ParticleOffsetBlockPacket(int type, int count, float speed, int blockid) {
        super(type, count, speed);
        this.blockid = blockid;
    }

    public ParticleOffsetBlockPacket(int type, int count, float speed, int blockid, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, count, speed, x, y, z, offx, offy, offz);
        this.blockid = blockid;
    }

    public boolean isSimilar(int type, int count, float speed, int blockid) {
        return this.type == type && this.count == count && this.speed == speed && this.blockid == blockid && 21 + this.x.size() * 48 + 48 < 1000;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
        buf.writeInt(count);
        buf.writeFloat(speed);
        buf.writeInt(blockid);
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

    public static ParticleOffsetBlockPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);
        //IDチェック
        byte ID = buf.readByte();
        if (ID != ParticleOffsetBlockPacket.ID) throw new UnsupportedOperationException();
        //載せられたデータの読み取り
        int type = buf.readInt();
        int count = buf.readInt();
        float speed = buf.readFloat();
        int blockid = buf.readInt();
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
        return new ParticleOffsetBlockPacket(type, count, speed, blockid, x, y, z, offx, offy, offz);
    }
}
