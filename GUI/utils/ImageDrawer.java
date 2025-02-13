package GUI.utils;

import GUI.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class ImageDrawer {
    private static final int SIZE = 12;

    private static final Image civilianImage = ImageLoader.loadImage("img/civilian.jpg");
    private static final Image medicImage = ImageLoader.loadImage("img/medic.jpg");
    private static final Image soldierImage = ImageLoader.loadImage("img/soldier.jpg");
    private static final Image engineerImage = ImageLoader.loadImage("img/engineer.jpg");

    public static void drawImages(Graphics g, int num, int width, int height) {
        drawUnits(g, Frame.gameData.get(num).getCivilian(), civilianImage, width, height);
        drawUnits(g, Frame.gameData.get(num).getMedic(), medicImage, width, height);
        drawUnits(g, Frame.gameData.get(num).getSoldier(), soldierImage, width, height);
        drawUnits(g, Frame.gameData.get(num).getEngineer(), engineerImage, width, height);
    }

    private static void drawUnits(Graphics g, int unitCount, Image unitImage, int width, int height) {
        Random rand = new Random();
        for (int i = 0; i < unitCount; i++) {
            drawRandomImage(g, unitImage, rand, width, height);
        }
    }

    private static void drawRandomImage(Graphics g, Image image, Random rand, int width, int height) {
        int x = rand.nextInt(width - SIZE);
        int y = rand.nextInt(height - SIZE);
        g.drawImage(image, x, y, SIZE, SIZE, null);
    }
}
