package GUI;

import GUI.utils.GameData;
import java.util.ArrayList;
import javax.swing.JFrame;
public class Frame {
    private final JFrame frame = new JFrame();
    public static ArrayList<GameData> gameData = new ArrayList<>();
    public static TextPanel text = new TextPanel();
    public static Grid grid = new Grid();

    public Frame() {
        frame.setTitle("Game");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null); 
        for (int i = 0; i < 25; i++) gameData.add(new GameData(i));

        frame.add(text.getPanel());
        frame.add(grid.getPanel());

        frame.setVisible(true);
    }
}
