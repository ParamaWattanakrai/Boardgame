package gui.events;

import gui.core.Game;
import gui.core.MainFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import src.entities.Civilian;
import src.utils.Tuple;

public class GridMouseListener implements MouseListener {
    private final JPanel panel;
    private final int x;
    private final int y;
    private final Game game;
    private final MainFrame mainFrame;

    public GridMouseListener(JPanel panel, int x, int y, Game game, MainFrame mainFrame) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.game = game;
        this.mainFrame = mainFrame;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        game.updateDataText(mainFrame.getField().getBlock(new Tuple(x, y)).toString().replace(", ", "\n"));
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new Civilian(mainFrame.getField().getBlock(new Tuple(x, y)));
        mainFrame.getGamaData().setNight(mainFrame.getGamaData().getNight() + 1);
        int[] task = mainFrame.getGamaData().getTask();

        switch (mainFrame.getField().getBlock(new Tuple(x, y)).getBlockType()) {
            case HOSPITAL -> task[3] = 1;
            case STORE -> task[4] = 1;
            case POLICESTATION -> task[1] = 1;
            case POWERPLANT -> task[2] = 1;
        }

        mainFrame.getGamaData().setTask(task);
        game.updateDataText(mainFrame.getField().getBlock(new Tuple(x, y)).toString().replace(", ", "\n"));
        game.updateNightText(mainFrame.getGamaData().getNight());

        String[] tasks = { "", "Police station", "Nuclear plant", "Hospital", "Store" };
        StringBuilder text = new StringBuilder();

        for (int i = 1; i < tasks.length; i++) {
            if (mainFrame.getGamaData().getTask()[i] == 0) {
                text.append(tasks[i]).append("\n");
            } else {
                text.append(tasks[0]).append("\n");
            }
        }

        game.updateTaskText(text.toString());
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
