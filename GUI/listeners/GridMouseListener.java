package gui.listeners;

import gui.Game;
import gui.MainFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class GridMouseListener implements MouseListener {
    private final JPanel panel;
    private final int num;

    public GridMouseListener(JPanel panel, int num) {
        this.panel = panel;
        this.num = num;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Game.getData().setData(num);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MainFrame.getRoadData().get(num).setCivilian(MainFrame.getRoadData().get(num).getCivilian()+1);
        Game.getData().setData(num);
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
