package gui.utils;

import gui.MainFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import src.entities.Civilian;
import src.entities.Mechanic;
import src.entities.Medic;
import src.entities.Soldier;
import src.map.Block;
import src.map.PathType;
import src.utils.Tuple;

public class ImageDrawer {
    private final int POPULATION_SIZE = 15;
    private final int LANDMARK_SIZE = 75;

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

    public void drawRoad(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        Block block = mainFrame.getField().getBlock(new Tuple(x, y));
        int orientation = block.getOrientation();
        Graphics2D g2d = (Graphics2D) g.create();
            switch (block.getPathType()) {
                case FOURWAY -> g2d.drawImage(fourwayRoad, 0, 0, width, height, null);
                case THREEWAY, CURVED -> {
                    switch (orientation) {
                        case 1 -> g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                        case 2 -> g2d.rotate(Math.toRadians(180), width / 2, height / 2);
                        case 3 -> g2d.rotate(Math.toRadians(270), width / 2, height / 2);
                    }
                    g2d.drawImage(block.getPathType() == PathType.THREEWAY ? threewayRoad : curvedRoad, 0, 0, width, height, null);
                }
                case STRAIGHT -> {
                    if (orientation % 2 != 0) g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                    g2d.drawImage(straightRoad, 0, 0, width, height, null);
                }
            }
            g2d.dispose();
    }

    public void drawPopulation(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {    
        ArrayList<Civilian> populations = mainFrame.getField().getBlock(new Tuple(x, y)).getPopulation();
        for (Civilian population : populations) {
            if (population != null) {
                Image image = switch (population) {
                    case Mechanic _ -> mechanicImage;
                    case Medic _ -> medicImage;
                    case Soldier _ -> soldierImage;
                    default -> civilianImage;
                };
    
                int posX = new Random().nextInt(width - POPULATION_SIZE);
                int posY = new Random().nextInt(height - POPULATION_SIZE);
                g.drawImage(image, posX, posY, POPULATION_SIZE, POPULATION_SIZE, null);
            }
        }
    }
    
    public void drawLandmark(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {    
        Image image = switch (mainFrame.getField().getBlock(new Tuple(x, y)).getBlockType()) {
            case STORE -> store;
            case HOSPITAL -> hospital;
            case POLICESTATION -> policeStation;
            case POWERPLANT -> nuclearPlant;
            default -> null;
        };
    
        int centerX = x + (width - LANDMARK_SIZE) / 2;
        int centerY = y + (height - LANDMARK_SIZE) / 2;
        g.drawImage(image, centerX, centerY, LANDMARK_SIZE, LANDMARK_SIZE, null);
    }
}
