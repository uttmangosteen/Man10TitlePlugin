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
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }
    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if (subChannel.equals("MTitle")) {
            String title = in.readUTF();
            String subtitle = in.readUTF();
            int stayTime = in.readInt();
            int fadeInTime = in.readInt();
            int fadeOutTime = in.readInt();

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(title, subtitle, fadeInTime, stayTime, fadeOutTime);
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            }
        }
    }
}
