import entities.Civilian;
import map.Field;
import map.MetaSettings;
import utils.Direction;
import utils.Tuple;

public class Main {
    public static void main(String[] args) {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 1, 2, 1, 1, 5, 3, 2, 12);
        Field field = new Field(metaSettings);
        field.printField();
        Civilian civilian = new Civilian(field.getBlock(new Tuple(0, 0)));
        field.printField();
        civilian.move(Direction.EAST);
        field.printField();
    }
}
