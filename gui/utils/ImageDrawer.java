package gui.utils;

import gui.MainFrame;
import gui.enums.ImageResource;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import src.entities.Civilian;
import src.entities.Dog;
import src.entities.Vitality;
import src.map.Block;
import src.map.PathType;
import src.utils.Direction;
import src.utils.Tuple;

public class ImageDrawer {
    private final int ENTITY_SIZE = 50;
    private final int LANDMARK_SIZE = 75;
    private final int BARRICADE_SIZE = 20;

    public void drawRoad(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        Block block = mainFrame.getField().getBlock(new Tuple(x, y));
        int orientation = block.getOrientation();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        switch (block.getPathType()) {
            case FOURWAY -> g2d.drawImage(ImageResource.FOURWAY_ROAD.getImage(), 0, 0, width, height, null);
            case THREEWAY, CURVED -> {
                switch (orientation) {
                    case 1 -> g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                    case 2 -> g2d.rotate(Math.toRadians(180), width / 2, height / 2);
                    case 3 -> g2d.rotate(Math.toRadians(270), width / 2, height / 2);
                }
                g2d.drawImage(block.getPathType() == PathType.THREEWAY ? ImageResource.THREEWAY_ROAD.getImage()
                        : ImageResource.CURVED_ROAD.getImage(), 0, 0, width, height, null);
            }
            case STRAIGHT -> {
                if (orientation % 2 != 0)
                    g2d.rotate(Math.toRadians(90), width / 2, height / 2);
                g2d.drawImage(ImageResource.STRAIGHT_ROAD.getImage(), 0, 0, width, height, null);
            }
        }
        g2d.dispose();
    }

    public void drawPopulation(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        List<Civilian> civilianList = mainFrame.getField().getBlock(new Tuple(x, y)).getAllCivilians();
        mainFrame.getField().getBlock(new Tuple(x, y)).getAllCivilians().forEach(Civilian::arm);

        for (Civilian civilian : civilianList) {
            if (civilian != null) {
                if (civilian.isContacted()) {
                    mainFrame.getField().getBlock(new Tuple(x, y)).getAllCivilians().forEach(Civilian::contact);
                    break;
                }
            }
        }
        
        for (Civilian civilian : civilianList) {
            if (civilian != null && (civilian.getVitality() == Vitality.COMA || civilian.getVitality() == Vitality.ALIVE)) {
                Image image = switch (civilian.getEntityType()) {
                    case MECHANIC -> {
                        if (civilian.isContacted()) {
                            if (civilian.isArmed()) {
                                yield ImageResource.MECHANIC_ARM.getImage();
                            } else if (civilian.getVitality() == Vitality.COMA) {
                                yield ImageResource.MECHANIC_COMA.getImage();
                            }else {
                                yield ImageResource.MECHANIC.getImage();
                            }
                        } else {
                            yield ImageResource.MECHANIC_NO_CONTACT.getImage();
                        }
                    }

                    case MEDIC -> {
                        if (civilian.isArmed()) {
                            yield ImageResource.MEDIC_ARM.getImage();
                        } else if (civilian.getVitality() == Vitality.COMA) {
                            yield ImageResource.MEDIC_COMA.getImage();
                        } else {
                            yield ImageResource.MEDIC.getImage();
                        }
                    }
                    case SOLDIER -> {
                        if (civilian.getVitality() == Vitality.COMA){
                            yield ImageResource.SOLDIER_COMA.getImage();
                        }else{
                            yield ImageResource.SOLDIER.getImage();
                        }
                    }
                    default -> {
                        if (civilian.isContacted()) {
                            if (civilian.isArmed()) {
                                yield ImageResource.CIVILIAN_ARM.getImage();
                            } else if (civilian.getVitality() == Vitality.COMA) {
                                yield ImageResource.CIVILIAN_COMA.getImage();
                            }else {
                                yield ImageResource.CIVILIAN.getImage();
                            }
                        } else {
                            yield ImageResource.CIVILIAN_NO_CONTACT.getImage();
                        }
                    }
                };

                int posX = civilian.getPixelCoordinate().getA();
                int posY = civilian.getPixelCoordinate().getB();
                g.drawImage(image, posX, posY, ENTITY_SIZE, ENTITY_SIZE, null);
            }
        }

    }

    public void drawDog(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        List<Dog> dogList = mainFrame.getField().getBlock(new Tuple(x, y)).getAllDogs();
        for (Dog dog : dogList) {
            if (dog != null) {
                int posX = dog.getPixelCoordinate().getA();
                int posY = dog.getPixelCoordinate().getB();
                g.drawImage(ImageResource.DOG.getImage(), posX, posY, ENTITY_SIZE, ENTITY_SIZE, null);
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

    public void drawBarricade(Graphics g, int x, int y, int width, int height, MainFrame mainFrame) {
        Graphics2D g2d;
        int Y = (height - BARRICADE_SIZE);
        Block block = mainFrame.getField().getBlock(new Tuple(x, y));

        if (block.getPath(Direction.NORTH).isBarricaded()){
            g.drawImage(ImageResource.BARRICADE.getImage(), 0, 0, width*2, BARRICADE_SIZE, null);
        
        }   
        if (block.getPath(Direction.SOUTH).isBarricaded()){
            g.drawImage(ImageResource.BARRICADE.getImage(), 0, Y, width*2, BARRICADE_SIZE, null);
        }

        if (block.getPath(Direction.EAST).isBarricaded()) {
            g2d = (Graphics2D) g.create();
            g2d.rotate(Math.toRadians(90), width / 2, height / 2);
            g2d.drawImage(ImageResource.BARRICADE.getImage(), 0, 0, width*2, BARRICADE_SIZE, null);
            g2d.dispose();
        }
       
        if (block.getPath(Direction.WEST).isBarricaded()) {
            g2d = (Graphics2D) g.create();
            g2d.rotate(Math.toRadians(270), width / 2, height / 2);
            g2d.drawImage(ImageResource.BARRICADE.getImage(), 0, 0,  width*2, BARRICADE_SIZE, null);
            g2d.dispose();
        }
    }
}
