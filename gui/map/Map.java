package gui.map;

import gui.core.MainFrame;
import gui.screens.Game;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Map extends JPanel {
    public Map(Game game, MainFrame mainFrame) {
        setLayout(new GridLayout(5, 5));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                add(new Road(j, i, game, mainFrame));
            }
        }
    }
}
