package packetoptimizeplugin.packetoptimizeplugin;

import com.destroystokyo.paper.ParticleBuilder;
import com.github.ryuzu.ryuzucommandsgenerator.CommandsGenerator;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

        List<String> originalparticles = Arrays.asList("flash", "flame", "endrod", "composter", "crit", "firework");

        for (int i = 0; i < originalparticles.size(); i++) {
            int finalI = i;
            CommandsGenerator.registerCommand("rpo.particle." + originalparticles.get(finalI),
                    data -> {
                        World world = data.getArgs().length == 14 ? ((Entity) data.getSender()).getWorld() : Bukkit.getServer().getWorld(data.getArgs()[14]);
                        double x = Double.parseDouble(data.getArgs()[2]);
                        double y = Double.parseDouble(data.getArgs()[3]);
                        double z = Double.parseDouble(data.getArgs()[4]);
                        float r = Float.parseFloat(data.getArgs()[5]);
                        float g = Float.parseFloat(data.getArgs()[6]);
                        float b = Float.parseFloat(data.getArgs()[7]);
                        float scale = Float.parseFloat(data.getArgs()[8]);
                        float offx = Float.parseFloat(data.getArgs()[9]);
                        float offy = Float.parseFloat(data.getArgs()[10]);
                        float offz = Float.parseFloat(data.getArgs()[11]);
                        float speed = Float.parseFloat(data.getArgs()[12]);
                        int count = Integer.parseInt(data.getArgs()[13]);

                        new OriginalParticleBuilder(finalI)
                                .location(world, x, y, z)
                                .count(count)
                                .extra(speed)
                                .color(Color.fromRGB(Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)), scale)
                                .offset(offx, offy, offz)
                                .spawn();
                    },
                    "rpo.op",
                    data -> {
                        if (data.getArgs().length <= 13) {
                            data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " particle " + originalparticles.get(finalI) + " [x] [y] [z] [r] [g] [b] [sclae] [offx] [offy] [offz] [speed] [count] {world}");
                            return false;
                        }

                        if (data.getArgs().length >= 15 && Bukkit.getWorld(data.getArgs()[14]) == null) {
                            data.sendMessage(ChatColor.RED + "正しい型を入力してください");
                            return false;
                        }

                        try {
                            Double.parseDouble(data.getArgs()[2]);
                            Double.parseDouble(data.getArgs()[3]);
                            Double.parseDouble(data.getArgs()[4]);
                            Float.parseFloat(data.getArgs()[5]);
                            Float.parseFloat(data.getArgs()[6]);
                            Float.parseFloat(data.getArgs()[7]);
                            Float.parseFloat(data.getArgs()[8]);
                            Float.parseFloat(data.getArgs()[9]);
                            Float.parseFloat(data.getArgs()[10]);
                            Float.parseFloat(data.getArgs()[11]);
                            Float.parseFloat(data.getArgs()[12]);
                            Integer.parseInt(data.getArgs()[13]);
                        } catch (NumberFormatException e) {
                            data.sendMessage(ChatColor.RED + "正しい型を入力してください");
                            return false;
                        }
                        return true;
                    },
                    data -> {
                        if (!(data.getSender() instanceof Entity) && data.getArgs().length <= 13) {
                            data.sendMessage(ChatColor.RED + "このコマンドはエンティティのみ実行できます");
                            return false;
                        }
                        return true;
                    }
            );
        }

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
                data -> data.sendMessage(ChatColor.RED + "/" + data.getLabel() + " particle [flash/flame/endrod]"),
                "rpo.op",
                data -> data.getArgs().length == 1
        );
    }
}
