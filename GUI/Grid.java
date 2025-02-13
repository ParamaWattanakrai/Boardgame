package GUI;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Grid extends JPanel {
    public Grid() {
        setLayout(new GridLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 25; i++) {
            add(new GridPanel(i));
        }
    }
}
