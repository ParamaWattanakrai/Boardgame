package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.components.GamePanel;

public class RightPanel extends JPanel {
    private static GamePanel data = new GamePanel();;

    public RightPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setPreferredSize(new Dimension(200, 50));
        add(data);
    }
    
    public static GamePanel getData() {
        return data;
    }

    public static void setData(GamePanel data) {
        RightPanel.data = data;
    }
}
