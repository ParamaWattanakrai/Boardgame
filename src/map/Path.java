package src.map;

public class Path {
    private boolean exist = false;
    private boolean barricaded = false;
    
    public void build() {
        exist = true;
    }
    public void destroy() {
        exist = false;
    }

    public boolean buildBarricade() {
        if (barricaded) {
            return false;
        }
        barricaded = true;
        return true;
    }

    public boolean destroyBarricade() {
        if (!barricaded) {
            return false;
        }
        barricaded = false;
        return true;
    }

    public boolean doesExist() {
        return exist;
    }

    public boolean isBarricaded() {
        return barricaded;
    }
}
