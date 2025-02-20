import gui.MainFrame;
import gui.data.GameData;

import src.map.Field;
import src.map.MetaSettings;
import src.utils.Tuple;

public class Main {
    public static void main(String[] args) {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 1, 2, 1, 1, 5, 3, 2, 12);
        Field field = new Field(metaSettings);
        field.printField();
        Tuple spawnCoord = field.getSpawnCoords().get(0);
        System.out.println(field.getBlock(spawnCoord).getSoldiers().size());

        new MainFrame(field, new GameData());
    }
}
