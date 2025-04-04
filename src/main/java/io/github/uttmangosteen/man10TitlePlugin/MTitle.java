package io.github.uttmangosteen.man10TitlePlugin;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MTitle implements CommandExecutor {

    private final JavaPlugin plugin;

    public MTitle(JavaPlugin plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, String[] args) {
        if (!(sender.hasPermission("red.man10.mtitle"))) return true;
        if (!(sender instanceof Player) && alias.equals("mtitle_test")) return true;
        if (args.length == 0) return false;

        //受け取ったコマンドを読み取る
        StringBuilder titleSB = new StringBuilder();
        StringBuilder subTitleSB = new StringBuilder();
        //defaultの表示時間(tick)
        int stay = 100, fadeIn = 20, fadeOut = 20;
        for (int i = 0, j = 0; i < args.length; i++) {
            if (args[i].equals("|")) {j++;continue;}
            args[i] = args[i].replace("&", "§").replace("#", " ");
            switch (j) {
                case 0 -> titleSB.append(args[i]);
                case 1 -> subTitleSB.append(args[i]);
                case 2 -> stay = readInt(args[i], stay);
                case 3 -> fadeIn = readInt(args[i], fadeIn);
                case 4 -> fadeOut = readInt(args[i], fadeOut);
            }
        }
        String title = titleSB.toString();
        String subTitle = subTitleSB.toString();

        //コマンドごとの処理
        if (Objects.equals(alias, "mtitle_test")) {
            Player p = (Player) sender;
            p.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);

        } else if (Objects.equals(alias, "mtitle_all")) {
            //bungeeにpluginMessageを送る際、playerが鯖に一人いなければならない
            //調べた感じbungee仕様上仕方なさそう
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            if (player == null) {
                Bukkit.getConsoleSender().sendMessage("§cmtitle_allは失敗した");
                return true;
            }
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("MTitle");
            out.writeUTF(title);
            out.writeUTF(subTitle);
            out.writeInt(stay);
            out.writeInt(fadeIn);
            out.writeInt(fadeOut);
            player.sendPluginMessage(plugin, "mtitle:channel", out.toByteArray());

        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            }
        }
        return true;
    }

    private static int readInt(String arg, int defaultValue) {
        try {
            return Integer.parseInt(arg) * 20;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}