package gui.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;

public class SoundPlayer {
    private static List<Clip> activeClips = new ArrayList<>();

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
            activeClips.add(clip);
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
            activeClips.add(clip);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    public static void stopSound(){
        for(Clip clip : activeClips){
            if(clip != null && clip.isRunning()){
                clip.stop();
            }
        }
        activeClips.clear();
    }
}
