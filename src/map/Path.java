package src.map;

public class Path {
    private boolean exist = false;
    private boolean barricaded = false;
    private boolean weak = false;
    
    public void build() {
        exist = true;
    }
    public void destroy() {
        exist = false;
    }

    public void buildBarricade() {
        barricaded = true;
    }

    public void biteBarricade() {
        if (weak) {
            destroyBarricade();
        } else {
            weak = true;
        }
    }

    public void destroyBarricade() {
        barricaded = false;
        weak = false;
    }

    public boolean doesExist() {
        return exist;
    }

    public boolean isBarricaded() {
        return barricaded;
    }

    @Override
    public String toString() {
        return "barricaded: " + barricaded + ", weak: " + weak;
    }
}
