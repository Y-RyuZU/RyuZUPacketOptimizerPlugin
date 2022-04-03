package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Offset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

public class ParticleOffsetItemPacket extends ParticleOffsetPacket {
    public static final byte ID = 11;

    protected final int itemid;
    protected final int data;

    public ParticleOffsetItemPacket(int type, int count, float speed, int itemid, int data) {
        super(type, count, speed);
        this.itemid = itemid;
        this.data = data;
    }

    public ParticleOffsetItemPacket(int type, int count, float speed, int itemid, int data, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, count, speed, x, y, z, offx, offy, offz);
        this.itemid = itemid;
        this.data = data;
    }

    public boolean isSimilar(int type, int count, float speed, int itemid,int data) {
        return this.type == type && this.count == count && this.speed == speed && this.data == data && this.itemid == itemid && 21 + this.x.size() * 48 + 48 < 1024;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
        buf.writeInt(count);
        buf.writeFloat(speed);
        buf.writeInt(itemid);
        buf.writeInt(data);
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

    public static ParticleOffsetItemPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);
        //IDチェック
        byte ID = buf.readByte();
        if (ID != ParticleOffsetItemPacket.ID) throw new UnsupportedOperationException();
        //載せられたデータの読み取り
        int type = buf.readInt();
        int count = buf.readInt();
        float speed = buf.readFloat();
        int blockid = buf.readInt();
        int data = buf.readInt();
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
        return new ParticleOffsetItemPacket(type, count, speed, blockid, data, x, y, z, offx, offy, offz);
    }
}
