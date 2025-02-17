package GUI;

import GUI.utils.GridData;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Frame extends JFrame {
    private static ArrayList<GridData> gridData = new ArrayList<>();
    private static RightPanel rightPanel = new RightPanel();
    private static LeftPanel leftPanel = new LeftPanel();
    private static Grid grid = new Grid();

    public Frame() {
        super("Out Bark");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(new GridBagLayout());

        for (int i = 0; i < 25; i++) gridData.add(new GridData(i));
    
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
    
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        leftPanel.setBackground(Color.black);
        add(leftPanel, gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        grid.setBackground(Color.black);
        add(grid, gbc);
    
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        rightPanel.setBackground(Color.black);
        add(rightPanel, gbc);
        setVisible(true);
    }
    
    public static LeftPanel getLeftPanel() {
        return leftPanel;
    }
    
    public static RightPanel getRightPanel() {
        return rightPanel;
    }

    public static Grid getGrid() {
        return grid;
    }

    public static void setGrid(Grid grid) {
        Frame.grid = grid;
    }

    public static void setRightPanel(RightPanel rightPanel) {
        Frame.rightPanel = rightPanel;
    }

    public static void setLeftPanel(LeftPanel leftPanel) {
        Frame.leftPanel = leftPanel;
    }

    public static ArrayList<GridData> getGridData() {
        return gridData;
    }

    public static void setGridData(ArrayList<GridData> gridData) {
        Frame.gridData = gridData;
    }
}
