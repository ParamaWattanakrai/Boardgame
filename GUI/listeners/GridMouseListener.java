package gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import gui.Game;
import gui.RightPanel;

public class GridMouseListener implements MouseListener {
    private final JPanel panel;
    private final int num;

    public GridMouseListener(JPanel panel, int num) {
        this.panel = panel;
        this.num = num;
        Game.getLeftPanel().getStat().setText("Stat");
        Game.getLeftPanel().getNight().setText("Night");
        Game.getLeftPanel().getTask().setText("Task");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Game.getLeftPanel();
        RightPanel.getData().setText(num);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Game.getRoadData().get(num).setCivilian(Game.getRoadData().get(num).getCivilian()+1);
        Game.getLeftPanel();
        RightPanel.getData().setText(num);
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
