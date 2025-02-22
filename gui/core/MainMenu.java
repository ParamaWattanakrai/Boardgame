package gui.core;

import gui.components.Button;
import gui.data.GameData;
import gui.enums.GameScreen;
import gui.enums.button.MainButton;
import gui.utils.ImageLoader;
import gui.utils.SoundPlayer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JPanel;
import src.map.Field;
import src.map.MetaSettings;

public class MainMenu extends JPanel {
    private HashMap<MainButton, Button> buttons;
    private MainFrame mainFrame;

    MainMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        createButton();
        setButtonPosition();
        initializeUI();
        SoundPlayer.loopSound("MainManu.wav");
    }

    private void initializeUI() {
        setLayout(null);
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        setVisible(true);
    }

    private void createButton() {
        buttons = new HashMap<>();
        buttons.put(MainButton.NEW_GAME, new Button(MainButton.NEW_GAME.name()));
        buttons.put(MainButton.RESUME, new Button(MainButton.RESUME.name()));
        buttons.put(MainButton.QUIT, new Button(MainButton.QUIT.name()));
        buttons.put(MainButton.RULE, new Button(MainButton.RULE.name(), false));
    }

    private void setButtonPosition() {
        buttons.get(MainButton.NEW_GAME).setBounds(715, 490, 500, 70);
        buttons.get(MainButton.RESUME).setBounds(715, 660, 500, 70);
        buttons.get(MainButton.QUIT).setBounds(715, 840, 500, 70);
        buttons.get(MainButton.RULE).setBounds(120, 910, 240, 50);
    }

    private void addButtonListener(MainButton button) {
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
        mainFrame.showScreen(GameScreen.GAME);
        SoundPlayer.playSound("Press.wav");
    }
    
    private void resumeButton() {
        if (mainFrame.getField() != null) {
            mainFrame.showScreen(GameScreen.GAME);
            SoundPlayer.playSound("Press.wav");
        } else {
            SoundPlayer.playSound("Incorrect.wav");
        }
    }
    
    private void quitButton() {
        SoundPlayer.playSound("Press.wav");
        System.exit(0);
    }

    private void ruleButton() {
        mainFrame.showScreen(GameScreen.RULE);
        SoundPlayer.playSound("Press.wav");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("MainBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
