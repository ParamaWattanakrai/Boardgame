package GUI;

import GUI.utils.TextPanel;
import java.awt.*;
import javax.swing.*;

public class RightPanel extends JPanel {
    private static TextPanel data = new TextPanel();;

    public RightPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(200, 50));
        add(data);
    }
    
    public static TextPanel getData() {
        return data;
    }

    public static void setData(TextPanel data) {
        RightPanel.data = data;
    }
}
