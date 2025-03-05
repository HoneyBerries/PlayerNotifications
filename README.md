# PlayerNotifications

## 📢 About
**PlayerNotifications** is a Minecraft PaperMC plugin that plays a sound on the **server's computer** when a player joins or leaves the server.

## 🔊 Features
- Plays `join_sound.wav` when a player joins.
- Plays `quit_sound.wav` when a player leaves.
- Runs sound playback asynchronously to prevent lag.

## 🛠️ Installation
1. Download the latest `PlayerNotifications.jar` from the [Releases](https://github.com/HoneyBerries/PlayerNotifications/releases).
2. Place the `.jar` file in your PaperMC server's `plugins/` folder.
3. Restart the server.
4. Place your `join_sound.wav` and `quit_sound.wav` in the plugin's data folder (`plugins/PlayerNotifications/`).
5. Enjoy!

## ⚙️ Configuration
Currently, there is no configuration file. Just replace `join_sound.wav` and `quit_sound.wav` with your own sounds.

## 📜 Code Example
```java
private void playSound(String fileName) {
    new Thread(() -> {
        try {
            File soundFile = new File(plugin.getDataFolder(), fileName);
            if (!soundFile.exists()) {
                plugin.getLogger().warning("Sound file not found: " + fileName);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            plugin.getLogger().severe("Error playing sound: " + e.getMessage());
            e.printStackTrace();
        }
    }).start();
}
```

## 🎵 Sound Credits
Thanks to **Devrinta Rose Nataya** and **Rasool Asaad** for the sound effects from [Pixabay](https://pixabay.com/).

## 📜 License
This project is licensed under the **MIT License**. Feel free to modify and share!

