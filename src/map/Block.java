package src.map;

import src.utils.*;
import src.entities.*;

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

    private int occupationLevel;

    private String blockTypeString;
    private String pathString;

    private ArrayList<Civilian> population = new ArrayList<>();
    private ArrayList<Civilian> civilians = new ArrayList<>();
    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Medic> medics = new ArrayList<>();
    private ArrayList<Mechanic> mechanics = new ArrayList<>();


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
        if (soldiers.size() > 0 && getPopulation().size() > 0) {
            for (Civilian person : getPopulation()) {
                if (!person.isContacted()) {
                    contactFlag = true;
                }
            }
        }
        return contactFlag;
    }

    public boolean occupy() {
        if (getPopulation().size() > 0) {
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

    public void addPerson(Civilian person) {
        population.add(person);
        if (person instanceof Soldier) {
            soldiers.add((Soldier) person);
        } else if (person instanceof Medic) {
            medics.add((Medic) person);
        } else if (person instanceof Mechanic) {
            mechanics.add((Mechanic) person);
        } else {
            civilians.add(person);
        }
    }

    public void removePerson(Civilian person) {
        population.remove(person);
        if (person instanceof Soldier) {
            soldiers.remove(person);
        } else if (person instanceof Medic) {
            medics.remove(person);
        } else if (person instanceof Mechanic) {
            mechanics.remove(person);
        } else {
            civilians.remove(person);
        }
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
        for (Civilian civilian : civilians) {
            if (civilian.isArmed()) {
                secondaryTroop++;
            }
        }
        return new Tuple(soldiers.size(), secondaryTroop);
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

    public ArrayList<Civilian> getCivilians() {
        return civilians;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public ArrayList<Medic> getMedics() {
        return medics;
    }

    public ArrayList<Mechanic> getMechanics() {
        return mechanics;
    }

    public ArrayList<Civilian> getPopulation() {
        return population;
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

    @Override
    public String toString() {
        return "Block=" + getCoordinate() + ", commander=" + 0 + ", soldier=" + getSoldiers().size() + ", civilian="
                + getCivilians().size() + ", medic=" + getMedics().size() + ", mechanic=" + getMechanics().size() + ", path=" + getPathType() + ", dog=" + 0
                + ", landmark=" + getBlockType() + ", capture=" + false;
    }
}
