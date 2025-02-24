package gui.events;

import gui.MainFrame;
import gui.components.WorldMap;
import gui.enums.texts.GameText;
import gui.screens.Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import src.entities.Civilian;
import src.utils.Tuple;

public class GridMouseListener implements MouseListener {
    private JPanel panel;
    private int x;
    private int y;
    private Game game;
    private MainFrame mainFrame;
    private WorldMap map;

    public GridMouseListener(JPanel panel, int x, int y, Game game, MainFrame mainFrame, WorldMap map) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.game = game;
        this.mainFrame = mainFrame;
        this.map = map;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateData();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new Civilian(mainFrame.getField().getBlock(new Tuple(x, y))).toString();
        updateData();

        mainFrame.getGamaData().setNight(mainFrame.getGamaData().getNight() + 1);
        updateNight();

        updateTask();
        highlightBlockBeside(x, y);
        map.getRoad(x, y).setBarricade(!map.getRoad(x, y).isBarricade());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    private void updateTask(){
        int[] task = mainFrame.getGamaData().getTask();
        switch (mainFrame.getField().getBlock(new Tuple(x, y)).getBlockType()) {
                    case HOSPITAL -> task[3] = 1;
                    case STORE -> task[4] = 1;
                    case POLICESTATION -> task[1] = 1;
                    case POWERPLANT -> task[2] = 1;
                    default -> System.out.print("");
        }

        String[] tasks = { "", "Police station", "Nuclear plant", "Hospital", "Store" };
        StringBuilder text = new StringBuilder();

        for (int i = 1; i < tasks.length; i++) {
            if (mainFrame.getGamaData().getTask()[i] == 0) {
                text.append(tasks[i]).append("\n");
            } else {
                text.append(tasks[0]).append("\n");
            }
        }
        game.updateText(GameText.Task, text.toString());
    }

    private void updateNight(){
        game.updateText(GameText.Night, mainFrame.getGamaData().getNight() + "/15");
    }

    private void updateData(){
        game.updateText(GameText.Data, mainFrame.getField().getBlock(new Tuple(x, y)).toString().replace(", ", "\n"));
    }

    private void highlightBlockBeside(int x, int y){
        map.getRoad(x, y).setSelect(!map.getRoad(x, y).isSelect());
        
        // what this shit
        if (map.getRoad(x, y).isSelect()) {
            if (x + 1 < 5) map.getRoad(x + 1, y).setHighlighted(true);
            if (y + 1 < 5) map.getRoad(x, y + 1).setHighlighted(true);
            if (x - 1 >= 0) map.getRoad(x - 1, y).setHighlighted(true);
            if (y - 1 >= 0) map.getRoad(x, y - 1).setHighlighted(true);
        } else {
            if (x + 1 < 5) map.getRoad(x + 1, y).setHighlighted(false);
            if (y + 1 < 5) map.getRoad(x, y + 1).setHighlighted(false);
            if (x - 1 >= 0) map.getRoad(x - 1, y).setHighlighted(false);
            if (y - 1 >= 0) map.getRoad(x, y - 1).setHighlighted(false);
        }
        map.repaint();
    }
}
