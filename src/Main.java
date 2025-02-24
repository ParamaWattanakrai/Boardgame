package src;

import java.util.HashMap;
import java.util.List;

import src.entities.Entity;

import src.entities.Civilian;
import src.entities.EntityType;
import src.map.Field;
import src.map.MetaSettings;
import src.utils.Direction;
import src.utils.Tuple;

public class Main {
    public static void main(String[] args) {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 1, 2, 1, 1, 5, 3, 2, 12);
        Field field = new Field(metaSettings);
        field.printField();
        Civilian civilian = new Civilian(field.getBlock(new Tuple(0, 0)));
        field.printField();
        System.out.println(civilian.move(Direction.EAST));
        field.printField();
        System.out.println(field.getBlock(new Tuple(2, 2)).getBlockType());
        System.out.println(field.getBlock(new Tuple(2, 2)).getPathString());
        HashMap<EntityType, List<Entity>> populationMap =  field.getBlock(new Tuple(2, 2)).getPopulationMap();
        for (EntityType entityType : populationMap.keySet()) {
            System.out.println(entityType);
            System.out.println(populationMap.get(entityType));
        }
    }
}
