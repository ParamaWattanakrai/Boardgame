package GUI.utils;

import GUI.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class GridMouseListener implements MouseListener{
    private final JPanel panel;
    private final int num;

    public GridMouseListener(JPanel panel, int num) {
        this.panel = panel;
        this.num = num;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Frame.textPanel.setText(num);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Frame.gameData.get(num).setCivilian(Frame.gameData.get(num).getCivilian()+1);
        Frame.textPanel.setText(num);
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
