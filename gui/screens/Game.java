package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.components.TextArea;
import gui.components.WorldMap;
import gui.enums.GameButton;
import gui.enums.GameMode;
import gui.enums.GameScreen;
import gui.enums.GameText;
import gui.enums.ImageResource;
import gui.interfaces.ButtonActions;
import gui.interfaces.TextDisplay;
import gui.utils.MinimalScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.swing.*;
import src.entities.ActionType;
import src.entities.Civilian;
import src.entities.EntityType;
import src.entities.Mechanic;
import src.entities.Medic;
import src.entities.Vitality;
import src.utils.Direction;
import src.utils.Tuple;

public class Game extends BaseScreen implements ButtonActions<GameButton>, TextDisplay<GameText> {
    private HashMap<GameText, TextArea> textPanels;
    private HashMap<GameButton, Button> buttons;
    private WorldMap map;

    private JScrollPane scrollEntityButton;
    private JPanel entityPanel = new JPanel();

    private JScrollPane scrollActionButton;
    private JPanel actionPanel = new JPanel();

    private Boolean day = true;

    private GameMode mode = GameMode.Default;

    public Game(MainFrame mainFrame) {
        super(mainFrame);
        initialize();
    }

    @Override
    protected void initializeUI() {
        setLayout(null);
        createTextPanel();
        setTextPanelBounds();
        createButton();
        setButtonBounds();
        createMap();
        setMapPosition();
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
        scrollEntityButton.getVerticalScrollBar().setUnitIncrement(20);

        scrollActionButton = new JScrollPane(actionPanel);
        scrollActionButton.setOpaque(false);
        scrollActionButton.setBorder(null);
        scrollActionButton.getViewport().setOpaque(false);
        scrollActionButton.setBounds(1520, 740, 350, 100);
        scrollActionButton.getVerticalScrollBar().setUI(new MinimalScrollBarUI());
        scrollActionButton.getVerticalScrollBar().setOpaque(false);
        scrollActionButton.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollActionButton.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollActionButton.getVerticalScrollBar().setUnitIncrement(20);

        add(scrollEntityButton);
        add(scrollActionButton);
        setVisible(true);
    }

    // -------- TextPanel --------//
    @Override
    public void createTextPanel() {
        textPanels = new HashMap<>();

        textPanels.put(GameText.DatNightTitle, new TextArea("Day", 60f));
        textPanels.put(GameText.StatTitle, new TextArea("Stat", 60f));
        textPanels.put(GameText.TaskTitle, new TextArea("Task", 60f));
        textPanels.put(GameText.DataTitle, new TextArea("Data", 60f));
        textPanels.put(GameText.SelectTitle, new TextArea("Select", 50f));
        textPanels.put(GameText.Action, new TextArea("Action", 50f));

        textPanels.put(GameText.DatNight, new TextArea(60f));
        textPanels.put(GameText.Stat, new TextArea(30f));
        textPanels.put(GameText.Task, new TextArea(30f));
        textPanels.put(GameText.Data, new TextArea(20f));
        resetText();
    }

    @Override
    public void setTextPanelBounds() {
        textPanels.get(GameText.DatNightTitle).setBounds(60, 25, 220, 200);
        textPanels.get(GameText.DatNight).setBounds(60, 95, 220, 200);

        textPanels.get(GameText.StatTitle).setBounds(60, 220, 220, 200);
        textPanels.get(GameText.Stat).setBounds(60, 305, 220, 200);

        textPanels.get(GameText.TaskTitle).setBounds(60, 650, 220, 200);
        textPanels.get(GameText.Task).setBounds(60, 740, 250, 200);

        textPanels.get(GameText.DataTitle).setBounds(1600, 380, 220, 500);
        textPanels.get(GameText.DataTitle).setVisible(false);
        textPanels.get(GameText.Data).setBounds(1600, 470, 220, 500);
        textPanels.get(GameText.Data).setVisible(false);

        textPanels.get(GameText.SelectTitle).setBounds(1630, 360, 250, 70);
        textPanels.get(GameText.Action).setBounds(1630, 675, 250, 70);
    }

    @Override
    public void updateText(GameText panel, String text) {
        textPanels.get(panel).setText(text);
    }

