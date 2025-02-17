package gui.map;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Map extends JPanel {
    public Map() {
        setLayout(new GridLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        for (int i = 0; i < 25; i++) {
            add(new Road(i));
        }
    }
}
