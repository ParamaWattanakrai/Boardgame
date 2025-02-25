package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.components.Road;
import gui.components.TextArea;
import gui.components.WorldMap;
import gui.enums.GameScreen;
import gui.enums.Mode;
import gui.enums.buttons.GameButton;
import gui.enums.texts.GameText;
import gui.interfaces.ButtonActions;
import gui.interfaces.TextDisplay;
import gui.utils.ImageLoader;
import gui.utils.MinimalScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import src.entities.Civilian;
import src.entities.CivilianAction;
import src.entities.EntityType;
import src.utils.Tuple;

public class Game extends BaseScreen implements ButtonActions<GameButton>, TextDisplay<GameText> {
    private HashMap<GameText, TextArea> textPanels;
    private HashMap<GameButton, Button> buttons;
    private WorldMap map;

    private JScrollPane scrollEntityButton;
    private JPanel entityPanel  = new JPanel();

    private JScrollPane scrollActionButton;
    private JPanel actionPanel  = new JPanel();

    private Mode mode  = Mode.Default;

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

        entityPanel.setLayout(new BoxLayout(entityPanel, BoxLayout.Y_AXIS));
        entityPanel.setOpaque(false);

        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setOpaque(false);
        
        scrollEntityButton = new JScrollPane(entityPanel);
        scrollEntityButton.setOpaque(false);
        scrollEntityButton.setBorder(null);
        scrollEntityButton.getViewport().setOpaque(false);
        scrollEntityButton.setBounds(1520, 440, 350, 150); 
        scrollEntityButton.getVerticalScrollBar().setUI(new MinimalScrollBarUI());
        scrollEntityButton.getVerticalScrollBar().setOpaque(false);
        scrollEntityButton.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollEntityButton.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollActionButton = new JScrollPane(actionPanel);
        scrollActionButton.setOpaque(false);
        scrollActionButton.setBorder(null);
        scrollActionButton.getViewport().setOpaque(false);
        scrollActionButton.setBounds(1520, 740, 350, 100); 
        scrollActionButton.getVerticalScrollBar().setUI(new MinimalScrollBarUI());
        scrollActionButton.getVerticalScrollBar().setOpaque(false);
        scrollActionButton.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollActionButton.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollEntityButton);
        add(scrollActionButton);
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
        textPanels.put(GameText.SelectTitle, new TextArea("Select",60f));
        textPanels.put(GameText.Action, new TextArea("Action",60f));

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

        textPanels.get(GameText.StatTitle).setBounds(60, 220, 220, 200);
        textPanels.get(GameText.Stat).setBounds(60, 305, 220, 200);

        textPanels.get(GameText.TaskTitle).setBounds(60, 650, 220, 200);
        textPanels.get(GameText.Task).setBounds(60, 740, 250, 200);

        textPanels.get(GameText.DataTitle).setBounds(1600, 380, 220, 500);
        textPanels.get(GameText.DataTitle).setVisible(false);
        textPanels.get(GameText.Data).setBounds(1600, 470, 220, 500);
        textPanels.get(GameText.Data).setVisible(false);

        textPanels.get(GameText.SelectTitle).setBounds(1610, 360, 250, 70);
        textPanels.get(GameText.Action).setBounds(1610, 675, 250, 70);
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
        buttons.put(GameButton.EndButton, new Button("END TURN", 50));
    }

    @Override
    public void setButtonBounds() {
        buttons.get(GameButton.Setting).setBounds(1820, 20, 80, 80);
        buttons.get(GameButton.EndButton).setBounds(1475,  905, 500, 50);
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
        mainFrame.getField().endTurn();
        map.repaint();
        map.setSelect(null);
        textPanels.get(GameText.SelectTitle).setText("Select");

        entityPanel.removeAll();
        actionPanel.removeAll();
        entityPanel.repaint();
        actionPanel.repaint();
    }

    //-------- Map --------//
    private void createMap() {
        map = new WorldMap(this, mainFrame);
    }

    private void setMapPosition() {
        map.setBounds(482, 54, 959, 900);
    }

     //-------- Entity button --------//
    public void loadEntityButton(int x, int y) {
        textPanels.get(GameText.SelectTitle).setText("Entity");
        entityPanel.removeAll();
        List<Civilian> allAlive = mainFrame.getField().getBlock(new Tuple(x, y)).getAllAlive(); 
        
        if (!allAlive.isEmpty()) {
            for (int i = 0; i < allAlive.size();i++) {
                int o = i;
                Button btn = new Button(allAlive.get(i).getEntityType().name(), 30);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.addActionListener(_ ->  entityButton(map.getRoad(x, y), o));
                entityPanel.add(btn);
            }
        }
        
        entityPanel.setPreferredSize(new Dimension(400, Math.max(allAlive.size()*70, 300)));
        entityPanel.revalidate();
        entityPanel.repaint();
    }

    private void entityButton(Road road, int alive){
        int x = road.getA();
        int y = road.getB();
        System.out.println(mainFrame.getField().getBlock(new Tuple(x, y)).getAllAlive().get(alive).getEntityType());
        loadActionButton(road, alive);
    }

    //-------- Action button --------//
    private void loadActionButton(Road road, int alive) {
        textPanels.get(GameText.SelectTitle).setText("Action");
        entityPanel.removeAll();

        for (CivilianAction action : CivilianAction.values()) {
            Button btn = new Button(action.name(), 30);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(_ ->  actionButton(road, action, alive));
            entityPanel.add(btn);
        }
        
        entityPanel.setPreferredSize(new Dimension(400, Math.max(CivilianAction.values().length*70, 300)));
        entityPanel.revalidate();
        entityPanel.repaint();        
    }

    private void actionButton(Road road, CivilianAction action, int alive){
        int x = road.getA();
        int y = road.getB();
        if (x + 1 < 5) map.getRoad(x + 1, y).setHighlight(true);
        if (y + 1 < 5) map.getRoad(x, y + 1).setHighlight(true);
        if (x - 1 >= 0) map.getRoad(x - 1, y).setHighlight(true);
        if (y - 1 >= 0) map.getRoad(x, y - 1).setHighlight(true);
        map.setAction(action);
        map.setAlive(alive);
        setMode(Mode.Action);

        textPanels.get(GameText.SelectTitle).setText("Select");
        entityPanel.removeAll();
        entityPanel.revalidate();
        entityPanel.repaint();
        map.revalidate();
        map.repaint();
    }

    public void resetButton(){
        entityPanel.removeAll();
        textPanels.get(GameText.SelectTitle).setText("Select");
    }

     //-------- In Action  --------//
    public void loadInActionButton() {
        textPanels.get(GameText.Action).setText("Action");
        actionPanel.removeAll();
        List<Civilian> allAlive = mainFrame.getField().getAllCivilians(); 
        int cont = 0;
        if (allAlive != null) {
            for (Civilian civilian : allAlive) {
                if (civilian.getActionRunnable() != null) {
                    System.out.println("wow");
                    Button btn = new Button(civilian.getEntityType().name(), 30);
                    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btn.addActionListener(_ ->  inActionButton(civilian));
                    actionPanel.add(btn);
                    cont++;
                } 
            }
        }
        
        actionPanel.setPreferredSize(new Dimension(400, Math.max(cont*70, 300)));
        actionPanel.revalidate();
        actionPanel.repaint();        
    }

    private void inActionButton(Civilian civilian){        
        mainFrame.getField().removeAction(civilian, civilian.getCivilianAction(), civilian.getActionRunnable());
        actionPanel.removeAll();
        loadInActionButton();
        actionPanel.revalidate();
        actionPanel.repaint();   
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("GameBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        int dogSize = mainFrame.getField().getAllEntityOfType(EntityType.DOG).size();
        int CivilianSize = mainFrame.getField().getAllEntityOfType(EntityType.CIVILIAN).size();
        int SoldierSize = mainFrame.getField().getAllEntityOfType(EntityType.SOLDIER).size();
        int docterSize = mainFrame.getField().getAllEntityOfType(EntityType.MEDIC).size();
        int engineerSize = mainFrame.getField().getAllEntityOfType(EntityType.MECHANIC).size();

        String str = "Dog: " + dogSize + "\nPerson: " + CivilianSize + "\nSoldier: " + SoldierSize + "\nDocter: " + docterSize + "\nEngineer: " + engineerSize;
        updateText(GameText.Stat, str);
    }

    public WorldMap getMap() {
        return map;
    }

    public void setMap(WorldMap map) {
        this.map = map;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
