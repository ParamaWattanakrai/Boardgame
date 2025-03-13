package gui;

import gui.data.GameData;
import gui.enums.GameScreen;
import gui.enums.ImageResource;
import gui.screens.BaseScreen;
import gui.screens.Game;
import gui.screens.GameOver;
import gui.screens.GameWin;
import gui.screens.MainMenu;
import gui.screens.Rule;
import java.awt.CardLayout;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.map.Field;

public class MainFrame extends JFrame {
    private Field field; private GameData gamaData;
    private final String TITLE = "Outbark";
    private final HashMap<GameScreen, BaseScreen> screens;
    private JPanel mainPanel;
    

    public MainFrame() {
        this(null, new GameData());
    }

    public MainFrame(Field field, GameData gamaData) {
        this.field = field;
        this.gamaData = gamaData;
        this.screens = new HashMap<>();
        this.mainPanel = new JPanel(new CardLayout());

        initializeUI();
    }

    private void initializeUI() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        setIconImage(ImageResource.LOGO.getImage());
        createScreen();
        addScreen();
    }

    private void createScreen() {
        MainMenu mainMenu = new MainMenu(this, null);
        Game game = new Game(this, null);
      
        mainMenu.setGame(game);
        game.setMainMenu(mainMenu);
      
        screens.put(GameScreen.MAIN_MENU, mainMenu);
        screens.put(GameScreen.GAME, game);
        screens.put(GameScreen.RULE, new Rule(this));
        screens.put(GameScreen.GAMEOVER, new GameOver(this));
        screens.put(GameScreen.GAMEWIN, new GameWin(this));
       }
    private void addScreen() {
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

    public HashMap<GameScreen, BaseScreen> getScreens() {
        return screens;
    }
}
