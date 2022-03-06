package packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Force;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.ParticleBasePacket;

import java.util.ArrayList;
import java.util.List;

public class ParticleForcePacket extends ParticleBasePacket {
    public static final byte ID = 7;

    public ParticleForcePacket(int type) {
        super(type);
    }

    public ParticleForcePacket(int type, List<Double> x, List<Double> y, List<Double> z) {
        super(type, x, y, z);
    }

    public static ParticleForcePacket decode(byte[] byteArray) {
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);
        //IDチェック
        byte ID = buf.readByte();
        if (ID != ParticleForcePacket.ID) throw new UnsupportedOperationException();
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

        return new ParticleForcePacket(type, x, y, z);
    }
}
