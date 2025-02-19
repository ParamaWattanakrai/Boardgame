package gui;

import gui.data.GameData;
import gui.data.RoadData;
import gui.utils.ImageLoader;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);;
    private final MainMenu mainMenu;
    private final Game game;
    private final Rule rule;
    private static ArrayList<RoadData> roadData;
    private static GameData gamaData;

    public MainFrame(ArrayList<RoadData> roadData,  GameData gamaData) {
        setTitle("Out Bark");
        setIconImage(ImageLoader.loadImage("logo.png"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MainFrame.roadData = roadData;
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
        
    public static ArrayList<RoadData> getRoadData() {
        return roadData;
    }

    public static void setRoadData(ArrayList<RoadData> roadData) {
        MainFrame.roadData = roadData;
    }

    public static GameData getGamaData() {
        return gamaData;
    }

    public static void setGamaData(GameData gamaData) {
        MainFrame.gamaData = gamaData;
    }

    public static void reSetRoadData() {
        MainFrame.roadData = new ArrayList<>();
    }

    public static void reSetGamaData() {
        MainFrame.gamaData = new GameData();
    }
}
