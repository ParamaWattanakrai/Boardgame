package src.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import src.entities.*;
import src.utils.*;

public class Block {
    private Field field;
    private Tuple coordinate;

    private BlockType blockType;
    private PathType pathType;
    private int orientation;
    private Path northPath = new Path();
    private Path eastPath = new Path();
    private Path westPath = new Path();
    private Path southPath = new Path();

    private int occupationLevel;

    private String blockTypeString;
    private String pathString;

    private HashMap<EntityType, List<Entity>> entityMap = new HashMap<>();
    private List<Civilian> shooters = new ArrayList<>();

    private int gunAmount = 0;
    private int gunToBeLooted = 0;

    public Block(Field field, int x, int y, BlockType blockType, PathType pathType, int orientation, int occupationLevel) {

        this.field = field;
        coordinate = new Tuple(x, y);

        this.occupationLevel = occupationLevel;

        this.blockType = blockType;
        switch (blockType) {
            case DEFAULT:
                blockTypeString = "D";
                break;
            case SPAWN:
                blockTypeString = "S";
                break;
            case STORE:
                blockTypeString = "F";
                break;
            case HOSPITAL:
                blockTypeString = "H";
                break;
            case POLICESTATION:
                blockTypeString = "P";
                break;
            case POWERPLANT:
                blockTypeString = "N";
                break;
            default:
                break;
        }

        this.pathType = pathType;
        this.orientation = orientation;

        northPath.build();
        eastPath.build();
        southPath.build();
        westPath.build();

        pathString = "╬";

        if (pathType == PathType.STRAIGHT) {
            if (orientation % 2 == 0) {
                westPath.destroy();
                eastPath.destroy();
                pathString = "║";
            } else {
                northPath.destroy();
                southPath.destroy();
                pathString = "═";
            }
        }
        if (pathType == PathType.THREEWAY) {
            switch (orientation) {
                case 0:
                    southPath.destroy();
                    pathString = "╩";
                    break;
                case 1:
                    westPath.destroy();
                    pathString = "╠";
                    break;
                case 2:
                    northPath.destroy();
                    pathString = "╦";
                    break;
                case 3:
                    eastPath.destroy();
                    pathString = "╣";
                    break;
            }
        }
        if (pathType == PathType.CURVED) {
            switch (orientation) {
                case 0:
                    southPath.destroy();
                    westPath.destroy();
                    pathString = "╚";
                    break;
                case 1:
                    northPath.destroy();
                    westPath.destroy();
                    pathString = "╔";
                    break;
                case 2:
                    northPath.destroy();
                    eastPath.destroy();
                    pathString = "╗";
                    break;
                case 3:
                    eastPath.destroy();
                    southPath.destroy();
                    pathString = "╝";
                    break;
            }
        }
    }

    public boolean coordinateOccupied(int posX, int posY, int entitySize) {
        List<Entity> allEntities = getAllEntities();
        for (Entity entity : allEntities) {
            Tuple entityCoord = entity.getPixelCoordinate();
            if (entityCoord == null) {
                continue;
            }
            if ((entityCoord.getA() <= posX && posX <= entityCoord.getA() + 15) &&
                (entityCoord.getB() <= posY && posY <= entityCoord.getB() + 15)) {
                    return true;
                }
        }
        return false;
    }

    public void contact() {
        if (getAllEntityOfType(EntityType.SOLDIER) == null || getAllEntityOfType(EntityType.SOLDIER).size() < 1) {
            return;
        }
        List<Civilian> civilians = getAllCivilians();
        for (Civilian civilian : civilians) {
            civilian.contact();
        }
    }

    public void occupy() {
        if (occupationLevel < 2) {
            occupationLevel++;
        }
    }
    public void unOccupy() {
        if (occupationLevel > 0) {
            occupationLevel--;
        }
    }

    public int getOccupationLevel() {
        return occupationLevel;
    }

    public Block getNeighborBlock(Direction direction) {
        return field.getNextBlock(this, direction);
    }

    public List<Block> getManhattanBlocks(int distance) {
        return field.getManhattanBlocks(coordinate, distance);
    }

    public void addEntity(Entity entity) {
        EntityType entityType = entity.getEntityType();
        entityMap.computeIfAbsent(entityType, _ -> new ArrayList<>()).add(entity);
    }

    public void removeEntity(Entity entity) {
        EntityType entityType = entity.getEntityType();
        entityMap.get(entityType).remove(entity);
    }

    public void addGunToBeLooted() {
        gunToBeLooted++;
    }

    public void removeGunToBeLooted() {
        gunToBeLooted--;
    }

    public void resetGunToBeLooted() {
        gunToBeLooted = 0;
    }

