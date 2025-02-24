package src;

import java.util.List;

import src.entities.*;
import src.map.*;
import src.utils.Direction;
import src.utils.Tuple;

public class Main {
    public static void main(String[] args) {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 1, 2, 1, 1, 5, 3, 2, 12);
        Field field = new Field(metaSettings);
        field.printField();
        Civilian civilian = new Civilian(field.getBlock(new Tuple(0, 0)));
        civilian.contact();
        field.printField();
        System.out.println(civilian.validateMove(Direction.EAST));
        if (civilian.validateMove(Direction.EAST)) {
            field.addAction(CivilianAction.MOVE, () -> civilian.move(Direction.EAST));
            System.out.println("Added Action");
        }
        field.endTurn();
        field.printField();
        List<Block> manhattanBlocks = field.getBlock(new Tuple(2, 2)).getManhattanBlocks(2);
        for (Block block : manhattanBlocks) {
            System.out.println(block.getCoordinate());
        }
    }
}
