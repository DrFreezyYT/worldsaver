package me.freezy.plugins.worldsaver.commands;

import me.freezy.plugins.worldsaver.worldsaver.Worldsaver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"resource", "deprication", "unchecked", "ResultOfMethodCallIgnored"})
public class saveWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                Player player = (Player) sender;

                if (!player.hasPermission("wssaver.admin") || !player.hasPermission("wssaver.use.delccmd")) {
                    player.sendMessage(ChatColor.RED + "Not enough Permissions!");
                    return false;
                }

                player.sendMessage("The server may lag!");
                World world = player.getWorld();
                File worldFolder = world.getWorldFolder();
                File dataFolder = Worldsaver.getInstance().getDataFolder();

                File dataWorldFolder = new File(dataFolder.getPath() + "/worlds" + "/" + args[0]);

                IOFileFilter filter = new NotFileFilter(new NameFileFilter("session.lock"));

                world.save();

                try {
                    if (dataWorldFolder.exists()) FileUtils.deleteDirectory(dataWorldFolder);
                    FileUtils.copyDirectory(worldFolder, dataWorldFolder, filter, true);
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
        return true;
    }
}
