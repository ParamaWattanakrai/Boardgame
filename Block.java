import java.util.ArrayList;

public class Block {
    protected Tuple coordinate;
    protected boolean northPath;
    protected boolean eastPath;
    protected boolean southPath;
    protected boolean westPath;

    protected ArrayList<Civilian> population = new ArrayList<>();
    protected int gunAmount = 0;

    public Block(int x, int y, boolean northPath, boolean eastPath, boolean southPath, boolean westPath) {
        this.coordinate = new Tuple(x, y);
        this.northPath = northPath;
        this.eastPath = eastPath;
        this.southPath = southPath;
        this.westPath = westPath;
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
