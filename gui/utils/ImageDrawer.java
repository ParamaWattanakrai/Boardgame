package gui.utils;

import gui.MainFrame;
import src.utils.Tuple;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class ImageDrawer {
        private static final Image civilianImage = ImageLoader.loadImage("entities/civilian.jpg");
        private static final Image medicImage = ImageLoader.loadImage("entities/medic.jpg");
        private static final Image soldierImage = ImageLoader.loadImage("entities/soldier.jpg");
        private static final Image mechanicImage = ImageLoader.loadImage("entities/mechanic.jpg");
    
        private static final Image fourwayRoad = ImageLoader.loadImage("map/fourway.png");
        private static final Image threewayRoad = ImageLoader.loadImage("map/threeway.png");
        private static final Image curvedRoad = ImageLoader.loadImage("map/curved.png");
        private static final Image straightRoad = ImageLoader.loadImage("map/straight.png");
    
        private static final Image policeStation = ImageLoader.loadImage("landmark/1.png");
        private static final Image nuclearPlant = ImageLoader.loadImage("landmark/2.png");
        private static final Image hospital = ImageLoader.loadImage("landmark/3.png");
        private static final Image store = ImageLoader.loadImage("landmark/4.png");
    
        private static void drawMap(Graphics g, int width, int height, int x, int y) {
            switch (MainFrame.getField().getBlock(new Tuple(x, y)).getPathType()) {
                case FOURWAY -> g.drawImage(fourwayRoad, 0, 0, width, height, null);
                case THREEWAY -> g.drawImage(threewayRoad, 0, 0, width, height, null);
                case CURVED -> g.drawImage(curvedRoad, 0, 0,width, height,null);
                case STRAIGHT -> g.drawImage(straightRoad, 0, 0,width, height,null);
            }
        }
    
        public static void drawImages(Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            drawMap(g2d, width, height, x, y);
            drawLandmark(g, x, y, width, height);
            drawUnits(g, MainFrame.getField().getBlock(new Tuple(x, y)).getCivilians().size(), civilianImage, width, height);
            drawUnits(g, MainFrame.getField().getBlock(new Tuple(x, y)).getSoldiers().size(), soldierImage, width, height);
            drawUnits(g, MainFrame.getField().getBlock(new Tuple(x, y)).getMedics().size(), medicImage, width, height);
            drawUnits(g, MainFrame.getField().getBlock(new Tuple(x, y)).getMechanics().size(), mechanicImage, width, height);
        }
    
        private static void drawLandmark(Graphics g, int x, int y, int width, int height) {
            int Landmark_SIZE = 60;
            switch (MainFrame.getField().getBlock(new Tuple(x, y)).getBlockType()){
                            case STORE -> drawRandomImage(g, store, Landmark_SIZE, width, height);
                            case HOSPITAL -> drawRandomImage(g, hospital, Landmark_SIZE, width, height);
                            case POLICESTATION -> drawRandomImage(g, policeStation, Landmark_SIZE, width, height);
                            case POWERPLANT -> drawRandomImage(g, nuclearPlant, Landmark_SIZE, width, height);
                            case DEFAULT -> System.out.println("");
                            case SPAWN -> System.out.println("");
                            default -> throw new IllegalArgumentException("Unexpected value: " + MainFrame.getField().getBlock(new Tuple(x, y)).getBlockType());
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
