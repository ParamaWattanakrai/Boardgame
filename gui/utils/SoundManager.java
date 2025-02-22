package gui.utils;

public class SoundManager {
    public static void playButtonClickSound() {
        SoundPlayer.playSound("Press.wav");
    }

    public static void playIncorrectSound() {
        SoundPlayer.playSound("Incorrect.wav");
    }

    public static void playMainMenuMusic() {
        SoundPlayer.loopSound("MainMenu.wav");
    }
    public static void playSelectSound() {
        SoundPlayer.playSound("Select.wav");
    }
}
