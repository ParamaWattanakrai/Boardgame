package gui.events;

import gui.MainFrame;
import gui.components.WorldMap;
import gui.enums.Mode;
import gui.enums.texts.GameText;
import gui.screens.Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import src.entities.Civilian;
import src.entities.CivilianAction;
import src.utils.Direction;
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
        if (game.getMode() == Mode.Action && map.getSelect() != null && map.getAction() != null) {
            if (nearBlock(x, y)) {
                int startX = map.getSelect().getA();
                int startY = map.getSelect().getB();
                int dx = x - startX;
                int dy = y - startY;

                Direction direction = Direction.offsetToDirection(new Tuple(dx, dy));
                Civilian civilian = mainFrame.getField().getBlock(new Tuple(startX, startY)).getAllAlive().get(map.getAlive());
                System.out.println(civilian.getEntityType());

                civilian.contact();
                switch (map.getAction()) {
                    case MOVE -> {
                        if (civilian.validateMove(direction)) {
                            Direction finalDirection = direction;
                            mainFrame.getField().addAction(civilian, CivilianAction.MOVE, () -> civilian.move(finalDirection));
                        }
                    }
                    case SHOOT -> {
                        System.out.println("SHOOT");
                    }
                    case BUILD -> {
                        System.out.println("BUILD");
                    }
                    case HEAL -> {
                        System.out.println("HEAL");
                    }
                    case ARM -> {
                        System.out.println("ARM");
                    }

                    default -> System.out.print("");
                }
            }

            int selectX = map.getSelect().getA();
            int selectY = map.getSelect().getB();
            if (selectX + 1 < 5) map.getRoad(selectX + 1, selectY).setHighlight(false);
            if (selectY + 1 < 5) map.getRoad(selectX, selectY + 1).setHighlight(false);
            if (selectX - 1 >= 0) map.getRoad(selectX - 1, selectY).setHighlight(false);
            if (selectY - 1 >= 0) map.getRoad(selectX, selectY - 1).setHighlight(false);
            map.setSelect(null);
            game.setMode(Mode.Default);
        } else{
            selectBlock(x, y);
        }

        game.loadEntityButton(x, y);
        game.loadInActionButton();
        game.repaint();
        map.repaint();
        map.revalidate();

    }

    public boolean nearBlock(int x, int y) {
        if (x - 1 >= 0 && map.getSelect() == map.getRoad(x - 1, y)) return true;  
        if (x + 1 < 5 && map.getSelect() == map.getRoad(x + 1, y)) return true;   
        if (y - 1 >= 0 && map.getSelect() == map.getRoad(x, y - 1)) return true;   
        return y + 1 < 5 && map.getSelect() == map.getRoad(x, y + 1);
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
