package packetoptimizeplugin.packetoptimizeplugin;

import com.destroystokyo.paper.ParticleBuilder;
import com.github.ryuzu.ryuzucommandsgenerator.CommandsGenerator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import packetoptimizeplugin.packetoptimizeplugin.Packets.CheckUsingModPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.ParticlePackets.Original.ParticleOriginalColorPacket;
import packetoptimizeplugin.packetoptimizeplugin.Packets.SetParticleDrawingRatePacket;

import java.util.*;

public class Commands {
    public static void implement() {
        CommandsGenerator.registerCommand("rpo.list",
                (data) -> {
                    data.getSender().sendMessage(ChatColor.GREEN + "使用者一覧:");
                    for (Player p : RyuZUPacketOptimizer.usingPlayers.keySet()) {
                        data.getSender().sendMessage(ChatColor.GREEN + p.getName() + ": " + RyuZUPacketOptimizer.usingPlayers.get(p));
                    }
                }
                , "rpo.op"
        );

        CommandsGenerator.registerCommand("rpo.remove",
                data -> {
                    Player p = (Player) data.getSender();
                    RyuZUPacketOptimizer.usingPlayers.remove(p);
                    RyuZUPacketOptimizer.particleQueue.remove(p);
                    data.getSender().sendMessage(ChatColor.GREEN + "使用者リストから除外しました");
                },
                "rpo.op",
                data -> true,
                data -> {
                    if (!(data.getSender() instanceof Player)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行できます");
                        return false;
                    }
                    Player p = (Player) data.getSender();
                    if (!RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                        data.sendMessage(ChatColor.RED + "あなたはRyuZUPacketOptimizerを使用していません");
                        return false;
                    }
                    return true;
                }
        );

        CommandsGenerator.registerCommand("rpo.add",
                data -> {
                    Player p = (Player) data.getSender();
                    RyuZUPacketOptimizer.sendPacket(p, new CheckUsingModPacket().encode().array());
                    data.getSender().sendMessage(ChatColor.GREEN + "使用者リストに追加しました");
                },
                "rpo.op",
                data -> true,
                data -> {
                    if (!(data.getSender() instanceof Player)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行できます");
                        return false;
                    }
                    return true;
                }
        );

        CommandsGenerator.registerCommand("rpo.particledrawingrate",
                data -> {
                    Player p = (Player) data.getSender();
                    int i = Integer.parseInt(data.getArgs()[1]);
                    RyuZUPacketOptimizer.sendPacket(p, new SetParticleDrawingRatePacket(i).encode());
                    data.sendMessage(ChatColor.GREEN + "パーティクル描画率を適用しました");
                },
                "rpo.player",
                data -> {
                    if (data.getArgs().length <= 1) {
                        data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " particledrawingrate [0～100]");
                        return false;
                    }
                    int i;
                    try {
                        i = Integer.parseInt(data.getArgs()[1]);
                    } catch (NumberFormatException e) {
                        data.sendMessage(ChatColor.RED + "0～100の値を使用してください");
                        return false;
                    }
                    if (0 <= i && i <= 100) {
                        return true;
                    } else {
                        data.sendMessage(ChatColor.RED + "0～100の値を使用してください");
                        return false;
                    }
                },
                data -> {
                    if (!(data.getSender() instanceof Player)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはプレイヤーしか実行できません");
                        return false;
                    }
                    Player p = (Player) data.getSender();
                    if (!RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                        data.sendMessage(ChatColor.RED + "RyuZUPacketOptimizerの使用者以外は使用できません");
                        return false;
                    }
                    return true;
                }
        );

