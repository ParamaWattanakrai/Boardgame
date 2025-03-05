package gui.enums;

public enum SoundResource {
    Press("Press.wav"),
    Incorrect("Incorrect.wav"),
    MainMenu("MainMenu.wav"),
    Select("Select.wav"),
    NEXTTURN("nextturn.wav");

    private final String path;
    
    SoundResource(String path) {
        this.path = path;
    }

    public String getSound() {
        return path;
    }
}
