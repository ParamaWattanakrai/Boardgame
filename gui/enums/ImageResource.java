package gui.enums;

import gui.utils.ImageLoader;
import java.awt.Image;

public enum ImageResource {
    LOGO("logo.png"),
    MENU_BACKGROUND("MainBg.png"),
    RULE_BACKGROUND("RuleBg.png"),
    GAME_BACKGROUND("GameBg.png"),

    DOG("entities/dog.png"),
    CIVILIAN("entities/civilian.png"),
    MEDIC("entities/medic.png"),
    SOLDIER("entities/soldier.png"),
    MECHANIC("entities/mechanic.png"),

    FOURWAY_ROAD("map/fourway.png"),
    THREEWAY_ROAD("map/threeway.png"),
    CURVED_ROAD("map/curved.png"),
    STRAIGHT_ROAD("map/straight.png"),

    POLICE_STATION("landmark/1.png"),
    NUCLEAR_PLANT("landmark/2.png"),
    HOSPITAL("landmark/3.png"),
    STORE("landmark/4.png"),

    BARRICADE("Barricade.png"),

    SETTING_RULE("settingRule.png");

    private final String path;
    private Image image;
    
    ImageResource(String path) {
        this.path = path;
        this.image = null;
    }
    
    public Image getImage() {
        if (image == null) {
            image = ImageLoader.loadImage(path);
        }
        return image;
    }
    
    public Image getScaledImage(int width, int height) {
        return getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}