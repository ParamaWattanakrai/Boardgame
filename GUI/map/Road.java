package gui.map;

import java.awt.Graphics;
import javax.swing.JPanel;

import gui.listeners.GridMouseListener;
import gui.utils.ImageDrawer;

public class Road extends JPanel {
    private final int num;

    public Road(int num) {
        this.num = num;
        addMouseListener(new GridMouseListener(this, num));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageDrawer.drawImages(g, num, getWidth(), getHeight());
    }
}
