import entities.Civilian;
import map.Field;
import utils.Direction;
import utils.Tuple;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(5, 5);
        field.printField();
        Civilian civilian = new Civilian(field.getBlock(new Tuple(0, 0)));
        field.printField();
        civilian.move(Direction.EAST);
        field.printField();
    }
}
