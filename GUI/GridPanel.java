package GUI;

import GUI.utils.GridMouseListener;
import GUI.utils.ImageDrawer;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GridPanel extends JPanel {
    private final int num;

    public GridPanel(int num) {
        this.num = num;
        setBackground(Color.LIGHT_GRAY);
        addMouseListener(new GridMouseListener(this, num));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageDrawer.drawImages(g, num, getWidth(), getHeight());
    }
}
