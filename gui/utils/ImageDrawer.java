package gui.utils;

import gui.MainFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.Random;
import src.entities.Civilian;
import src.entities.Mechanic;
import src.entities.Medic;
import src.entities.Soldier;
import src.map.Block;
import src.map.PathType;
import src.utils.Direction;
import src.utils.Tuple;

public class ImageDrawer {
    private final int ENTITY_SIZE = 25;
    private final int LANDMARK_SIZE = 75;
    private final int BARRICADE_SIZE = 30;

    private final Image civilianImage = ImageLoader.loadImage("entities/civilian.png");
    private final Image medicImage = ImageLoader.loadImage("entities/medic.png");
    private final Image soldierImage = ImageLoader.loadImage("entities/soldier.png");
    private final Image mechanicImage = ImageLoader.loadImage("entities/mechanic.png");

    private final Image fourwayRoad = ImageLoader.loadImage("map/fourway.png");
    private final Image threewayRoad = ImageLoader.loadImage("map/threeway.png");
    private final Image curvedRoad = ImageLoader.loadImage("map/curved.png");
    private final Image straightRoad = ImageLoader.loadImage("map/straight.png");

    private final Image policeStation = ImageLoader.loadImage("landmark/1.png");
    private final Image nuclearPlant = ImageLoader.loadImage("landmark/2.png");
    private final Image hospital = ImageLoader.loadImage("landmark/3.png");
    private final Image store = ImageLoader.loadImage("landmark/4.png");

    private final Image barricadImage = ImageLoader.loadImage("Barricade.png");

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
        List<Civilian> civilian = mainFrame.getField().getBlock(new Tuple(x, y)).getAllCivilians();
        for (Civilian population : civilian) {
            if (population != null) {
                Image image = switch (population) {
                    case Mechanic _ -> mechanicImage;
                    case Medic _ -> medicImage;
                    case Soldier _ -> soldierImage;
                    default -> civilianImage;
                };
    
                int posX = new Random().nextInt(width - ENTITY_SIZE);
                int posY = new Random().nextInt(height - ENTITY_SIZE);
                g.drawImage(image, posX, posY, ENTITY_SIZE, ENTITY_SIZE, null);
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

    public void drawBarricade(Graphics g, int x, int y, int width, int height, MainFrame mainFrame){
        Graphics2D g2d;
        int centerX = (width - BARRICADE_SIZE*5) / 2;
        int Y = (height - BARRICADE_SIZE);

        Block block = mainFrame.getField().getBlock(new Tuple(x, y));
        if(block.getPath(Direction.NORTH).isBarricaded()) g.drawImage(barricadImage, centerX, 0, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        if(block.getPath(Direction.SOUTH).isBarricaded()) g.drawImage(barricadImage, centerX, Y, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        
        g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(90), width / 2, height / 2);
        if(block.getPath(Direction.EAST).isBarricaded()) g2d.drawImage(barricadImage, centerX, 0, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        g2d.dispose();

        g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(270), width / 2, height / 2);
        if(block.getPath(Direction.WEST).isBarricaded()) g2d.drawImage(barricadImage, centerX, 0, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        g2d.dispose();
    }
}
