package io.github.uttmangosteen.man10TitlePlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MTitleAll implements CommandExecutor {
    private final JavaPlugin plugin;

    public MTitleAll(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (!(sender.hasPermission("red.man10.mtitle"))) return true;
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

        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);
            out.writeUTF("MTitleAll");
            out.writeUTF(title.toString());
            out.writeUTF(subtitle.toString());
            out.writeInt(stay);
            out.writeInt(fadeOut);

            Player player = (Player) sender;
            player.sendPluginMessage(plugin, "BungeeCord", byteArray.toByteArray());
        } catch (IOException e) {e.printStackTrace();}
        return true;
    }
}