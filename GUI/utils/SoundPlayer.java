package gui.utils;

import java.io.File;
import javax.sound.sampled.*;

public class SoundPlayer {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    
}
