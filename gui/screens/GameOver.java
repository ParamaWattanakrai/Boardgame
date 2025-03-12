package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.data.GameData;
import gui.enums.GameEndBtn;
import gui.enums.GameScreen;
import gui.enums.ImageResource;
import gui.interfaces.ButtonActions;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import src.entities.Dog;
import src.map.Field;
import src.map.MetaSettings;
import src.utils.Tuple;

public class GameOver extends BaseScreen implements ButtonActions<GameEndBtn>{
    private HashMap<GameEndBtn, Button> buttons;

    public GameOver(MainFrame mainFrame) {
        super(mainFrame);
        initialize();
    }

    @Override
    protected void initializeUI() {
        setLayout(null);
        createButton();
        setButtonBounds();
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        setVisible(true);
    }

    @Override
    public void createButton() {
        buttons = new HashMap<>();
         for (GameEndBtn button : GameEndBtn.values()) {
            buttons.put(button, new Button(button.name()));
        }
    }

    @Override
    public void setButtonBounds() {
        buttons.get(GameEndBtn.HOME).setBounds(715, 515, 500, 70);
        buttons.get(GameEndBtn.NEW_GAME).setBounds(715, 690, 500, 70);
    }

    @Override
    public void addButtonListener(GameEndBtn button) {
        ActionListener actionListener = (ActionEvent e) -> {
            System.out.println(e.getActionCommand());
            switch (button) {
                case HOME -> homeButton();
                case NEW_GAME -> newGameButton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void homeButton() {
        mainFrame.setField(null);  
        mainFrame.showScreen(GameScreen.MAIN_MENU);
    }

    private void newGameButton() {
        Dog.clearBiteLocations();
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 180, 180, 50, 1, 2, 1, 1, 5, 3, 2, 12);
        mainFrame.setField(new Field(metaSettings));
        mainFrame.setGamaData(new GameData());
        mainFrame.getField().printField();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).resetButton();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().setSelect(null);
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().resetPerRoads();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).resetText();
        List<Tuple> next = mainFrame.getField().getNextRoundDogCoordinates();
        next.forEach((dog) -> {((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().getRoad(dog.getA(), dog.getB()).setPreviewDog(true);});
        mainFrame.showScreen(GameScreen.GAME);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageResource.OVER_BACKGROUND.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}