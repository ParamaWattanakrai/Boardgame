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
        Game.getData().setText(MainFrame.getRoadData().get(num).toString().replace(", ", "\n"));
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MainFrame.getRoadData().get(num).setCivilian(MainFrame.getRoadData().get(num).getCivilian()+1);
        MainFrame.getGamaData().setNight(MainFrame.getGamaData().getNight()+1);
        int[] task = MainFrame.getGamaData().getTask(); 

        task[MainFrame.getRoadData().get(num).getLandmark()] = 1;

        MainFrame.getGamaData().setTask(task);
        
        Game.getData().setText(MainFrame.getRoadData().get(num).toString().replace(", ", "\n"));
        Game.getNight().setText(MainFrame.getGamaData().getNight()+ "/15");

        String[] tasks = {"", "Police station", "Nuclear plant", "Hospital", "Store"};
        StringBuilder text = new StringBuilder();
        
        for (int i = 1; i < tasks.length; i++) {
            if (MainFrame.getGamaData().getTask()[i] == 0) {
                text.append(tasks[i]).append("\n");
            } else{
                text.append(tasks[0]).append("\n");
            }
        }
        
        Game.getTask().setText(text.toString());
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
