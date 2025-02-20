package gui.map;

import gui.listeners.GridMouseListener;
import gui.utils.ImageDrawer;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Road extends JPanel {
    private final int x;
    private final int y;

    public Road(int x, int y) {
        this.x = x;
        this.y = y;
        addMouseListener(new GridMouseListener(this, x, y));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageDrawer.drawImages(g, x, y, getWidth(), getHeight());
        
    }
}
