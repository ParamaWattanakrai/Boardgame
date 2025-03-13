package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.data.GameData;
import gui.enums.GameButton;
import gui.enums.GameScreen;
import gui.enums.ImageResource;
import gui.enums.MainButton;
import gui.enums.SoundResource;
import gui.interfaces.ButtonActions;
import gui.utils.SoundPlayer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import src.entities.Dog;
import src.map.Field;
import src.map.MetaSettings;
import src.utils.Tuple;

public class MainMenu extends BaseScreen implements ButtonActions<MainButton> {
    private final HashMap<MainButton, Button> buttons;
    private Game game;
    private boolean isSoundOn = true;

    public MainMenu(MainFrame mainFrame, Game game) {
        super(mainFrame);
        this.game = game;
        this.buttons = new HashMap<>();
        initialize();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    protected void initializeUI() {
        setLayout(null);
        createButton();
        setButtonBounds();
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        SoundPlayer.loopSound(SoundResource.MAINMENU.getSound());
        setVisible(true);
    }

    @Override
    public void createButton() {
        for (MainButton button : MainButton.values()) {
            buttons.put(button, new Button(button.name()));
        }
        buttons.get(MainButton.RULE).setPop(false);
        buttons.put(MainButton.SOUND, new Button());
        buttons.get(MainButton.SOUND).setIcon(new ImageIcon(ImageResource.SOUND.getScaledImage(80, 80)));
        buttons.put(MainButton.SOUND_OFF, new Button());
        buttons.get(MainButton.SOUND_OFF).setVisible(false);
        buttons.get(MainButton.SOUND_OFF).setIcon(new ImageIcon(ImageResource.SOUND_OFF.getScaledImage(80, 80)));
    }

    @Override
    public void setButtonBounds() {
        buttons.get(MainButton.NEW_GAME).setBounds(715, 515, 500, 70);
        buttons.get(MainButton.RESUME).setBounds(715, 690, 500, 70);
        buttons.get(MainButton.QUIT).setBounds(715, 870, 500, 70);
        buttons.get(MainButton.RULE).setBounds(120, 910, 240, 50);
        buttons.get(MainButton.SOUND).setBounds(1700, 910, 240, 50);
        buttons.get(MainButton.SOUND_OFF).setBounds(1700, 910, 240, 50);
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
                case SOUND -> soundbotton();
                case SOUND_OFF -> soundbotton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void startButton() {
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
        next.forEach((dog) -> {
            ((Game) mainFrame.getScreens().get(GameScreen.GAME)).getMap().getRoad(dog.getA(), dog.getB())
                    .setPreviewDog(true);
        });
        mainFrame.showScreen(GameScreen.GAME);
    }

    private void resumeButton() {
        if (mainFrame.getField() != null) {
            mainFrame.showScreen(GameScreen.GAME);
        } else {
            SoundPlayer.playSound(SoundResource.INCORRECT.getSound());
        }
    }

    private void quitButton() {
        System.exit(0);
    }

    private void ruleButton() {
        mainFrame.showScreen(GameScreen.RULE);
        SoundPlayer.playSound(SoundResource.MISSION.getSound());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageResource.MENU_BACKGROUND.getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    public void soundbotton() {
        if (isSoundOn) {
            isSoundOn = false;
            SoundPlayer.stopSound();
            buttons.get(MainButton.SOUND).setVisible(false);
            buttons.get(MainButton.SOUND_OFF).setVisible(true);
            if (game != null) {
                game.setSoundOn(false);
                game.getButtons().get(GameButton.SoundON).setVisible(false);
                game.getButtons().get(GameButton.SoundOFF).setVisible(true);
            }
        } else {
            isSoundOn = true;
            SoundPlayer.loopSound(SoundResource.MAINMENU.getSound());
            buttons.get(MainButton.SOUND).setVisible(true);
            buttons.get(MainButton.SOUND_OFF).setVisible(false);
            if (game != null) {
                game.setSoundOn(true);
                game.getButtons().get(GameButton.SoundON).setVisible(true);
                game.getButtons().get(GameButton.SoundOFF).setVisible(false);
            }
        }
    }
}
