package me.freezy.plugins.worldsaver.commands;

import me.freezy.plugins.worldsaver.Worldsaver;
import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class saveworld implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                Player player = (Player) sender;
                player.sendMessage("The server may lag!");
                World world = player.getWorld();
                File worldFolder = world.getWorldFolder();
                File dataFolder = Worldsaver.getInstance().getDataFolder();

                File dataWorldFolder = new File(dataFolder.getPath() + "/worlds" + "/" + args[0]);

                world.save();

                try {
                    FileUtils.copyDirectory(worldFolder, dataWorldFolder);
                } catch (IOException e) {
                    sender.getServer().getLogger().severe(new RuntimeException(e).toString());
                }
                player.sendMessage("Saved the Worlds and Created a backup of your world!");
            } else {
                Player player = (Player) sender;
                player.sendMessage("The server may lag!");
                World world = player.getWorld();
                File worldFolder = world.getWorldFolder();
                File dataFolder = Worldsaver.getInstance().getDataFolder();

                File dataWorldFolder = new File(dataFolder.getPath() + "/worlds" + "/save");

                world.save();

                try {
                    FileUtils.copyDirectory(worldFolder, dataWorldFolder);
                } catch (IOException e) {
                    sender.getServer().getLogger().severe(new RuntimeException(e).toString());
                }
                player.sendMessage("Saved the Worlds and Created a backup of your world!");
            }
        } else {
            sender.getServer().getLogger().severe("You must be a Player, otherwise the server might corrupt!");
        }
        return false;
    }
}