    public void resetText() {
        updateText(GameText.Task, "Police station\nNuclear plant\nHospital\nStore");
        if (mainFrame.getField() != null) {
            if (day) {
                updateText(GameText.DatNightTitle, "Day");
                updateText(GameText.DatNight, mainFrame.getGamaData().getNight() + "/15");
                mainFrame.getGamaData().setDay(mainFrame.getGamaData().getDay() + 1);
                day = false;
            } else {
                updateText(GameText.DatNightTitle, "Night");
                updateText(GameText.DatNight, mainFrame.getGamaData().getNight() + "/15");
                mainFrame.getGamaData().setNight(mainFrame.getGamaData().getNight() + 1);
                day = true;
            }

            int dogSize = mainFrame.getField().getAllDog().size();
            int CivilianSize = mainFrame.getField().getAllEntityOfType(EntityType.CIVILIAN, Vitality.ALIVE).size();
            int SoldierSize = mainFrame.getField().getAllEntityOfType(EntityType.SOLDIER, Vitality.ALIVE).size();
            int medicSize = mainFrame.getField().getAllEntityOfType(EntityType.MEDIC, Vitality.ALIVE).size();
            int engineerSize = mainFrame.getField().getAllEntityOfType(EntityType.MECHANIC, Vitality.ALIVE).size();
            String str = "Dog: " + dogSize + "\nPerson: " + CivilianSize + "\nSoldier: " + SoldierSize + "\nMedic: "
                    + medicSize + "\nEngineer: " + engineerSize;
            updateText(GameText.Stat, str);
        } else {
            updateText(GameText.Stat, "Noting here");
        }
    }

    // -------- Button --------//
    @Override
    public void createButton() {
        buttons = new HashMap<>();
        buttons.put(GameButton.Setting, new Button(""));
        buttons.get(GameButton.Setting).setIcon(ImageResource.SETTING_GAME.getScaledIcon(80, 80));
        buttons.put(GameButton.EndButton, new Button("END TURN", 50));
    }

