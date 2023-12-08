package me.freezy.plugins.worldsaver.commands;

import me.freezy.plugins.worldsaver.Worldsaver;
import net.kyori.adventure.text.Component;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class loadworld implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
            File worldFolder = world.getWorldFolder();
            File dataFolder = Worldsaver.getInstance().getDataFolder();

            File dataWorldFolder = new File(dataFolder.getPath() + "/worlds" + "/save");

            if (!dataWorldFolder.exists()) {
                player.sendMessage("no save");
                return true;
            }

            player.kick(Component.text("The Server stops to load the world"));

            try {
                if (dataWorldFolder.exists()) {
                       dataWorldFolder.delete();
                }
                FileUtils.copyDirectory(dataWorldFolder, worldFolder);
            } catch (IOException e) {
                sender.getServer().getLogger().severe(new RuntimeException(e).toString());
            }
            Bukkit.shutdown();
        } else {
            sender.getServer().getLogger().severe("You must be a Player, otherwise the server might corrupt!");
        }

        return false;
    }
}
