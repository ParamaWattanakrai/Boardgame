package gui.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;

public class SoundPlayer {
    private static HashMap<String, Clip> activeClips = new HashMap<>();

    public static void playSound(String soundFile) {
        try {
            File file = new File("gui/assets/sfx/" + soundFile);
            if (!file.exists()) {
                System.out.println("Error: File not found -> " + file.getAbsolutePath());
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            activeClips.put(soundFile, clip); // Store clip with soundFile as key
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void loopSound(String soundFile) {
        try {
            File file = new File("gui/assets/sfx/" + soundFile);
            if (!file.exists()) {
                System.out.println("Error: File not found -> " + file.getAbsolutePath());
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            activeClips.put(soundFile, clip); // Store clip with soundFile as key
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    public static void stopSound() {
        // Iterate through the values (Clips) in the HashMap
        for (Clip clip : activeClips.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close(); // Close the clip to release resources
            }
        }
        activeClips.clear(); // Clear the HashMap as all sounds are stopped
    }
    public static void stopSpecificSound(String soundFile) {
        if (activeClips.containsKey(soundFile)) {
            Clip clip = activeClips.get(soundFile);
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.close();
            activeClips.remove(soundFile);
            System.out.println("Stopped specific sound: " + soundFile);
        } else {
            System.out.println("Sound not currently playing: " + soundFile);
        }
    }
}
