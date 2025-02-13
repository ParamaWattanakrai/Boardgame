package GUI;

import GUI.utils.GameData;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Frame extends JFrame {
    public static ArrayList<GameData> gameData = new ArrayList<>();
    public static TextPanel textPanel  = new TextPanel();
    public static Grid grid = new Grid();

    public Frame() {
        super("Game");

        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        for (int i = 0; i < 25; i++) {
            gameData.add(new GameData(i));
        }

        add(textPanel);
        add(grid);
        setVisible(true);
    }
}
