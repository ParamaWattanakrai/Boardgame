package GUI;

import java.util.ArrayList;
import javax.swing.*;

public class Frame {
    private final JFrame frame = new JFrame();
    public static ArrayList<Data> gameData = new ArrayList<>();

    public Frame() {
        frame.setTitle("Game");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null); 
        for (int i = 0; i < 25; i++) gameData.add(new Data(i));

        Text text = new Text();
        frame.add(text.getPanel());

        Grid grid = new Grid();
        frame.add(grid.getPanel());


        frame.setVisible(true);
    }
}
