package io.github.uttmangosteen.man10TitlePlugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("mtitle")).setExecutor(new MTitle());
        Objects.requireNonNull(getCommand("mtitle_test")).setExecutor(new MTitle());
        Objects.requireNonNull(getCommand("mtitle_all")).setExecutor(new MTitleAll(this));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }
}
