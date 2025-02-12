import java.util.ArrayList;

public class Block {
    protected Field field;
    protected Tuple coordinate;
    protected PathType pathType;
    protected boolean northPath, eastPath, southPath, westPath;

    protected ArrayList<Civilian> population = new ArrayList<>();
    protected int gunAmount = 0;

    public Block(int x, int y, PathType pathType, int orientation) {
        coordinate = new Tuple(x, y);

        switch (pathType) {
            case FOURWAY:
                northPath = true;
                eastPath = true;
                southPath = true;
                westPath = true;
                break;

            case STRAIGHT:
                if (orientation == 0) {
                    northPath = true;
                    southPath = true;
                } else {
                    westPath = true;
                    eastPath = true;
                }
                break;

            case CURVED:
                switch (orientation) {
                    case 0:
                        northPath = eastPath = true;
                        break;
                    case 1:
                        eastPath = southPath = true;
                        break;
                    case 2:
                        southPath = westPath = true;
                        break;
                    case 3:
                        westPath = northPath = true;
                        break;
                }
                break;

            case THREEWAY:
                northPath = eastPath = southPath = westPath = true;
                
                switch (orientation) {
                    case 0:
                        southPath = false;
                        break;
                    case 1:
                        westPath = false;
                        break;
                    case 2:
                        northPath = false;
                        break;
                    case 3:
                        eastPath = false;
                        break;
                }
                break;
        }
    }

    public Block getNeighborBlock(Direction direction) {
        return field.getNextBlock(this, direction);
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
}
