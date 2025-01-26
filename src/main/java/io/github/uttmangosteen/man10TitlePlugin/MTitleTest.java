package io.github.uttmangosteen.man10TitlePlugin;

import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MTitleTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, String[] args) {
        if (!(sender.hasPermission("red.man10.mtitle"))) return true;
        if (!(sender instanceof Player)) {
            sender.sendMessage("§e[Man10TitlePlugin] mtitle_testはプレイヤーでないと使えません§r");
            return true;
        }
        String title, subtitle = "";
        int stay = 5, fadeOut = 2;
        switch (args.length) {
            case 4:
                try {
                    fadeOut = Integer.parseInt(args[3]);
                } catch (NumberFormatException ignored) {}
            case 3:
                try {
                    stay = Integer.parseInt(args[2]);
                } catch (NumberFormatException ignored) {}
            case 2:
                subtitle = Objects.equals(args[1], "-") ? "" : args[1].replace('&', '§');
            case 1:
                title = Objects.equals(args[0], "-") ? "" : args[0].replace('&', '§');
                Player p = (Player) sender;
                p.sendTitle(title, subtitle, 0, stay * 20, fadeOut * 20);
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);

                return true;
        }
        return false;
    }
}