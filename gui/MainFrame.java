package gui;

import gui.data.GameData;
import gui.utils.ImageLoader;
import src.map.Field;
import src.map.MetaSettings;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);;
    private final MainMenu mainMenu;
    private final Game game;
    private final Rule rule;
    private static Field field;
    private static GameData gamaData;

    public MainFrame(Field field,  GameData gamaData) {
        setTitle("Out Bark");
        setIconImage(ImageLoader.loadImage("logo.png"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MainFrame.field = field;
        MainFrame.gamaData = gamaData;

        mainMenu = new MainMenu(this);
        game = new Game(this);
        rule = new Rule(this);

        mainPanel.add(mainMenu, "MainMenu");
        mainPanel.add(game, "Game");
        mainPanel.add(rule, "Rule");

        add(mainPanel);
        setVisible(true);
    }

    public void showMainMenu() {
        cardLayout.show(mainPanel, "MainMenu");
    }

    public void showGame() {
        cardLayout.show(mainPanel, "Game");
    }

    public void showRule() {
        cardLayout.show(mainPanel, "Rule");
    }
        
    public static Field getField() {
        return field;
    }

    public static GameData getGamaData() {
        return gamaData;
    }

    public static void setGamaData(GameData gamaData) {
        MainFrame.gamaData = gamaData;
    }

    public static void resetGame(MetaSettings metaSettings) {
        MainFrame.field = new Field(metaSettings);
        field.printField();
    }

    public static void resetGamaData() {
        MainFrame.gamaData = new GameData();
        Game.getNight().setText("" + MainFrame.getGamaData().getNight() + "/15");
        Game.getTask().setText("Police station\nNuclear plant\nHospital\nStore");
    }
}