        CommandsGenerator.registerCommand("rpo.particle.flash",
                data -> {
                    Entity e = (Entity) data.getSender();

                    int type = 0;
                    int count;
                    float r;
                    float g;
                    float b;
                    float scale;
                    float speed;
                    double x;
                    double y;
                    double z;
                    float offx;
                    float offy;
                    float offz;
                    x = Double.parseDouble(data.getArgs()[2]);
                    y = Double.parseDouble(data.getArgs()[3]);
                    z = Double.parseDouble(data.getArgs()[4]);
                    r = Float.parseFloat(data.getArgs()[5]);
                    g = Float.parseFloat(data.getArgs()[6]);
                    b = Float.parseFloat(data.getArgs()[7]);
                    scale = Float.parseFloat(data.getArgs()[8]);
                    offx = Float.parseFloat(data.getArgs()[9]);
                    offy = Float.parseFloat(data.getArgs()[10]);
                    offz = Float.parseFloat(data.getArgs()[11]);
                    speed = Float.parseFloat(data.getArgs()[12]);
                    count = Integer.parseInt(data.getArgs()[13]);

                    Collection<Player> players = e.getWorld().getNearbyPlayers(new Location(e.getWorld(), x, y, z), 512);
                    Collection<Player> usingPlayers = new ArrayList<>();
                    for (Player p : players) {
                        if (RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                            if(RyuZUPacketOptimizer.particleQueue.get(p) == null) RyuZUPacketOptimizer.particleQueue.put(p , new ArrayDeque<>());
                            RyuZUPacketOptimizer.particleQueue.get(p).add(new ParticleOriginalColorPacket(type, count, r, g, b, scale, speed
                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                            );
                            usingPlayers.add(p);
                        }
                    }
                    players.removeAll(usingPlayers);
                    Particle.FLASH.builder().location(e.getWorld(), x, y, z).count(count).extra(speed).receivers(players).offset(offx, offy, offz).spawn();
                },
                "rpo.op",
                data -> {
                    if (data.getArgs().length <= 13) {
                        data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " particle flash [x] [y] [z] [r] [g] [b] [sclae] [offx] [offy] [offz] [speed] [count]");
                        return false;
                    }
                    int count;
                    float r;
                    float g;
                    float b;
                    float scale;
                    float speed;
                    double x;
                    double y;
                    double z;
                    float offx;
                    float offy;
                    float offz;
                    try {
                        x = Double.parseDouble(data.getArgs()[2]);
                        y = Double.parseDouble(data.getArgs()[3]);
                        z = Double.parseDouble(data.getArgs()[4]);
                        r = Float.parseFloat(data.getArgs()[5]);
                        g = Float.parseFloat(data.getArgs()[6]);
                        b = Float.parseFloat(data.getArgs()[7]);
                        scale = Float.parseFloat(data.getArgs()[8]);
                        offx = Float.parseFloat(data.getArgs()[9]);
                        offy = Float.parseFloat(data.getArgs()[10]);
                        offz = Float.parseFloat(data.getArgs()[11]);
                        speed = Float.parseFloat(data.getArgs()[12]);
                        count = Integer.parseInt(data.getArgs()[13]);
                    } catch (NumberFormatException e) {
                        data.sendMessage(ChatColor.RED + "正しい型を入力してください");
                        return false;
                    }
                    return true;
                },
                data -> {
                    if (!(data.getSender() instanceof Entity)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはエンティティのみ実行できます");
                        return false;
                    }
                    return true;
                }
        );

        CommandsGenerator.registerCommand("rpo.particle.flame",
                data -> {
                    Entity e = (Entity) data.getSender();

                    int type = 1;
                    int count;
                    float r;
                    float g;
                    float b;
                    float scale;
                    float speed;
                    double x;
                    double y;
                    double z;
                    float offx;
                    float offy;
                    float offz;
                    x = Double.parseDouble(data.getArgs()[2]);
                    y = Double.parseDouble(data.getArgs()[3]);
                    z = Double.parseDouble(data.getArgs()[4]);
                    r = Float.parseFloat(data.getArgs()[5]);
                    g = Float.parseFloat(data.getArgs()[6]);
                    b = Float.parseFloat(data.getArgs()[7]);
                    scale = Float.parseFloat(data.getArgs()[8]);
                    offx = Float.parseFloat(data.getArgs()[9]);
                    offy = Float.parseFloat(data.getArgs()[10]);
                    offz = Float.parseFloat(data.getArgs()[11]);
                    speed = Float.parseFloat(data.getArgs()[12]);
                    count = Integer.parseInt(data.getArgs()[13]);

                    Collection<Player> players = e.getWorld().getNearbyPlayers(new Location(e.getWorld(), x, y, z), 512);
                    Collection<Player> usingPlayers = new ArrayList<>();
                    for (Player p : players) {
                        if (RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                            if(RyuZUPacketOptimizer.particleQueue.get(p) == null) RyuZUPacketOptimizer.particleQueue.put(p , new ArrayDeque<>());
                            RyuZUPacketOptimizer.particleQueue.get(p).add(new ParticleOriginalColorPacket(type, count, r, g, b, scale, speed
                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                            );
                            usingPlayers.add(p);
                        }
                    }
                    players.removeAll(usingPlayers);
                    Particle.FLAME.builder().location(e.getWorld(), x, y, z).count(count).extra(speed).receivers(players).offset(offx, offy, offz).spawn();
                },
                "rpo.op",
                data -> {
                    if (data.getArgs().length <= 13) {
                        data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " particle flame [x] [y] [z] [r] [g] [b] [sclae] [offx] [offy] [offz] [speed] [count]");
                        return false;
                    }
                    int count;
                    float r;
                    float g;
                    float b;
                    float scale;
                    float speed;
                    double x;
                    double y;
                    double z;
                    float offx;
                    float offy;
                    float offz;
                    try {
                        x = Double.parseDouble(data.getArgs()[2]);
                        y = Double.parseDouble(data.getArgs()[3]);
                        z = Double.parseDouble(data.getArgs()[4]);
                        r = Float.parseFloat(data.getArgs()[5]);
                        g = Float.parseFloat(data.getArgs()[6]);
                        b = Float.parseFloat(data.getArgs()[7]);
                        scale = Float.parseFloat(data.getArgs()[8]);
                        offx = Float.parseFloat(data.getArgs()[9]);
                        offy = Float.parseFloat(data.getArgs()[10]);
                        offz = Float.parseFloat(data.getArgs()[11]);
                        speed = Float.parseFloat(data.getArgs()[12]);
                        count = Integer.parseInt(data.getArgs()[13]);
                    } catch (NumberFormatException e) {
                        data.sendMessage(ChatColor.RED + "正しい型を入力してください");
                        return false;
                    }
                    return true;
                },
                data -> {
                    if (!(data.getSender() instanceof Entity)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはエンティティのみ実行できます");
                        return false;
                    }
                    return true;
                }
        );

