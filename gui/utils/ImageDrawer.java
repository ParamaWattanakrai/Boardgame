package gui.utils;

import gui.MainFrame;
import gui.components.WorldMap;
import gui.components.WorldMap.Road;
import gui.enums.ImageResource;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import src.entities.Vitality;
import src.map.Block;
import src.map.PathType;
import src.utils.Direction;
import src.utils.Tuple;

public class ImageDrawer {
    private final int ENTITY_SIZE = 50;
    private final int LANDMARK_SIZE = 75;
    private final int BARRICADE_SIZE = 20;
    private final int HEIGHT = 180;
    private final int WIDTH = 180;

    public void drawRoad(Graphics g, WorldMap maps, MainFrame mainFrame) {
        for (Road[] roads : maps.getAllRoad()) {
            for (Road road : roads) {
                Block block = mainFrame.getField().getBlock(new Tuple(road.getGridX(), road.getGridY()));
                int x = road.getX();
                int y = road.getY();
                int orientation = block.getOrientation();

                // ตรวจสอบว่า block.getPathType() ไม่เป็น null ก่อนใช้งาน
                PathType pathType = block.getPathType();
                if (pathType == null) {
                    continue; // ข้ามการวาดถนนสำหรับบล็อกที่ไม่มี PathType
                }

                double rotationAngle = 0;
                boolean rotate = false;

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                switch (pathType) {
                    case FOURWAY -> g2d.drawImage(ImageResource.FOURWAY_ROAD.getImage(), x, y, WIDTH, HEIGHT, null);
                    case THREEWAY, CURVED -> {
                        rotate = true;
                        switch (orientation) {
                            case 1 -> rotationAngle = Math.toRadians(90);
                            case 2 -> rotationAngle = Math.toRadians(180);
                            case 3 -> rotationAngle = Math.toRadians(270);
                        }
                    }
                    case STRAIGHT -> {
                        if (orientation % 2 != 0) {
                            rotate = true;
                            rotationAngle = Math.toRadians(90);
                        }
                    }
                }

                if (rotate)
                    g2d.rotate(rotationAngle, x + WIDTH / 2, y + HEIGHT / 2);
                g2d.drawImage(switch (pathType) {
                    case THREEWAY -> ImageResource.THREEWAY_ROAD.getImage();
                    case CURVED -> ImageResource.CURVED_ROAD.getImage();
                    case STRAIGHT -> ImageResource.STRAIGHT_ROAD.getImage();
                    default -> null;
                }, x, y, WIDTH, HEIGHT, null);
                g2d.dispose();
            }
        }
    }

    public void drawPopulation(Graphics g, WorldMap maps, MainFrame mainFrame) {
        mainFrame.getField().getAllCivilians().forEach((civilian) -> {
            if (civilian.getVitality() == Vitality.COMA || civilian.getVitality() == Vitality.ALIVE) {
                Image image = switch (civilian.getEntityType()) {
                    case MECHANIC -> {
                        if (civilian.isContacted()) {
                            if (civilian.getVitality() == Vitality.COMA) {
                                yield ImageResource.MECHANIC_COMA.getImage();
                            } else if (civilian.isArmed()) {
                                yield ImageResource.MECHANIC_ARM.getImage();
                            } else {
                                yield ImageResource.MECHANIC.getImage();
                            }
                        } else {
                            if (civilian.getVitality() == Vitality.COMA) {
                                yield ImageResource.CIVILIAN_NO_CONTACT_COMA.getImage();
                            } else {
                                yield ImageResource.MECHANIC_NO_CONTACT.getImage();
                            }
                        }
                    }

                    case MEDIC -> {
                        if (civilian.getVitality() == Vitality.COMA) {
                            yield ImageResource.MEDIC_COMA.getImage();
                        } else if (civilian.isArmed()) {
                            yield ImageResource.MEDIC_ARM.getImage();
                        } else {
                            yield ImageResource.MEDIC.getImage();
                        }
                    }
                    case SOLDIER -> {
                        if (civilian.getVitality() == Vitality.COMA) {
                            yield ImageResource.SOLDIER_COMA.getImage();
                        } else {
                            yield ImageResource.SOLDIER.getImage();
                        }
                    }
                    default -> {
                        if (civilian.isContacted()) {
                            if (civilian.getVitality() == Vitality.COMA) {
                                yield ImageResource.CIVILIAN_COMA.getImage();
                            } else if (civilian.isArmed()) {
                                yield ImageResource.CIVILIAN_ARM.getImage();
                            } else {
                                yield ImageResource.CIVILIAN.getImage();
                            }
                        } else {
                            if (civilian.getVitality() == Vitality.COMA) {
                                yield ImageResource.CIVILIAN_NO_CONTACT_COMA.getImage();
                            } else {
                                yield ImageResource.CIVILIAN_NO_CONTACT.getImage();
                            }
                        }
                    }
                };

                Road road = maps.getRoad(civilian.getBlock().getCoordinate().getA(), civilian.getBlock().getCoordinate().getB());
                int posX = civilian.getPixelCoordinate().getA() + road.getX();
                int posY = civilian.getPixelCoordinate().getB() + road.getY();
                Tuple previouPos = civilian.getPreviousCoordinate();
                Tuple currentPos = new Tuple(posX, posY);

                if (previouPos != null && !previouPos.equals(currentPos)) {
                    double speed = 0.1;
                    int newPrevX = (int) (previouPos.getA() + (posX - previouPos.getA()) * speed);
                    int newPrevY = (int) (previouPos.getB() + (posY - previouPos.getB()) * speed);
                    g.drawImage(image, newPrevX, newPrevY, ENTITY_SIZE, ENTITY_SIZE, null);
                    civilian.setPreviousCoordinate(new Tuple(newPrevX, newPrevY));
                    maps.repaint();
                } else {
                    g.drawImage(image, posX, posY, ENTITY_SIZE, ENTITY_SIZE, null);
                    civilian.setPreviousCoordinate(new Tuple(posX, posY));
                }
            }
        });
    }

