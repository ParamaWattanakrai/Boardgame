package gui.map;

import gui.core.Game;
import gui.core.MainFrame;
import gui.events.GridMouseListener;
import gui.utils.ImageDrawer;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Road extends JPanel {
    private final int x;
    private final int y;
    private MainFrame mainFrame;

    public Road(int x, int y, Game game,  MainFrame mainFrame) {
        this.x = x;
        this.y = y;
        this.mainFrame = mainFrame;
        addMouseListener(new GridMouseListener(this, x, y, game, mainFrame));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        new ImageDrawer(g, x, y, getWidth(), getHeight(), mainFrame);        
    }
}
