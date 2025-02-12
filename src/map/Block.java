package map;

import utils.*;
import entities.*;

import java.util.ArrayList;

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

    private ArrayList<Civilian> population = new ArrayList<>();
    private int gunAmount = 0;

    public Block(Field field, int x, int y, BlockType blockType, PathType pathType, int orientation) {
        this.field = field;
        coordinate = new Tuple(x, y);

        this.blockType = blockType;
        switch (blockType) {
            case DEFAULT:
                blockTypeString = "■";
                break;
            case STORE:
                blockTypeString = "🏬";
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
        population.add(person);
    }

    public void removePerson(Civilian person) {
        population.remove(person);
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
        return new Tuple(gunAmount, gunAmount);
    }

    public boolean checkPath(Direction direction) {
        switch (direction) {
            case NORTH:
                return northPath.doesExist();
            case EAST:
                return eastPath.doesExist();
            case SOUTH:
                return southPath.doesExist();
            case WEST:
                return westPath.doesExist();
        }
        return false;
    }

    public Tuple getCoordinate() {
        return coordinate;
    }

    public ArrayList<Civilian> getPopulation() {
        return population;
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
