package src.utils;

public class Tuple {
    private int a;
    private int b;

    public Tuple(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", a, b);
    }
}
