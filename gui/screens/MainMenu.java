package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.data.GameData;
import gui.enums.GameScreen;
import gui.enums.buttons.MainButton;
import gui.interfaces.ButtonActions;
import gui.utils.ImageLoader;
import gui.utils.SoundManager;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import src.map.Field;
import src.map.MetaSettings;

public class MainMenu extends BaseScreen implements ButtonActions<MainButton> {
    private final Image backgroundImage = ImageLoader.loadImage("MainBg.png");;
    private HashMap<MainButton, Button> buttons;

    public MainMenu(MainFrame mainFrame) {
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
        SoundManager.playMainMenuMusic();
    }

    @Override
    public void createButton() {
        buttons = new HashMap<>();
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
        mainFrame.setGamaData(new GameData());
        mainFrame.setField(new Field(new MetaSettings(30, 5, 5, 1, 2, 1, 1, 5, 3, 2, 12)));
        mainFrame.getField().printField();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).resetText();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).resetButton();
        ((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().setSelect(null);
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
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
