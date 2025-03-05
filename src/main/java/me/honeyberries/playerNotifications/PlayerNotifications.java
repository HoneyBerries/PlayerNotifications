package me.honeyberries.playerNotifications;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class PlayerNotifications extends JavaPlugin {

    private void copyResourceIfNotExists(String fileName) {
        File file = new File(getDataFolder(), fileName);
        if (!file.exists()) {
            try (InputStream in = getResource(fileName);
                 FileOutputStream out = new FileOutputStream(file)) {
                if (in != null) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                    getLogger().info("Copied default sound file: " + fileName);
                } else {
                    getLogger().warning("Resource not found in JAR: " + fileName);
                }
            } catch (IOException e) {
                getLogger().severe("Failed to copy " + fileName + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void onEnable() {
        // Ensure the plugin folder exists
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Copy the sound files from resources if they don’t exist
        copyResourceIfNotExists("join_sound.wav");
        copyResourceIfNotExists("quit_sound.wav");
        // Register event listener
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);

        getLogger().info("PlayerNotifications has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("PlayerNotifications has been disabled!");
    }

    public static PlayerNotifications getInstance() {
        return getPlugin(PlayerNotifications.class);
    }
}
