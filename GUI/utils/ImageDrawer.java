package GUI.utils;

import GUI.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class ImageDrawer {
    private static final int SIZE = 15;

    private static final Image civilianImage = ImageLoader.loadImage("img/civilian.jpg");
    private static final Image medicImage = ImageLoader.loadImage("img/medic.jpg");
    private static final Image soldierImage = ImageLoader.loadImage("img/soldier.jpg");
    private static final Image engineerImage = ImageLoader.loadImage("img/engineer.jpg");

    private static final Image oneMap = ImageLoader.loadImage("img/map/1.png");
    private static final Image twoMap = ImageLoader.loadImage("img/map/2.png");
    private static final Image treeMap = ImageLoader.loadImage("img/map/3.png");
    
    private static void drawMap(Graphics g, int width, int height, int num) {
        switch (Frame.gameData.get(num).getRoad()){
            case 0 -> g.drawImage(oneMap, 0, 0, width,height, null);
            case 1 -> g.drawImage(twoMap, 0, 0, width,height, null);
            case 2 -> g.drawImage(treeMap, 0, 0,width,height,null);
        }
    }

    public static void drawImages(Graphics g, int num, int width, int height) {
        drawMap(g, width, height, num);
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
