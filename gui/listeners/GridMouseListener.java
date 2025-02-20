package gui.listeners;

import gui.Game;
import gui.MainFrame;
import src.entities.Civilian;
import src.utils.Tuple;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class GridMouseListener implements MouseListener {
    private final JPanel panel;
    private final int x;
    private final int y;

    public GridMouseListener(JPanel panel, int x, int y) {
        this.panel = panel;
        this.x = x;
        this.y = y;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Game.getData().setText(MainFrame.getField().getBlock(new Tuple(x, y)).toString().replace(", ", "\n"));
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new Civilian(MainFrame.getField().getBlock(new Tuple(x, y)));
        MainFrame.getGamaData().setNight(MainFrame.getGamaData().getNight()+1);
        int[] task = MainFrame.getGamaData().getTask(); 

        // task[MainFrame.getField().getBlock(new Tuple(x, y)).getBlockType()] = 1;

        MainFrame.getGamaData().setTask(task);
        
        Game.getData().setText(MainFrame.getField().getBlock(new Tuple(x, y)).toString().replace(", ", "\n"));
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
