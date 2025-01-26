package io.github.uttmangosteen.man10TitlePlugin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MTitle implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, String[] args) {
        if (!(sender.hasPermission("red.man10.mtitle"))) return true;
        if (!(sender instanceof Player) && alias.equals("mtitle_test")) return true;
        if (args.length == 0) return false;
        StringBuilder title = new StringBuilder();
        StringBuilder subtitle = new StringBuilder();
        int stay = 5, fadeOut = 2;
        for (int i = 0, j = 0; i < args.length; i++) {
            if (args[i].equals("|")) {j++;continue;}
            args[i] = args[i].replace("&", "§");
            args[i] = args[i].replace("#", " ");
            switch (j) {
                case 0: title.append(args[i]);continue;
                case 1: subtitle.append(args[i]);continue;
                case 2:
                    try {stay = Integer.parseInt(args[i]);} catch (NumberFormatException ignored) {}continue;
                case 3:
                    try {fadeOut = Integer.parseInt(args[i]);} catch (NumberFormatException ignored) {}
            }
        }
        if (Objects.equals(alias, "mtitle_test")) {
            Player p = (Player) sender;
            p.sendTitle(title.toString(), subtitle.toString(), 0, stay * 20, fadeOut * 20);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(title.toString(), subtitle.toString(), 0, stay * 20, fadeOut * 20);
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            }
        }
        return true;
    }
}