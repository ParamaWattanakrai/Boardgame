package gui.map;

import gui.listeners.GridMouseListener;
import gui.utils.ImageDrawer;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Road extends JPanel {
    private final int num;

    public Road(int num) {
        this.num = num;
        addMouseListener(new GridMouseListener(this, num));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageDrawer.drawImages(g, num, getWidth(), getHeight());
    }
}