    @Override
    public void setButtonBounds() {
        buttons.get(GameButton.Setting).setBounds(1820, 20, 80, 80);
        buttons.get(GameButton.EndButton).setBounds(1475, 905, 500, 50);
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

    private void endButton() {
        mainFrame.getField().endTurn(1);
        textPanels.get(GameText.SelectTitle).setText("Select");
        map.setSelect(null);

        map.resetPerRoads();
        List<Tuple> next = mainFrame.getField().getNextRoundDogCoordinates();
        next.forEach((dog) -> map.getRoad(dog.getA(), dog.getB()).setPreviewDog(true));

        entityPanel.removeAll();
        actionPanel.removeAll();
        rePaints();
        resetText();
    }

    // -------- Map --------//
    private void createMap() {
        map = new WorldMap(this, mainFrame);
    }

    private void setMapPosition() {
        map.setBounds(500, 50, 900, 900);
    }

    // -------- Entity button --------//
    public void loadEntityButton(int x, int y) {
        resetButton();
        List<Civilian> allAlive = mainFrame.getField().getBlock(new Tuple(x, y)).getAllAlive();

        allAlive.forEach((alive) -> {
            if (alive.getActionRunnable() == null && alive.isContacted()) {
                Button btn = new Button(alive.getEntityType().name(), 30);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.addActionListener(_ -> loadActionButton(alive));
                entityPanel.add(btn);
            }
        });
        
        entityPanel.setPreferredSize(new Dimension(400, Math.max(allAlive.size() * 70, 300)));
        rePaints();
    }

    // -------- Action button --------//
    private void loadActionButton(Civilian alive) {
        resetButton();
        List<ActionType> actions = new ArrayList<>();

        if (checkValidateAction(ActionType.SHOOT, alive) && alive.isArmed()) {
            actions.add(ActionType.SHOOT);
        }

        if (checkValidateAction(ActionType.MOVE, alive)) {
            actions.add(ActionType.MOVE);
        }

        if (checkValidateAction(ActionType.ARM, alive)) { 
            actions.add(ActionType.ARM);
        }

        switch (alive.getEntityType()) {
            case MECHANIC -> {
                if (checkValidateAction(ActionType.BUILD, alive)) {
                    actions.add(ActionType.BUILD);
                }
            }
            case MEDIC -> {
                if (checkValidateAction(ActionType.HEAL, alive))  {
                    actions.add(ActionType.HEAL);
                }
            }
            case SOLDIER -> {
                if (checkValidateAction(ActionType.SHOOT, alive)) {
                    actions.add(ActionType.SHOOT);
                }
            }
            default -> {}
        }

        actions.forEach(action -> {
            Button btn = new Button(action.name(), 30);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(_ -> actionButton(action, alive));
            entityPanel.add(btn);
        });

        entityPanel.setPreferredSize(new Dimension(400, Math.max(ActionType.values().length * 70, 300)));
        rePaints();
    }

    private boolean checkValidateAction(ActionType action, Civilian alive) {
        Function<Function<Direction, Boolean>, Boolean> validateAction = (validate) -> {
            for (Direction dir : Direction.values()) {
                if (validate.apply(dir)) {
                    return true;
                }
            } 
            return false;
        };
    
        return switch (action) {
            case MOVE -> validateAction.apply(alive::validateMove);
            case SHOOT -> validateAction.apply(alive::validateShoot);
            case BUILD -> validateAction.apply(((Mechanic)alive)::validateBuildBarricade);
            case ARM -> alive.validateArm();
            case HEAL -> ((Medic) alive).validateCure();
        };
    }
    
    private void actionButton(ActionType action, Civilian alive) {
        BiConsumer<Function<Direction, Boolean>, ActionType> validateAction = (validate, actionType) -> {
            int newX;
            int newY;
            for (Direction dir : Direction.values()) {
                if (validate.apply(dir)) {
                    Tuple offset = dir.getOffset();
                    newX = map.getSelect().getGridX() + offset.getA();
                    newY = map.getSelect().getGridY() + offset.getB();
                    switch (actionType) {
                        case MOVE -> map.getRoad(newX, newY).setIsCanMove(true); 
                        case SHOOT -> map.getRoad(newX, newY).setIsCanShot(true); 
                        case BUILD -> map.getRoad(newX, newY).setIsCanMove(true); 
                        default -> {}
                    }
                }
            } 
        };

        resetButton();
        boolean selectBlock = switch (action) {
            case MOVE -> {
                validateAction.accept(alive::validateMove, ActionType.MOVE);
                yield true;
            }
            case SHOOT -> {
                validateAction.accept(alive::validateShoot, ActionType.SHOOT);
                yield true;
            }
            case BUILD -> {
                validateAction.accept(((Mechanic) alive)::validateBuildBarricade, ActionType.BUILD);
                yield true;
            }
            case ARM -> {
                mainFrame.getField().addAction(ActionType.ARM, alive, alive::arm);
                yield false;
            }
            case HEAL -> {
                Medic medic = (Medic) alive;
                mainFrame.getField().addAction(ActionType.HEAL, medic, medic::cure);
                yield false;
            }
        };

        if (selectBlock) {
            map.setAction(action);
            map.setAlive(alive);
            setMode(GameMode.Action);
        } else {
            loadEntityButton(map.getSelect().getGridX(), map.getSelect().getGridY());
            loadInActionButton();
        }
        rePaints();
    }

    public void resetButton() {
        entityPanel.removeAll();
        textPanels.get(GameText.SelectTitle).setText("Select");
    }

    // -------- In Action --------//
    public void loadInActionButton() {
        textPanels.get(GameText.Action).setText("Action");
        actionPanel.removeAll();
        List<Civilian> allAlive = mainFrame.getField().getAllCivilians();
        int cont = 0;
        if (allAlive != null) {
            for (Civilian civilian : allAlive) {
                if (civilian.getActionRunnable() != null) {
                    Button btn = new Button(civilian.getEntityType().name(), 30);
                    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btn.addActionListener(_ -> inActionButton(civilian));
                    actionPanel.add(btn);
                    cont++;
                }
            }
        }
        actionPanel.setPreferredSize(new Dimension(400, Math.max(cont * 70, 300)));
        rePaints();
    }

    private void inActionButton(Civilian civilian) {
        mainFrame.getField().removeAction(civilian.getActionType(), civilian, civilian.getActionRunnable());
        loadInActionButton();
        rePaints();
    }

    public void rePaints() {
        map.repaintAllRoads();
        entityPanel.revalidate();
        entityPanel.repaint();
        actionPanel.revalidate();
        actionPanel.repaint();
        scrollActionButton.repaint();
        scrollActionButton.revalidate();
        scrollEntityButton.repaint();
        scrollEntityButton.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageResource.GAME_BACKGROUND.getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    public WorldMap getMap() {
        return map;
    }

    public void setMap(WorldMap map) {
        this.map = map;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }
}
