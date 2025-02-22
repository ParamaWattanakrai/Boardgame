package gui;

import gui.components.Button;
import gui.components.TextPanel;
import gui.enums.GameScreen;
import gui.enums.button.GameButton;
import gui.enums.panel.GamePanel;
import gui.map.Map;
import gui.utils.ImageLoader;
import gui.utils.SoundPlayer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;

public class Game extends JPanel {
    private HashMap<GamePanel, TextPanel> textPanels;
    private HashMap<GameButton, Button> buttons;
    private Map map;
    private MainFrame mainFrame;

    public Game(MainFrame mainFrame) {
        this.mainFrame =  mainFrame;        

        createTextPanel();
        setTextPanelPosition();

        createButton();
        setButtonPosition();

        creatMap();
        setMapPosition();

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(null);

        textPanels.values().forEach(this::add);
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        add(map);
    }

    //-------- TextPanel --------//
    private void createTextPanel() {
        textPanels = new HashMap<>();
        textPanels.put(GamePanel.NightTitle, new TextPanel("Night",60f));
        textPanels.put(GamePanel.StatTitle, new TextPanel("Stat",60f));
        textPanels.put(GamePanel.TaskTitle, new TextPanel("Task",60f));
        textPanels.put(GamePanel.DataTitle, new TextPanel("Data",60f));

        textPanels.put(GamePanel.Night, new TextPanel(60f));
        textPanels.put(GamePanel.Stat, new TextPanel(30f));
        textPanels.put(GamePanel.Task, new TextPanel(30f));
        textPanels.put(GamePanel.Data, new TextPanel(20f));

        updateNightText(mainFrame.getGamaData().getNight());
        updateStatText("Noting here");
        updateTaskText("Police station\nNuclear plant\nHospital\nStore");
    }

    private void setTextPanelPosition() {
        textPanels.get(GamePanel.NightTitle).setBounds(60, 25, 220, 200);
        textPanels.get(GamePanel.Night).setBounds(60, 95, 220, 200);

        textPanels.get(GamePanel.StatTitle).setBounds(60, 210, 220, 200);
        textPanels.get(GamePanel.Stat).setBounds(60, 280, 220, 200);

        textPanels.get(GamePanel.TaskTitle).setBounds(60, 640, 220, 200);
        textPanels.get(GamePanel.Task).setBounds(60, 720, 250, 200);

        textPanels.get(GamePanel.DataTitle).setBounds(1600, 430, 220, 500);
        textPanels.get(GamePanel.Data).setBounds(1600, 520, 220, 500);
    }

    //-------- Button --------//
    private void createButton() {
        buttons = new HashMap<>();
        buttons.put(GameButton.Setting, new Button(""));
        buttons.get(GameButton.Setting).setIcon(new ImageIcon(ImageLoader.loadImage("settings.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
    }

    private void setButtonPosition() {
        buttons.get(GameButton.Setting).setBounds(1820, 20, 80, 80);
    }

    private void addButtonListener(GameButton button) {
        ActionListener actionListener = (ActionEvent e) -> {
            System.out.println( e.getActionCommand());
            switch (button) {
                case Setting -> settingButton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void settingButton() {
        mainFrame.showScreen(GameScreen.MAIN_MENU);
        SoundPlayer.playSound("Press.wav");
    }

    //-------- Map --------//
    private void creatMap() {
        map = new Map(this, mainFrame);
    }

    private void setMapPosition() {
        map.setBounds(482, 54, 959, 900);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("GameBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void updateNightText(int nightCount) {
        textPanels.get(GamePanel.Night).setText(nightCount + "/15");
    }

    public void updateStatText(String statText) {
        textPanels.get(GamePanel.Stat).setText(statText);
    }

    public void updateTaskText(String taskText) {
        textPanels.get(GamePanel.Task).setText(taskText);
    }

    public void updateDataText(String dataText) {
        textPanels.get(GamePanel.Data).setText(dataText);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
