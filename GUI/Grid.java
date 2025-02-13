package GUI;

import java.awt.*;
import javax.swing.*;

public class Grid {
    private final JPanel panel = new JPanel();
    
    public Grid() {
        panel.setLayout(new GridLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBounds(0, 0, 560, 560);
        for (int i = 0; i < 25; i++) {
            panel.add(new GridPanel(i).getPanel());
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}