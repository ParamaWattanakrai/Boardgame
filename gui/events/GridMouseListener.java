package gui.events;

import gui.MainFrame;
import gui.components.WorldMap;
import gui.enums.texts.GameText;
import gui.screens.Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
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
        selectBlock(x, y);
        game.loadEntityButton(x, y);
        game.repaint();
        map.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    private void updateData(){
        game.updateText(GameText.Data, mainFrame.getField().getBlock(new Tuple(x, y)).toString().replace(", ", "\n"));
    }

    private void selectBlock(int x, int y){
        map.setSelect(map.getRoad(x, y));
    }
}
