package packetoptimizeplugin.packetoptimizeplugin;

import com.github.ryuzu.ryuzucommandgenerator.CommandsGenerator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commands {
    public static void implement() {
        CommandsGenerator.registerCommand("rpo.list",
                (data) -> {
                    data.getSender().sendMessage(ChatColor.GREEN + "使用者一覧:");
                    for (Player p : RyuZUPacketOptimizer.usingPlayers.keySet()) {
                        data.getSender().sendMessage(ChatColor.GREEN + p.getName() + ": " + RyuZUPacketOptimizer.usingPlayers.get(p));
                    }
                }, "rpo.op");

        CommandsGenerator.registerCommand("rpo.remove",
                data -> {
                    Player p = (Player) data.getSender();
                    RyuZUPacketOptimizer.usingPlayers.remove(p);
                    RyuZUPacketOptimizer.particleQueue.remove(p);
                    data.getSender().sendMessage(ChatColor.GREEN + "使用者リストから除外しました");
                }, "rpo.op",
                data -> {
                    if(!(data.getSender() instanceof Player)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行できます");
                        return false;
                    }
                    Player p = (Player) data.getSender();
                    if(!RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                        data.sendMessage(ChatColor.RED + "あなたはRyuZUPacketOptimizerを使用していません");
                        return false;
                    }
                    return true;
                });

        CommandsGenerator.registerCommand("rpo.remove",
                data -> {
                    Player p = (Player) data.getSender();
                    RyuZUPacketOptimizer.usingPlayers.remove(p);
                    RyuZUPacketOptimizer.particleQueue.remove(p);
                    data.getSender().sendMessage(ChatColor.GREEN + "使用者リストから除外しました");
                }, "rpo.op",
                data -> {
                    if(!(data.getSender() instanceof Player)) {
                        data.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行できます");
                        return false;
                    }
                    Player p = (Player) data.getSender();
                    if(!RyuZUPacketOptimizer.usingPlayers.containsKey(p)) {
                        data.sendMessage(ChatColor.RED + "あなたはRyuZUPacketOptimizerを使用していません");
                        return false;
                    }
                    return true;
                });
    }
}
