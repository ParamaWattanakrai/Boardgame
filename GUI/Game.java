package gui;

import gui.map.Map;
import gui.map.RoadData;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Game extends JFrame {
    private static ArrayList<RoadData> roadData = new ArrayList<>();
    private static RightPanel rightPanel = new RightPanel();
    private static LeftPanel leftPanel = new LeftPanel();
    private static Map map = new Map();

    public Game() {
        super("Out Bark");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(new GridBagLayout());

        for (int i = 0; i < 25; i++) roadData.add(new RoadData(i));

        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
    
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        // leftPanel.setBackground(Color.black);
        add(leftPanel, gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        // map.setBackground(Color.black);
        add(map, gbc);
    
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        // rightPanel.setBackground(Color.black);
        add(rightPanel, gbc);
        setVisible(true);
    }

    public static ArrayList<RoadData> getRoadData() {
        return roadData;
    }

    public static void setRoadData(ArrayList<RoadData> roadData) {
        Game.roadData = roadData;
    }

    public static RightPanel getRightPanel() {
        return rightPanel;
    }

    public static void setRightPanel(RightPanel rightPanel) {
        Game.rightPanel = rightPanel;
    }

    public static LeftPanel getLeftPanel() {
        return leftPanel;
    }

    public static void setLeftPanel(LeftPanel leftPanel) {
        Game.leftPanel = leftPanel;
    }

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        Game.map = map;
    }
}
