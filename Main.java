import gui.MainFrame;
import gui.data.GameData;
import java.util.ArrayList;
import src.map.Field;

public class Main {
    public static void main(String[] args) {
        
        new MainFrame(new ArrayList<>(), new GameData());
    }
}
