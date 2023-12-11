package me.freezy.plugins.worldsaver.commands;

import me.freezy.plugins.worldsaver.worldsaver.Worldsaver;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class deleteWorldCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("wssaver.admin") || !player.hasPermission("wssaver.use.delccmd")) {
                player.sendMessage(ChatColor.RED + "Not enough Permissions!");
                return false;
            }

            File dataFolder = Worldsaver.getInstance().getDataFolder();
            File dataWorldFolder;
            if (args.length >= 1) {
                dataWorldFolder = new File(dataFolder.getPath() + "/worlds" + "/" + args[0]);

                if (!dataWorldFolder.exists()) {
                    player.sendMessage("no save");
                    return true;
                } else if (dataWorldFolder.exists()) {
                    try {
                        FileUtils.deleteDirectory(dataWorldFolder);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    player.sendMessage("save deleted");
                }
            }
        } else {
            sender.getServer().getLogger().severe("You must be a Player, otherwise the server might corrupt!");
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File worldsFolder = new File(Worldsaver.getInstance().getDataFolder().getPath() + "/worlds");
        File[] files = worldsFolder.listFiles(File::isDirectory);

        List<String> directories = new ArrayList<>();
        for (File file : Objects.requireNonNull(files)) {
            directories.add(file.getName());
        }

        if (args.length >= 1) {
            return directories;
        }

        return null;
    }
}
