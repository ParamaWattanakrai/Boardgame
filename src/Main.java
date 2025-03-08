package src;

import java.util.List;

import src.entities.*;
import src.map.*;
import src.utils.Direction;
import src.utils.Tuple;

public class Main {
    public static void main(String[] args) {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 160, 171, 50, 1, 2, 1, 1, 5, 3, 2, 12);
        Field field = new Field(metaSettings);
        field.printField();
        // Civilian civilian = new Civilian(field.getBlock(new Tuple(0, 0)), metaSettings.getBlockWidth(), metaSettings.getBlockHeight(), metaSettings.getEntitySize());
        // civilian.contact();
    }
}
