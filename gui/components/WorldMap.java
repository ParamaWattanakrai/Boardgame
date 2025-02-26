package gui.components;

import gui.MainFrame;
import gui.screens.Game;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import src.entities.ActionType;

public class WorldMap extends JPanel {
    private Road[][] road = new Road[5][5];
    private MainFrame mainFrame;
    private Game game;
    private Road select = null;

    private ActionType action;
    private int alive;

    public WorldMap( Game game, MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.game = game;
        createBlock();
        setLayout(new GridLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setOpaque(false);
    }

    private void createBlock() {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                road[x][y] = new Road(x, y, this, game, mainFrame);
                add(road[x][y]); 
            }
        }
    }

    public void repaintAllRoads() {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                road[x][y].revalidate();
                road[x][y].repaint();
            }
        }
    }    

    public Road getRoad(int x, int y) {
        return road[x][y];
    }

    public void setRoad(Road[][] road) {
        this.road = road;
    }

    public Road getSelect() {
        return select;
    }

    public void setSelect(Road select) {
        this.select = select;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    
}