        CommandsGenerator.registerCommand("rpo.particle.endrod",
                data -> {
                    Entity e = (Entity) data.getSender();

                    int type = 2;
                    int count;
                    float r;
                    float g;
                    float b;
                    float scale;
                    float speed;
                    double x;
                    double y;
                    double z;
                    float offx;
                    float offy;
                    float offz;
                    x = Double.parseDouble(data.getArgs()[2]);
                    y = Double.parseDouble(data.getArgs()[3]);
                    z = Double.parseDouble(data.getArgs()[4]);
                    r = Float.parseFloat(data.getArgs()[5]);
                    g = Float.parseFloat(data.getArgs()[6]);
                    b = Float.parseFloat(data.getArgs()[7]);
                    scale = Float.parseFloat(data.getArgs()[8]);
                    offx = Float.parseFloat(data.getArgs()[9]);
                    offy = Float.parseFloat(data.getArgs()[10]);
                    offz = Float.parseFloat(data.getArgs()[11]);
                    speed = Float.parseFloat(data.getArgs()[12]);
                    count = Integer.parseInt(data.getArgs()[13]);

                    Collection<Player> players = e.getWorld().getNearbyPlayers(new Location(e.getWorld(), x, y, z), 512);
                    Collection<Player> usingPlayers = new ArrayList<>();
                    for (Player p : players) {
                        if (RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                            if(RyuZUPacketOptimizer.particleQueue.get(p) == null) RyuZUPacketOptimizer.particleQueue.put(p , new ArrayDeque<>());
                            RyuZUPacketOptimizer.particleQueue.get(p).add(new ParticleOriginalColorPacket(type, count, r, g, b, scale, speed
                                    , new ArrayList<>(Collections.singletonList(x)), new ArrayList<>(Collections.singletonList(y)), new ArrayList<>(Collections.singletonList(z))
                                    , new ArrayList<>(Collections.singletonList(offx)), new ArrayList<>(Collections.singletonList(offy)), new ArrayList<>(Collections.singletonList(offz)))
                            );
                            usingPlayers.add(p);
                        }
                    }
                    players.removeAll(usingPlayers);
                    Particle.END_ROD.builder().location(e.getWorld(), x, y, z).count(count).extra(speed).receivers(players).offset(offx, offy, offz).spawn();
                },
                "rpo.op",
                data -> {
                    if (data.getArgs().length <= 13) {
                        data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " particle endrod [x] [y] [z] [r] [g] [b] [sclae] [offx] [offy] [offz] [speed] [count]");
                        return false;
                    }
                    int count;
                    float r;
                    float g;
                    float b;
                    float scale;
                    float speed;
                    double x;
                    double y;
                    double z;
                    float offx;
                    float offy;
                    float offz;
                    try {
                        x = Double.parseDouble(data.getArgs()[2]);
                        y = Double.parseDouble(data.getArgs()[3]);
                        z = Double.parseDouble(data.getArgs()[4]);
                        r = Float.parseFloat(data.getArgs()[5]);
                        g = Float.parseFloat(data.getArgs()[6]);
                        b = Float.parseFloat(data.getArgs()[7]);
                        scale = Float.parseFloat(data.getArgs()[8]);
                        offx = Float.parseFloat(data.getArgs()[9]);
                        offy = Float.parseFloat(data.getArgs()[10]);
                        offz = Float.parseFloat(data.getArgs()[11]);
                        speed = Float.parseFloat(data.getArgs()[12]);
                        count = Integer.parseInt(data.getArgs()[13]);
                    } catch (NumberFormatException e) {
                        data.sendMessage(ChatColor.RED + "正しい型を入力してください");
                        return false;
                    }
                    return true;
                },
                data -> {
                    if (!(data.getSender() instanceof Entity)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはエンティティのみ実行できます");
                        return false;
                    }
                    return true;
                }
        );

        CommandsGenerator.registerCommand("rpo",
                data -> {
                    if (data.getSender().hasPermission("rpo.op")) {
                        data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " [list/particledrawingrate/remove/particle]");
                    } else {
                        data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " [particledrawingrate]");
                    }
                },
                "rpo.player",
                data -> data.getArgs().length == 0
        );

        CommandsGenerator.registerCommand("rpo.particle",
                data -> {
                    data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " particle [flash/flame/endrod]");
                },
                "rpo.op",
                data -> data.getArgs().length == 1
        );
    }
}
