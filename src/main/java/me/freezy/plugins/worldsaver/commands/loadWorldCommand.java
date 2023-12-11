package me.freezy.plugins.worldsaver.commands;

import me.freezy.plugins.worldsaver.worldsaver.Worldsaver;
import net.kyori.adventure.text.Component;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
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

public class loadWorldCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
            File worldFolder = world.getWorldFolder();
            File dataFolder = Worldsaver.getInstance().getDataFolder();
            File dataWorldFolder;
            if (args.length >= 1) {

                dataWorldFolder = new File(dataFolder.getPath() + "/worlds" + "/" + args[0]);

                if (!dataWorldFolder.exists()) {
                    player.sendMessage("no save");
                    return true;
                }

                player.kick(Component.text("The Server stops to load the world"));

                try {
                    FileUtils.copyDirectory(dataWorldFolder, worldFolder);
                } catch (IOException e) {
                    sender.getServer().getLogger().severe(new RuntimeException(e).toString());
                }
            } else {

                dataWorldFolder = new File(dataFolder.getPath() + "/worlds" + "/save");

                if (!dataWorldFolder.exists()) {
                    player.sendMessage("no save");
                    return true;
                }

                player.kick(Component.text("The Server stops to load the world"));

                try {
                    FileUtils.copyDirectory(dataWorldFolder, worldFolder);
                } catch (IOException e) {
                    sender.getServer().getLogger().severe(new RuntimeException(e).toString());
                }
            }
            Bukkit.spigot().restart();
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
            System.out.println(directories);
            return directories;
        }

        return null;
    }
}
