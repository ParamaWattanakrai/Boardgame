package GUI.utils;

import GUI.Frame;
import GUI.RightPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class GridMouseListener implements MouseListener {
    private final JPanel panel;
    private final int num;

    public GridMouseListener(JPanel panel, int num) {
        this.panel = panel;
        this.num = num;
        Frame.getLeftPanel().getStat().setText("Stat");
        Frame.getLeftPanel().getNight().setText("Night");
        Frame.getLeftPanel().getTask().setText("Task");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Frame.getLeftPanel();
        RightPanel.getData().setText(num);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Frame.getGridData().get(num).setCivilian(Frame.getGridData().get(num).getCivilian()+1);
        Frame.getLeftPanel();
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