    public int getGunToBeLooted() {
        return gunToBeLooted;
    }

    public void addGunAmount() {
        gunAmount++;
    }

    public boolean removeGunAmount() {
        if (gunAmount > 0) {
            gunAmount--;
            return true;
        }
        return false;
    }

    public void addShooter(Civilian entity) {
        shooters.add(entity);
    }

    public void removeShooter(Entity entity) {
        shooters.remove(entity);
    }

    public int shootDog() {
        int dogShot = 0;
        List<Dog> dogs = getAllDogs();
        if (dogs == null) {
            return dogShot;
        }
        Iterator<Dog> dogIterator = dogs.iterator();
        while (dogIterator.hasNext()) {
            Dog dog = dogIterator.next();
            Iterator<Civilian> shooterIterator = shooters.iterator();
            while (shooterIterator.hasNext()) {
                Civilian shooter = shooterIterator.next();
                boolean shot = Math.random() > shooter.getHitRate() ? false : true;
                if (shot) {
                    dog.kill();
                    dogShot++;
                    shooterIterator.remove();
                    break;
                }
                System.out.println("Survived Miraculously!");
                shooters.remove(shooter);
            }
        }
        return dogShot;
    }

    public Path getPath(Direction direction) {
        switch (direction) {
            case NORTH:
                return northPath;
            case EAST:
                return eastPath;
            case SOUTH:
                return southPath;
            case WEST:
                return westPath;
        }
        return new Path();
    }

    public Field getField() {
        return field;
    }

    public Tuple getCoordinate() {
        return coordinate;
    }

    public HashMap<EntityType, List<Entity>> getEntityMap() {
        return entityMap;
    }

    public List<Entity> getAllEntities() {
        List<Entity> entities = new ArrayList<>();
        for (EntityType entityType : entityMap.keySet()) {
            for (Entity entity : entityMap.get(entityType)) {
                entities.add(entity);
            }
        }
        return entities;
    }

    public List<Entity> getAllEntityOfType(EntityType entityType) {
        List<Entity> entityList = entityMap.get(entityType);
        if (entityList != null) {
            return entityList;
        }
        return new ArrayList<>();
    }

    public List<Civilian> getAllCivilians() {
        List<Civilian> civilians = new ArrayList<>();
        for (EntityType entityType : entityMap.keySet()) {
            if (entityType != EntityType.DOG) {
                for (Entity civilian : entityMap.get(entityType)) {
                    civilians.add((Civilian) civilian);
                }
            }
        }
        return civilians;
    }

    public List<Dog> getAllDogs() {
        List<Dog> dogs = new ArrayList<>();
        if (entityMap.get(EntityType.DOG) == null) {
            return dogs;
        }
        for (Entity dogEntity : entityMap.get(EntityType.DOG)) {
            dogs.add((Dog) dogEntity);
        }
        return dogs;
    }

    public List<Civilian> getAllAlive() {
        List<Civilian> alive = new ArrayList<>();
        for (EntityType entityType : entityMap.keySet()) {
            if (entityType != EntityType.DOG) {
                for (Entity civilianEntity : entityMap.get(entityType)) {
                    Civilian civilian = (Civilian) civilianEntity;
                    if (civilian.getVitality() == Vitality.ALIVE) {
                        alive.add(civilian);
                    }
                }
            }
        }
        return alive;
    }
    public List<Civilian> getAllComa() {
        List<Civilian> coma = new ArrayList<>();
        for (EntityType entityType : entityMap.keySet()) {
            if (entityType != EntityType.DOG) {
                for (Entity civilianEntity : entityMap.get(entityType)) {
                    Civilian civilian = (Civilian) civilianEntity;
                    if (civilian.getVitality() == Vitality.COMA) {
                        coma.add(civilian);
                    }
                }
            }
        }
        return coma;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public PathType getPathType() {
        return pathType;
    }

    public List<Civilian> getShooters() {
        return shooters;
    }

    public int getOrientation() {
        return orientation;
    }

    public int getGunAmount() {
        return gunAmount;
    }

    public String getBlockTypeString() {
        return blockTypeString;
    }
    public String getPathString() {
        return pathString;
    }

    @Override
    public String toString() {
        return "Block=" + getCoordinate() + ", Path" + getPathType() + getOrientation() + ", soldier=" + getAllEntityOfType(EntityType.SOLDIER).size() + ", civilian="
                + getAllEntityOfType(EntityType.CIVILIAN).size() + ", medic=" + getAllEntityOfType(EntityType.MEDIC).size() + ", mechanic=" + getAllEntityOfType(EntityType.MECHANIC).size() + ", path=" + getPathType() + ", dog=" + 0
                + ", landmark=" + getBlockType() + ", capture=" + false;
    }
}
