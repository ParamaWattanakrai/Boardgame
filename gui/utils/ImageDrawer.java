package gui.utils;

import gui.MainFrame;
import gui.enums.ImageResource;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import src.entities.Entity;
import src.entities.EntityType;
import src.map.Block;
import src.map.PathType;
import src.utils.Direction;
import src.utils.Tuple;

public class ImageDrawer {
    private final int ENTITY_SIZE = 50;
    private final int LANDMARK_SIZE = 75;
    private final int BARRICADE_SIZE = 30;

    public void drawRoad(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        Block block = mainFrame.getField().getBlock(new Tuple(x, y));
        int orientation = block.getOrientation();
        Graphics2D g2d = (Graphics2D) g.create();
            switch (block.getPathType()) {
                case FOURWAY -> g2d.drawImage(ImageResource.FOURWAY_ROAD.getImage(), 0, 0, width, height, null);
                case THREEWAY, CURVED -> {
                    switch (orientation) {
                        case 1 -> g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                        case 2 -> g2d.rotate(Math.toRadians(180), width / 2, height / 2);
                        case 3 -> g2d.rotate(Math.toRadians(270), width / 2, height / 2);
                    }
                    g2d.drawImage(block.getPathType() == PathType.THREEWAY ? ImageResource.THREEWAY_ROAD.getImage() : ImageResource.CURVED_ROAD.getImage(), 0, 0, width, height, null);
                }
                case STRAIGHT -> {
                    if (orientation % 2 != 0) g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                    g2d.drawImage(ImageResource.STRAIGHT_ROAD.getImage(), 0, 0, width, height, null);
                }
            }
            g2d.dispose();
    }

    public void drawPopulation(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {    
        List<Entity> entityList = mainFrame.getField().getBlock(new Tuple(x, y)).getAllEntities();
        for (Entity entity : entityList) {
            if (entity != null) {
                Image image = switch (entity.getEntityType()) {
                    case EntityType.DOG -> ImageResource.DOG.getImage();
                    case EntityType.MECHANIC -> ImageResource.MECHANIC.getImage();
                    case EntityType.MEDIC -> ImageResource.MEDIC.getImage();
                    case EntityType.SOLDIER -> ImageResource.SOLDIER.getImage();
                    default -> ImageResource.CIVILIAN.getImage();
                };
    
                int posX = entity.getPixelCoordinate().getA();
                int posY = entity.getPixelCoordinate().getB();
                g.drawImage(image, posX, posY, ENTITY_SIZE, ENTITY_SIZE, null);
            }
        }
    }
    
    public void drawLandmark(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {    
        Image image = switch (mainFrame.getField().getBlock(new Tuple(x, y)).getBlockType()) {
            case STORE -> ImageResource.STORE.getImage();
            case HOSPITAL -> ImageResource.HOSPITAL.getImage();
            case POLICESTATION -> ImageResource.POLICE_STATION.getImage();
            case POWERPLANT -> ImageResource.NUCLEAR_PLANT.getImage();
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
        if(block.getPath(Direction.NORTH).isBarricaded()) g.drawImage(ImageResource.BARRICADE.getImage(), centerX, 0, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        if(block.getPath(Direction.SOUTH).isBarricaded()) g.drawImage(ImageResource.BARRICADE.getImage(), centerX, Y, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        
        g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(90), width / 2, height / 2);
        if(block.getPath(Direction.EAST).isBarricaded()) g2d.drawImage(ImageResource.BARRICADE.getImage(), centerX, 0, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        g2d.dispose();

        g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(270), width / 2, height / 2);
        if(block.getPath(Direction.WEST).isBarricaded()) g2d.drawImage(ImageResource.BARRICADE.getImage(), centerX, 0, BARRICADE_SIZE*5, BARRICADE_SIZE, null);
        g2d.dispose();
    }
}
