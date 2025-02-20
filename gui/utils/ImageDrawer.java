package gui.utils;

import gui.MainFrame;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class ImageDrawer {
        private static final Image civilianImage = ImageLoader.loadImage("entities/civilian.jpg");
        private static final Image medicImage = ImageLoader.loadImage("entities/medic.jpg");
        private static final Image soldierImage = ImageLoader.loadImage("entities/soldier.jpg");
        private static final Image engineerImage = ImageLoader.loadImage("entities/engineer.jpg");
    
        private static final Image oneMap = ImageLoader.loadImage("map/1.png");
        private static final Image twoMap = ImageLoader.loadImage("map/2.png");
        private static final Image treeMap = ImageLoader.loadImage("map/3.png");
    
        private static final Image policeStation = ImageLoader.loadImage("landmark/1.png");
        private static final Image nuclearPlant = ImageLoader.loadImage("landmark/2.png");
        private static final Image hospital = ImageLoader.loadImage("landmark/3.png");
        private static final Image store = ImageLoader.loadImage("landmark/4.png");
    
        private static void drawMap(Graphics g, int width, int height, int num) {
            switch (MainFrame.getRoadData().get(num).getRoad()){
                case 0 -> g.drawImage(oneMap, 0, 0, width,height, null);
                case 1 -> g.drawImage(twoMap, 0, 0, width,height, null);
                case 2 -> g.drawImage(treeMap, 0, 0,width,height,null);
            }
        }
    
        public static void drawImages(Graphics g, int num, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            drawMap(g2d, width, height, num);
            drawLandmark(g, num, width, height);
            drawUnits(g, MainFrame.getRoadData().get(num).getCivilian(), civilianImage, width, height);
            drawUnits(g, MainFrame.getRoadData().get(num).getMedic(), medicImage, width, height);
            drawUnits(g, MainFrame.getRoadData().get(num).getSoldier(), soldierImage, width, height);
            drawUnits(g, MainFrame.getRoadData().get(num).getEngineer(), engineerImage, width, height);
        }
    
        private static void drawLandmark(Graphics g, int num, int width, int height) {
            int Landmark_SIZE = 60;
            switch (MainFrame.getRoadData().get(num).getLandmark()){
                case 1 -> drawRandomImage(g, policeStation, Landmark_SIZE, width, height);
                case 2 -> drawRandomImage(g, nuclearPlant, Landmark_SIZE, width, height);
                case 3 -> drawRandomImage(g, hospital, Landmark_SIZE, width, height);
                case 4 -> drawRandomImage(g, store, Landmark_SIZE, width, height);
        }
    }

    private static void drawUnits(Graphics g, int unitCount, Image unitImage, int width, int height) {
        for (int i = 0; i < unitCount; i++) {
            drawRandomImage(g, unitImage, 15, width, height);
        }
    }

    private static void drawRandomImage(Graphics g, Image image, int SIZE, int width, int height) {
        Random rand = new Random();
        int x = rand.nextInt(width - SIZE);
        int y = rand.nextInt(height - SIZE);

        g.drawImage(image, x, y, SIZE, SIZE, null);
    }
}
