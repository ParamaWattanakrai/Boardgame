public class Tuple {
    private int a;
    private int b;

    public Tuple(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int geta() {
        return a;
    }

    public int getb() {
        return b;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", a, b);
    }
}
