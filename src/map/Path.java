package map;

public class Path {
    private boolean exist = false;
    private boolean barricaded = false;
    
    public void build() {
        exist = true;
    }
    public void destroy() {
        exist = false;
    }

    public boolean barricade() {
        if (exist || barricaded) {
            return false;
        }
        barricaded = true;
        return true;
    }

    public boolean doesbuild() {
        return exist;
    }

    public boolean isBarricaded() {
        return barricaded;
    }
}
