package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleCountPacket;

import java.util.ArrayList;
import java.util.List;

public class ParticleItemPacket extends ParticleCountPacket {
    public static final byte ID = 9;

    protected final int itemid;
    protected final int data;

    public ParticleItemPacket(int type, int count, float speed, int itemid, int data) {
        super(type, count, speed);
        this.itemid = itemid;
        this.data = data;
    }

    public ParticleItemPacket(int type, int count, float speed, int itemid, int data, List<Double> x, List<Double> y, List<Double> z) {
        super(type, count, speed, x, y, z);
        this.itemid = itemid;
        this.data = data;
    }

    public boolean isSimilar(int type, int count, float speed, int itemid, int data) {
        return this.type == type && this.count == count && this.speed == speed && this.itemid == itemid && this.data == data && 21 + this.x.size() * 24 + 24 < 1024;
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
        }

        return buf;
    }

    public static ParticleItemPacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);
        //IDチェック
        byte ID = buf.readByte();
        if (ID != ParticleItemPacket.ID) throw new UnsupportedOperationException();
        //載せられたデータの読み取り
        int type = buf.readInt();
        int count = buf.readInt();
        float speed = buf.readFloat();
        int itemid = buf.readInt();
        int data = buf.readInt();
        int size = buf.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buf.readDouble());
            y.add(buf.readDouble());
            z.add(buf.readDouble());
        }
        return new ParticleItemPacket(type, count, speed, itemid, data, x, y, z);
    }
}
