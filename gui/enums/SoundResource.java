package gui.enums;

public enum SoundResource {
    Press("Press.wav"),
    Incorrect("Incorrect.wav"),
    MainMenu("MainMenu.wav"),
    Select("Select.wav"),
    NEXTTURN("nextturn.wav"),

    // rule sound
    MISSION("Mission.wav"),
    BASICMECHANICS("BasicMechanics.wav"),
    WINCONDITION("WinCondition.wav"),
    FACILITIES("Facilities.wav"),
    ROLE1("Roles1.wav"),
    ROLE2("Roles2.wav"),
    MOVE("Move.wav"),
    ARM("Arm.wav"),
    SHOOT("Shoot.wav"),
    BUILD("Build.wav"),
    HEAL("Heal.wav"),
    GOODLUCK("Goodluck.wav");

    private final String path;

    SoundResource(String path) {
        this.path = path;
    }

    public String getSound() {
        return path;
    }
}
