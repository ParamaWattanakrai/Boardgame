package src.map;

import java.util.ArrayList;
import java.util.HashMap;
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
                    pathString = "╩";
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

    public boolean contact() {
        boolean contactFlag = false;
        if (getAllEntity(EntityType.SOLDIER).size() > 0 && getAllCivilians().size() > 0) {
            for (Civilian person : getAllCivilians()) {
                if (!person.isContacted()) {
                    contactFlag = true;
                }
            }
        }
        return contactFlag;
    }

    public boolean occupy() {
        if (getAllCivilians().size() > 0) {
            if (occupationLevel < 2) {
                occupationLevel--;
            }
            return true;
        }
        if (occupationLevel > 0) {
            occupationLevel--;
        }
        return false;
    }

    public Block getNeighborBlock(Direction direction) {
        return field.getNextBlock(this, direction);
    }

    public void addEntity(Entity entity) {
        EntityType entityType = entity.getEntityType();
        entityMap.computeIfAbsent(entityType, _ -> new ArrayList<>()).add(entity);
    }

    public boolean removeEntity(Entity entity) {
        EntityType entityType = entity.getEntityType();
        List<Entity> entityPopulation = entityMap.get(entityType);
        if (entityPopulation != null) {
            return entityPopulation.remove(entity);
        }
        return false;
    }

    public void addGun() {
        gunAmount++;
    }

    public boolean removeGun() {
        if (gunAmount > 0) {
            gunAmount--;
            return true;
        }
        return false;
    }

    // public Tuple getFirePower() {
    //     int secondaryTroop = 0;
    //     for (Civilian civilian : getAllCivilians()) {
    //         if (civilian.isArmed()) {
    //             secondaryTroop++;
    //         }
    //     }
    //     return new Tuple(getAllEntity(EntityType.SOLDIER).size(), secondaryTroop);
    // }

    public void addShooter(Civilian entity) {
        shooters.add(entity);
    }

    public void removeShooter(Entity entity) {
        shooters.remove(entity);
    }

    public int shootDog() {
        int dogShot = 0;
        List<Entity> dogs = getAllEntity(EntityType.DOG);
        for (Entity dog : dogs) {
            for (Entity shooterEntity : shooters) {
                Civilian shooter = (Civilian) shooterEntity;
                boolean shot = Math.random() > shooter.getHitRate() ? false : true;
                if (shot) {
                    dog.kill();
                    dogShot++;
                    break;
                }
                shooters.remove(shooterEntity);
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

    public HashMap<EntityType, List<Entity>> getentityMap() {
        return entityMap;
    }

    public List<Entity> getAllEntity(EntityType entityType) {
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
        for (Entity dogEntity : entityMap.get(EntityType.DOG)) {
            dogs.add((Dog) dogEntity);
        }
        return dogs;
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
        return "Block=" + getCoordinate() + ", soldier=" + getAllEntity(EntityType.SOLDIER).size() + ", civilian="
                + getAllEntity(EntityType.CIVILIAN).size() + ", medic=" + getAllEntity(EntityType.MEDIC).size() + ", mechanic=" + getAllEntity(EntityType.MECHANIC).size() + ", path=" + getPathType() + ", dog=" + 0
                + ", landmark=" + getBlockType() + ", capture=" + false;
    }
}
