package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleBasePacket {
    public static final byte ID = 1;

    protected final int type;

    protected List<Double> x;
    protected List<Double> y;
    protected List<Double> z;

    public ParticleBasePacket(int type) {
        this.type = type;
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
    }

    public ParticleBasePacket(int type, List<Double> x, List<Double> y, List<Double> z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(ID);
        buf.writeInt(type);
        buf.writeInt(x.size());

        for (int i = 0; i < x.size(); i++) {
            buf.writeDouble(x.get(i));
            buf.writeDouble(y.get(i));
            buf.writeDouble(z.get(i));
        }

        return buf;
    }
    
    public boolean isSimilar(int type) {
        return this.type == type && 9 + this.x.size() * 24 + 24 < 1024;
    }

    public static ParticleBasePacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);
        //IDチェック
        byte ID = buf.readByte();
        if (ID != ParticleBasePacket.ID) throw new UnsupportedOperationException();
        //載せられたデータの読み取り
        int type = buf.readInt();
        int size = buf.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buf.readDouble());
            y.add(buf.readDouble());
            z.add(buf.readDouble());
        }

        return new ParticleBasePacket(type, x, y, z);
    }

    public void addLocation(Location loc) {
        x.add(loc.getX());
        y.add(loc.getY());
        z.add(loc.getZ());
    }

    public void addLocation(Vector loc) {
        x.add(loc.getX());
        y.add(loc.getY());
        z.add(loc.getZ());
    }

    public void addLocation(double x , double y , double z) {
        this.x.add(x);
        this.y.add(y);
        this.z.add(z);
    }

    public int getType() {
        return type;
    }
}
