package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.components.TextArea;
import gui.components.WorldMap;
import gui.enums.GameScreen;
import gui.enums.buttons.GameButton;
import gui.enums.texts.GameText;
import gui.interfaces.ButtonActions;
import gui.interfaces.TextDisplay;
import gui.utils.ImageLoader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import src.entities.EntityType;

public class Game extends BaseScreen implements ButtonActions<GameButton>, TextDisplay<GameText> {
    private HashMap<GameText, TextArea> textPanels;
    private HashMap<GameButton, Button> buttons;
    private WorldMap map;

    public Game(MainFrame mainFrame) {
        super(mainFrame);
        initialize();
    }

    @Override
    protected void initializeUI() {
        setLayout(null);

        createTextPanel(); setTextPanelBounds();
        createButton(); setButtonBounds();
        createMap();setMapPosition();
        textPanels.values().forEach(this::add);
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        add(map);
        setVisible(true);
        
    }

    //-------- TextPanel --------//
    @Override
    public void createTextPanel() {
        textPanels = new HashMap<>();

        textPanels.put(GameText.NightTitle, new TextArea("Night",60f));
        textPanels.put(GameText.StatTitle, new TextArea("Stat",60f));
        textPanels.put(GameText.TaskTitle, new TextArea("Task",60f));
        textPanels.put(GameText.DataTitle, new TextArea("Data",60f));

        textPanels.put(GameText.Night, new TextArea(60f));
        textPanels.put(GameText.Stat, new TextArea(30f));
        textPanels.put(GameText.Task, new TextArea(30f));
        textPanels.put(GameText.Data, new TextArea(20f));
        resetText();

    }
    
    @Override
    public void setTextPanelBounds() {
        textPanels.get(GameText.NightTitle).setBounds(60, 25, 220, 200);
        textPanels.get(GameText.Night).setBounds(60, 95, 220, 200);

        textPanels.get(GameText.StatTitle).setBounds(60, 210, 220, 200);
        textPanels.get(GameText.Stat).setBounds(60, 280, 220, 200);

        textPanels.get(GameText.TaskTitle).setBounds(60, 640, 220, 200);
        textPanels.get(GameText.Task).setBounds(60, 720, 250, 200);

        textPanels.get(GameText.DataTitle).setBounds(1600, 430, 220, 500);
        textPanels.get(GameText.Data).setBounds(1600, 520, 220, 500);
    }
    
    @Override
    public void updateText(GameText panel, String text) {
        textPanels.get(panel).setText(text);
    }

    public void resetText() {
        updateText(GameText.Night , mainFrame.getGamaData().getNight() + "/15");
        updateText(GameText.Stat ,"Noting here");
        updateText(GameText.Task, "Police station\nNuclear plant\nHospital\nStore");
    }

    //-------- Button --------//
    @Override
    public void createButton() {
        buttons = new HashMap<>();
        buttons.put(GameButton.Setting, new Button(""));
        buttons.get(GameButton.Setting).setIcon(new ImageIcon(ImageLoader.loadImage("settings.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        buttons.put(GameButton.EndButton, new Button("End Button", 50));
    }

    @Override
    public void setButtonBounds() {
        buttons.get(GameButton.Setting).setBounds(1820, 20, 80, 80);
        buttons.get(GameButton.EndButton).setBounds(1475,  900, 500, 50);
    }
    
    @Override
    public void addButtonListener(GameButton button) {
        ActionListener actionListener = (ActionEvent e) -> {
            System.out.println(e.getActionCommand());
            switch (button) {
                case Setting -> settingButton();
                case EndButton -> endButton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void settingButton() {
        mainFrame.showScreen(GameScreen.MAIN_MENU);
    }

    private void endButton(){
        int dogSize = mainFrame.getField().getAllEntity(EntityType.DOG).size();
        int CivilianSize = mainFrame.getField().getAllEntity(EntityType.CIVILIAN).size();
        int SoldierSize = mainFrame.getField().getAllEntity(EntityType.SOLDIER).size();
        int docterSize = mainFrame.getField().getAllEntity(EntityType.MEDIC).size();
        int engineerSize = mainFrame.getField().getAllEntity(EntityType.MECHANIC).size();

        String str = "Dog: " + dogSize + "\nPerson: " + CivilianSize + "\nSoldier: " + SoldierSize + "\nDocter: " + docterSize + "\nEngineer: " + engineerSize;
        updateText(GameText.Stat, str);
    }

    //-------- Map --------//
    private void createMap() {
        map = new WorldMap(this, mainFrame);
    }

    private void setMapPosition() {
        map.setBounds(482, 54, 959, 900);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("GameBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        endButton();
    }
}
