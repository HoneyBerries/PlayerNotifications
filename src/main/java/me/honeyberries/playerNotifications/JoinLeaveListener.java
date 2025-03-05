package me.honeyberries.playerNotifications;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class JoinLeaveListener implements Listener {

    // Get the instance of the main plugin class for accessing data and methods
    Plugin plugin = PlayerNotifications.getInstance();

    /**
     * This method is called when a player joins the server.
     *
     * @param event The PlayerJoinEvent triggered when a player joins.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Run the playSound method asynchronously to avoid blocking the main thread.
        // Using Bukkit's scheduler to run the task on the main server thread.
        Bukkit.getScheduler().runTask(plugin, () -> playSound("join_sound.wav"));
    }

    /**
     * This method is called when a player leaves the server.
     *
     * @param event The PlayerQuitEvent triggered when a player quits.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Run the playSound method asynchronously to avoid blocking the main thread.
        // Using Bukkit's scheduler to run the task on the main server thread.
        Bukkit.getScheduler().runTask(plugin, () -> playSound("quit_sound.wav"));
    }


    /**
     * Plays a sound file specified by the filename.
     *
     * @param fileName The name of the sound file (e.g., "join_sound.wav").
     */
    private void playSound(String fileName) {
        // Start a new thread to load and play the sound asynchronously, preventing it from blocking other operations.
        new Thread(() -> {
            try {
                // Create a File object representing the sound file's path.
                // Sounds are expected to be in the plugin's data folder.
                File soundFile = new File(plugin.getDataFolder(), fileName);

                // Check if the sound file exists.
                if (soundFile.exists()) {
                    // Open the audio file as an input stream.
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

                    // Get a Clip object, which can be used to play audio data.
                    Clip clip = AudioSystem.getClip();

                    // Load the audio data from the stream into the clip.
                    clip.open(audioStream);

                    // Start playing the sound.
                    clip.start();
                    // Log a message indicating the sound is playing.
                    plugin.getLogger().info("Playing sound: " + fileName);

                } else {
                    // Log a warning if the sound file is not found.
                    plugin.getLogger().warning("Sound file not found: " + fileName);
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                // Log an error if any exception occurs during sound playback.
                plugin.getLogger().severe("Error playing sound: " + e.getMessage());
                // Print the stack trace for debugging.
                e.printStackTrace();
            }
        }).start(); // Start the thread to play the sound.
    }
}