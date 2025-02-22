package gui;

import gui.data.GameData;
import gui.enums.GameScreen;
import gui.utils.ImageLoader;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;
import src.map.Field;

public class MainFrame extends JFrame {
    private HashMap<GameScreen, JPanel> screens;
    private JPanel mainPanel;
    private Field field;
    private GameData gamaData;

    public MainFrame(Field field, GameData gamaData) {
        this.field = field;
        this.gamaData = gamaData;

        createScreen();
        addScreen();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Out Bark");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setIconImage(ImageLoader.loadImage("logo.png"));
        setVisible(true);
    }

    private void createScreen() {
        screens = new HashMap<>();
        screens.put(GameScreen.MAIN_MENU, new MainMenu(this));
        screens.put(GameScreen.GAME, new Game(this));
        screens.put(GameScreen.RULE, new Rule(this));
    }

    private void addScreen() {
        mainPanel = new JPanel(new CardLayout());
        screens.forEach((screen, panel) -> mainPanel.add(panel, screen.name()));
        add(mainPanel);
        showScreen(GameScreen.MAIN_MENU);
    }

    public void showScreen(GameScreen screen) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, screen.name()); 
    }
        
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public GameData getGamaData() {
        return gamaData;
    }

    public void setGamaData(GameData gamaData) {
        this.gamaData = gamaData;
    }
}
