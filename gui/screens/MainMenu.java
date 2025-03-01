package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.data.GameData;
import gui.enums.GameScreen;
import gui.enums.ImageResource;
import gui.enums.MainButton;
import gui.interfaces.ButtonActions;
import gui.utils.SoundManager;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import src.map.Field;
import src.map.MetaSettings;
import src.utils.Tuple;

public class MainMenu extends BaseScreen implements ButtonActions<MainButton> {
    private final HashMap<MainButton, Button> buttons;

    public MainMenu(MainFrame mainFrame) {
        super(mainFrame);
        this.buttons = new HashMap<>();
        initialize();
    }

    @Override
    protected void initializeUI() {
        setLayout(null);
        createButton();
        setButtonBounds();
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        SoundManager.playMainMenuMusic();
        setVisible(true);
    }

    @Override
    public void createButton() {
        for (MainButton button : MainButton.values()) {
            buttons.put(button, new Button(button.name()));
        }
        buttons.get(MainButton.RULE).setPop(false);
    }

    @Override
    public void setButtonBounds() {
        buttons.get(MainButton.NEW_GAME).setBounds(715, 515, 500, 70);
        buttons.get(MainButton.RESUME).setBounds(715, 690, 500, 70);
        buttons.get(MainButton.QUIT).setBounds(715, 870, 500, 70);
        buttons.get(MainButton.RULE).setBounds(120, 910, 240, 50);
    }

    @Override
    public void addButtonListener(MainButton button) {
        ActionListener actionListener = (ActionEvent e) -> {
            System.out.println(e.getActionCommand());
            switch (button) {
                case NEW_GAME -> startButton();
                case RESUME -> resumeButton();
                case QUIT -> quitButton();
                case RULE -> ruleButton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void startButton() {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 180, 180, 50, 1, 2, 1, 1, 5, 3, 2, 12);
        mainFrame.setField(new Field(metaSettings));
        mainFrame.setGamaData(new GameData());
        mainFrame.getField().printField();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).resetText();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).resetButton();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().setSelect(null);
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().resetPerRoads();
        List<Tuple> next = mainFrame.getField().getNextRoundDogCoordinates();
        next.forEach((dog) -> {
            ((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().getRoad(dog.getA(), dog.getB()).setPreviewDog(true);
        });

        mainFrame.showScreen(GameScreen.GAME);
    }

    private void resumeButton() {
        if (mainFrame.getField() != null) {
            mainFrame.showScreen(GameScreen.GAME);
        } else {
            SoundManager.playIncorrectSound();
        }
    }

    private void quitButton() {
        System.exit(0);
    }

    private void ruleButton() {
        mainFrame.showScreen(GameScreen.RULE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageResource.MENU_BACKGROUND.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
