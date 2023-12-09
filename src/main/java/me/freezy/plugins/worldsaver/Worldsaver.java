package me.freezy.plugins.worldsaver;

import me.freezy.plugins.worldsaver.commands.loadworld;
import me.freezy.plugins.worldsaver.commands.saveworld;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Worldsaver extends JavaPlugin {
    public static Worldsaver instance;

    public static Worldsaver getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        //config stuff
        instance = this;
        Objects.requireNonNull(this.getCommand("saveworld")).setExecutor(new saveworld());
        Objects.requireNonNull(this.getCommand("loadworld")).setExecutor(new loadworld());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