    public void drawDog(Graphics g, WorldMap maps, MainFrame mainFrame) {
        mainFrame.getField().getAllDog().forEach((dog) -> {
            Road road = maps.getRoad(dog.getBlock().getCoordinate().getA(), dog.getBlock().getCoordinate().getB());
            int posX = dog.getPixelCoordinate().getA() + road.getX();
            int posY = dog.getPixelCoordinate().getB() + road.getY();
            Tuple previousPos = dog.getPreviousCoordinate();
            if (previousPos == null) {
                if (dog.getAlpha() < 0.9f) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, dog.getAlpha()));
                    g2d.drawImage(ImageResource.DOG.getImage(), posX, posY, ENTITY_SIZE, ENTITY_SIZE, null);
                    dog.setAlpha(dog.getAlpha()+0.05f);
                    g2d.dispose();
                    maps.repaint();
                } else {
                    dog.setPreviousCoordinate(new Tuple(posX, posY));
                }
            } else {
                double speed = 0.1;
                int newPrevX = (int) (previousPos.getA() + (posX - previousPos.getA()) * speed);
                int newPrevY = (int) (previousPos.getB() + (posY - previousPos.getB()) * speed);
                g.drawImage(ImageResource.DOG.getImage(), newPrevX, newPrevY, ENTITY_SIZE, ENTITY_SIZE, null);
                dog.setPreviousCoordinate(new Tuple(newPrevX, newPrevY));
                maps.repaint();
            }
        });
    }

    public void drawLandmark(Graphics g, WorldMap maps, MainFrame mainFrame) {
        for (Road[] Roads : maps.getAllRoad()) {
            for (Road Road : Roads) {
                Image image = switch (mainFrame.getField().getBlock(new Tuple(Road.getGridX(), Road.getGridY()))
                        .getBlockType()) {
                    case STORE -> ImageResource.STORE.getImage();
                    case HOSPITAL -> ImageResource.HOSPITAL.getImage();
                    case POLICESTATION -> ImageResource.POLICE_STATION.getImage();
                    case POWERPLANT -> ImageResource.NUCLEAR_PLANT.getImage();
                    default -> null;
                };

                int centerX = Road.getX() + (WIDTH - LANDMARK_SIZE) / 2;
                int centerY = Road.getY() + (HEIGHT - LANDMARK_SIZE) / 2;
                g.drawImage(image, centerX, centerY, LANDMARK_SIZE, LANDMARK_SIZE, null);
            }
        }
    }

    public void drawBarricade(Graphics g, WorldMap maps, MainFrame mainFrame) {
        for (Road[] Roads : maps.getAllRoad()) {
            for (Road Road : Roads) {
                int x = Road.getX();
                int y = Road.getY();
                int Y = (HEIGHT - BARRICADE_SIZE);

                Block block = mainFrame.getField().getBlock(new Tuple(Road.getGridX(), Road.getGridY()));
                if (block.getPath(Direction.NORTH).isBarricaded()) {
                    g.drawImage(ImageResource.BARRICADE.getImage(), x, y, WIDTH, BARRICADE_SIZE, null);
                }
                if (block.getPath(Direction.SOUTH).isBarricaded()) {
                    g.drawImage(ImageResource.BARRICADE.getImage(), x, y + Y, WIDTH, BARRICADE_SIZE, null);
                }

                if (block.getPath(Direction.EAST).isBarricaded()) {
                    Graphics2D g2d;
                    g2d = (Graphics2D) g.create();
                    g2d.rotate(Math.toRadians(90), x + WIDTH / 2, y + HEIGHT / 2);
                    g2d.drawImage(ImageResource.BARRICADE.getImage(), x, y, WIDTH, BARRICADE_SIZE, null);
                    g2d.dispose();
                }

                if (block.getPath(Direction.WEST).isBarricaded()) {
                    Graphics2D g2d;
                    g2d = (Graphics2D) g.create();
                    g2d.rotate(Math.toRadians(270), x + WIDTH / 2, y + HEIGHT / 2);
                    g2d.drawImage(ImageResource.BARRICADE.getImage(), x, y, WIDTH, BARRICADE_SIZE, null);
                    g2d.dispose();
                }
            }
        }
    }
}
