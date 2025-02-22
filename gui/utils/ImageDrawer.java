package gui.utils;

import gui.MainFrame;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import src.utils.Tuple;

public class ImageDrawer {
    private final Image civilianImage = ImageLoader.loadImage("entities/civilian.jpg");
    private final Image medicImage = ImageLoader.loadImage("entities/medic.jpg");
    private final Image soldierImage = ImageLoader.loadImage("entities/soldier.jpg");
    private final Image mechanicImage = ImageLoader.loadImage("entities/mechanic.jpg");

    private final Image fourwayRoad = ImageLoader.loadImage("map/fourway.png");
    private final Image threewayRoad = ImageLoader.loadImage("map/threeway.png");
    private final Image curvedRoad = ImageLoader.loadImage("map/curved.png");
    private final Image straightRoad = ImageLoader.loadImage("map/straight.png");

    private final Image policeStation = ImageLoader.loadImage("landmark/1.png");
    private final Image nuclearPlant = ImageLoader.loadImage("landmark/2.png");
    private final Image hospital = ImageLoader.loadImage("landmark/3.png");
    private final Image store = ImageLoader.loadImage("landmark/4.png");

    public void draw(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        drawImages(g, x, y, width, height, mainFrame);
    }

    private void drawMap(Graphics g, int width, int height, int x, int y, MainFrame mainFrame) {
        int orientation = mainFrame.getField().getBlock(new Tuple(x, y)).getOrientation();
        Graphics2D g2d = (Graphics2D) g;
            switch (mainFrame.getField().getBlock(new Tuple(x, y)).getPathType()) {
                case FOURWAY -> g2d.drawImage(fourwayRoad, 0, 0, width, height, null);
                case THREEWAY -> {
                    switch (orientation) {
                        case 1 -> g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                        case 2 -> g2d.rotate(Math.toRadians(180), width / 2, height / 2);
                        case 3 -> g2d.rotate(Math.toRadians(270), width / 2, height / 2);

                    }
                    g2d.drawImage(threewayRoad, 0, 0, width, height, null);
                }
                case CURVED -> {
                    switch (orientation) {
                        case 1 -> g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                        case 2 -> g2d.rotate(Math.toRadians(180), width / 2, height / 2);
                        case 3 -> g2d.rotate(Math.toRadians(270), width / 2, height / 2);
                    }
                    g2d.drawImage(curvedRoad, 0, 0, width, height, null);
                }
                case STRAIGHT -> {
                    if (orientation % 2 != 0) g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                    g2d.drawImage(straightRoad, 0, 0, width, height, null);
                }
            }
    }

    private void drawImages(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        drawMap(g2d, width, height, x, y, mainFrame);
        drawLandmark(g, x, y, width, height, mainFrame);
        drawUnits(g, mainFrame.getField().getBlock(new Tuple(x, y)).getCivilians().size(), civilianImage, width,height);
        drawUnits(g, mainFrame.getField().getBlock(new Tuple(x, y)).getSoldiers().size(), soldierImage, width, height);
        drawUnits(g, mainFrame.getField().getBlock(new Tuple(x, y)).getMedics().size(), medicImage, width, height);
        drawUnits(g, mainFrame.getField().getBlock(new Tuple(x, y)).getMechanics().size(), mechanicImage, width,height);
    }

    private void drawLandmark(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        int Landmark_SIZE = 60;
        switch (mainFrame.getField().getBlock(new Tuple(x, y)).getBlockType()) {
                    case STORE -> drawRandomImage(g, store, Landmark_SIZE, width, height);
                    case HOSPITAL -> drawRandomImage(g, hospital, Landmark_SIZE, width, height);
                    case POLICESTATION -> drawRandomImage(g, policeStation, Landmark_SIZE, width, height);
                    case POWERPLANT -> drawRandomImage(g, nuclearPlant, Landmark_SIZE, width, height);
                    default -> System.out.print("");
        }
    }

    private void drawUnits(Graphics g, int unitCount, Image unitImage, int width, int height) {
        for (int i = 0; i < unitCount; i++) {
            drawRandomImage(g, unitImage, 15, width, height);
        }
    }

    private void drawRandomImage(Graphics g, Image image, int SIZE, int width, int height) {
        Random rand = new Random();
        int x = rand.nextInt(width - SIZE);
        int y = rand.nextInt(height - SIZE);

        g.drawImage(image, x, y, SIZE, SIZE, null);
    }
}
