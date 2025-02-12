package map;

import utils.*;
import entities.*;

import java.util.ArrayList;
import java.util.EnumMap;

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

    private String blockTypeString;
    private String pathString;

    private EnumMap<EntityType, ArrayList<Civilian>> population = new EnumMap<>(EntityType.class);

    private int gunAmount = 0;

    public Block(Field field, int x, int y, BlockType blockType, PathType pathType, int orientation) {
        population.put(EntityType.CIVILIAN, new ArrayList<>());
        population.put(EntityType.SOLDIER, new ArrayList<>());
        population.put(EntityType.MEDIC, new ArrayList<>());
        population.put(EntityType.MECHANIC, new ArrayList<>());

        this.field = field;
        coordinate = new Tuple(x, y);

        this.blockType = blockType;
        switch (blockType) {
            case DEFAULT:
                blockTypeString = "■";
                break;
            case STORE:
                blockTypeString = "⌂";
                break;
            case HOSPITAL:
                blockTypeString = "✚";
                break;
            case POLICESTATION:
                blockTypeString = "🛡";
                break;
            case POWERPLANT:
                blockTypeString = "☢";
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

    public Block getNeighborBlock(Direction direction) {
        return field.getNextBlock(this, direction);
    }

    public void addPerson(Civilian person) {
        population.get(person.getEntityType()).add(person);
    }

    public void removePerson(Civilian person) {
        population.get(person.getEntityType()).remove(person);
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

    public Tuple getFirePower() {
        int secondaryTroop = 0;
        for (Civilian civilian : population.get(EntityType.CIVILIAN)) {
            if (civilian.isArmed()) {
                secondaryTroop++;
            }
        }
        return new Tuple(population.get(EntityType.SOLDIER).size(), secondaryTroop);
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

    public Tuple getCoordinate() {
        return coordinate;
    }

    public ArrayList<Civilian> getEveryone() {
        ArrayList<Civilian> everyone = new ArrayList<>();
        for (ArrayList<Civilian> group : population.values()) {
            for (Civilian civilian : group) {
                everyone.add(civilian);
            }
        }
        return everyone;
    }

    public int getPopulation() {
        return getEveryone().size();
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public PathType getPathType() {
        return pathType;
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
}
