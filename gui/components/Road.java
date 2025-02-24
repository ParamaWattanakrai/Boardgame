package gui.components;

import gui.MainFrame;
import gui.events.GridMouseListener;
import gui.screens.Game;
import gui.utils.ImageDrawer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Road extends JPanel {
    private int x;
    private int y;
    private MainFrame mainFrame;
    private boolean isSelect;
    private boolean isHighlighted;
    private boolean isBarricade;

    public Road(int x, int y, WorldMap map, Game game, MainFrame mainFrame) {
        this.x = x;
        this.y = y;
        this.mainFrame = mainFrame;
        addMouseListener(new GridMouseListener(this, x, y, game, mainFrame, map));
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        new ImageDrawer().drawRoad(g, x, y, getWidth(), getHeight(), mainFrame);
        new ImageDrawer().drawLandmark(g, x, y, getWidth(), getHeight(), mainFrame);
        new ImageDrawer().drawPopulation(g, x, y, getWidth(), getHeight(), mainFrame);
        Graphics2D g2d = (Graphics2D) g.create();
        if (isHighlighted) {
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        if (isSelect) {
            g2d.setColor(new Color(0, 255, 0, 100));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        if (isBarricade) {
            new ImageDrawer().drawBarricade(g, x, y, getWidth(), getHeight(), mainFrame);

        }
        g2d.dispose();
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public boolean isBarricade() {
        return isBarricade;
    }

    public void setBarricade(boolean isBarricade) {
        this.isBarricade = isBarricade;
    }
}
