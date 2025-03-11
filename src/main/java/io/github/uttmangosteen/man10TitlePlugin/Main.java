package io.github.uttmangosteen.man10TitlePlugin;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Main extends JavaPlugin implements PluginMessageListener {
    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("mtitle")).setExecutor(new MTitle(this));
        Objects.requireNonNull(getCommand("mtitle_test")).setExecutor(new MTitle(this));
        Objects.requireNonNull(getCommand("mtitle_all")).setExecutor(new MTitle(this));

        if (!getServer().getPluginManager().isPluginEnabled(this)) return;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "mtitle:channel");
        getServer().getMessenger().registerIncomingPluginChannel(this, "mtitle:channel", this);
    }

    //bungeeから送られた情報をもとにTitle表示
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("mtitle:channel")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("MTitle")) {
            String title = in.readUTF();
            String subTitle = in.readUTF();
            int stay = in.readInt();
            int fadeIn = in.readInt();
            int fadeOut = in.readInt();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            }
        }
    }
}
