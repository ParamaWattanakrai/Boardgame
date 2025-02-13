package GUI;

import GUI.utils.GameData;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Frame extends JFrame {
    public static ArrayList<GameData> gameData = new ArrayList<>();
    public static TextPanel textPanel  = new TextPanel();
    public static Grid grid = new Grid();
    public static JPanel panel = new JPanel(); // ใช้ JPanel เป็น container

    public Frame() {
        super("Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        for (int i = 0; i < 25; i++) {
            gameData.add(new GameData(i));
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.weightx = 0.7; 
        add(grid, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3; 
        add(textPanel, gbc);

        setVisible(true);
    }
}
