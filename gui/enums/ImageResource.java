package gui.enums;

import gui.utils.ImageLoader;
import java.awt.Image;
import javax.swing.ImageIcon;

public enum ImageResource {
    LOGO("logo.png"),
    MENU_BACKGROUND("MainBg.png"),
    RULE_BACKGROUND("RuleBg.png"),
    GAME_BACKGROUND("GameBg.png"),

    GUN_ANI("animation/gun.gif"),

    MAP("Map.png"),
    DOG("entities/dog.png"),

    CIVILIAN("entities/civilian.png"),
    MEDIC("entities/medic.png"),
    SOLDIER("entities/soldier.png"),
    MECHANIC("entities/mechanic.png"),

    CIVILIAN_COMA("entities/civilian_coma.png"),
    MEDIC_COMA("entities/medic_coma.png"),
    SOLDIER_COMA("entities/soldier_coma.png"),
    MECHANIC_COMA("entities/mechanic_coma.png"),
    CIVILIAN_NO_CONTACT_COMA("entities/civilian_no_coma.png"),

    CIVILIAN_NO_CONTACT("entities/civilian_no.png"),
    MECHANIC_NO_CONTACT("entities/mechanic_no.png"),

    CIVILIAN_ARM("entities/civilian_arm.png"),
    MEDIC_ARM("entities/medic_arm.png"),
    MECHANIC_ARM("entities/mechanic_arm.png"),

    FOURWAY_ROAD("map/fourway.png"),
    THREEWAY_ROAD("map/threeway.png"),
    CURVED_ROAD("map/curved.png"),
    STRAIGHT_ROAD("map/straight.png"),

    POLICE_STATION("landmark/1.png"),
    NUCLEAR_PLANT("landmark/2.png"),
    HOSPITAL("landmark/3.png"),
    STORE("landmark/4.png"),

    BARRICADE("Barricade.png"),

    SETTING_GAME("GameSetting.png"),
    SETTING_RULE("RuleSetting.png"),

    SHOOT("shoot.png"),

    RULEPAGE1("RulePage/Operation.png"),
    RULEPAGE2("RulePage/Facilities.png"),
    RULEPAGE3("RulePage/Role1.png"),
    RULEPAGE4("RulePage/Role2.png"),
    RULEPAGE5("RulePage/ActionsMove.png"),
    RULEPAGE6("RulePage/WinConditions.png");

    private final String path;
    private Image image;
    private ImageIcon imageIcon;

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

    public ImageIcon getIcon() {
        if (imageIcon == null) {
            imageIcon = ImageLoader.loadIcon(path);
        }
        return imageIcon;
    }

    public Image getScaledImage(int width, int height) {
        return getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public ImageIcon getScaledIcon(int width, int height) {
        return new ImageIcon(getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}