package map;

import utils.*;
import entities.*;

import java.util.ArrayList;

public class Block {
    protected Field field;
    protected Tuple coordinate;
    protected PathType pathType;
    protected int orientation;
    protected Path northPath = new Path();
    protected Path eastPath = new Path();
    protected Path westPath = new Path();
    protected Path southPath = new Path();

    protected String pathString;

    protected ArrayList<Civilian> population = new ArrayList<>();
    protected int gunAmount = 0;

    public Block(int x, int y, PathType pathType, int orientation) {
        coordinate = new Tuple(x, y);

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

    public Tuple getCoordinate() {
        return coordinate;
    }

    public ArrayList<Civilian> getPopulation() {
        return population;
    }

    public int getGunAmount() {
        return gunAmount;
    }

    public String getPathString() {
        return pathString;
    }
}
