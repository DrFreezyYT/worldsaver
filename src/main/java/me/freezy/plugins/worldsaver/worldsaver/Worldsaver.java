package me.freezy.plugins.worldsaver.worldsaver;

import me.freezy.plugins.worldsaver.commands.deleteWorldCommand;
import me.freezy.plugins.worldsaver.commands.loadWorldCommand;
import me.freezy.plugins.worldsaver.commands.saveWorldCommand;
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
        Objects.requireNonNull(this.getCommand("saveworld")).setExecutor(new saveWorldCommand());
        Objects.requireNonNull(this.getCommand("loadworld")).setExecutor(new loadWorldCommand());
        Objects.requireNonNull(this.getCommand("deleteworld")).setExecutor(new deleteWorldCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
