package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.components.ScrollPane;
import gui.components.TextArea;
import gui.components.WorldMap;
import gui.enums.GameButton;
import gui.enums.GameMode;
import gui.enums.GameScreen;
import gui.enums.GameScroll;
import gui.enums.GameText;
import gui.enums.ImageResource;
import gui.interfaces.ButtonActions;
import gui.interfaces.TextDisplay;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import src.entities.*;
import src.enums.*;

public class Game extends BaseScreen implements ButtonActions<GameButton>, TextDisplay<GameText> {
    private HashMap<GameText, TextArea> textPanels;
    private HashMap<GameButton, Button> buttons;
    private HashMap<GameScroll, ScrollPane> scrollPanes;
    private WorldMap map;
    private Boolean day = true;
    private GameMode mode = GameMode.DEFAULT;
    private MainMenu mainMenu;
    private boolean isSoundOn = true;

    public Game(MainFrame mainFrame, MainMenu mainMenu) {
        super(mainFrame);
        this.mainMenu = mainMenu;
        initialize();
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public void setSoundOn(boolean soundOn) {
        isSoundOn = soundOn;
    }

    public HashMap<GameButton, Button> getButtons() {
        return buttons;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
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

        createScrollPane();
        setScrollPaneBounds();

        textPanels.values().forEach(this::add);
        buttons.values().forEach(this::add);
        scrollPanes.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        add(map);
        setVisible(true);
    }

    // -------- TextPanel --------//
    @Override
    public void createTextPanel() {
        textPanels = new HashMap<>();

        textPanels.put(GameText.DATNIGHTTITLE, new TextArea("Day", 60f));
        textPanels.put(GameText.STATTITLE, new TextArea("Stat", 60f));
        textPanels.put(GameText.TASKTITLE, new TextArea("Task", 60f));
        textPanels.put(GameText.DATATITLE, new TextArea("Data", 60f));
        textPanels.put(GameText.SELECTTITLE, new TextArea("Select", 50f));
        textPanels.put(GameText.ACTION, new TextArea("Action", 50f));

        textPanels.put(GameText.DATNIGHT, new TextArea(60f));
        textPanels.put(GameText.STAT, new TextArea(30f));
        textPanels.put(GameText.TASK, new TextArea(27f));
        textPanels.put(GameText.DATA, new TextArea(20f));
        resetText();
    }

    @Override
    public void setTextPanelBounds() {
        textPanels.get(GameText.DATNIGHTTITLE).setBounds(60, 25, 220, 200);
        textPanels.get(GameText.DATNIGHT).setBounds(60, 95, 220, 200);

        textPanels.get(GameText.STATTITLE).setBounds(60, 220, 220, 200);
        textPanels.get(GameText.STAT).setBounds(60, 305, 220, 200);

        textPanels.get(GameText.TASKTITLE).setBounds(60, 650, 220, 200);
        textPanels.get(GameText.TASK).setBounds(60, 740, 250, 200);

        textPanels.get(GameText.DATATITLE).setBounds(1600, 380, 220, 500);
        textPanels.get(GameText.DATATITLE).setVisible(false);
        textPanels.get(GameText.DATA).setBounds(1600, 470, 220, 500);
        textPanels.get(GameText.DATA).setVisible(false);

        textPanels.get(GameText.SELECTTITLE).setBounds(1630, 360, 250, 70);
        textPanels.get(GameText.ACTION).setBounds(1630, 675, 250, 70);
    }

    @Override
    public void updateText(GameText panel, String text) {
        textPanels.get(panel).setText(text);
    }

    public void resetText() {
        if (mainFrame.getField() != null) {
            String HOSPITAL = "HOSPITAL\n";
            String STORE = "STORE\n";
            String POWERPLANT = "POWERPLANT\n";
            String POLICESTATION = "POLICESTATION\n";

            if (mainFrame.getField().getOccupationMap().get(BlockType.HOSPITAL))
                HOSPITAL = "\n";
            if (mainFrame.getField().getOccupationMap().get(BlockType.POLICESTATION))
                POLICESTATION = "\n";
            if (mainFrame.getField().getOccupationMap().get(BlockType.POWERPLANT))
                POWERPLANT = "\n";
            if (mainFrame.getField().getOccupationMap().get(BlockType.STORE))
                STORE = "\n";

            updateText(GameText.TASK, HOSPITAL + STORE + POWERPLANT + POLICESTATION);
            if (day) {
                updateText(GameText.DATNIGHTTITLE, "Day");
                updateText(GameText.DATNIGHT, mainFrame.getGamaData().getNight() + "/15");
                mainFrame.getGamaData().setDay(mainFrame.getGamaData().getDay() + 1);
                day = false;
            } else {
                updateText(GameText.DATNIGHTTITLE, "Night");
                updateText(GameText.DATNIGHT, mainFrame.getGamaData().getNight() + "/15");
                mainFrame.getGamaData().setNight(mainFrame.getGamaData().getNight() + 1);
                day = true;
            }

            int dogSize = mainFrame.getField().getAllDog().size();
            int CivilianSize = mainFrame.getField().getAllEntityOfType(EntityType.CIVILIAN, Vitality.ALIVE).size();
            int SoldierSize = mainFrame.getField().getAllEntityOfType(EntityType.SOLDIER, Vitality.ALIVE).size();
            int medicSize = mainFrame.getField().getAllEntityOfType(EntityType.MEDIC, Vitality.ALIVE).size();
            int mechanicSize = mainFrame.getField().getAllEntityOfType(EntityType.MECHANIC, Vitality.ALIVE).size();

            boolean allOccupied = mainFrame.getField().getOccupationMap().get(BlockType.HOSPITAL) == Boolean.TRUE &&
                    mainFrame.getField().getOccupationMap().get(BlockType.POLICESTATION) == Boolean.TRUE &&
                    mainFrame.getField().getOccupationMap().get(BlockType.POWERPLANT) == Boolean.TRUE &&
                    mainFrame.getField().getOccupationMap().get(BlockType.STORE) == Boolean.TRUE;
            boolean isPopulationLow = CivilianSize < 6 || SoldierSize < 1 || medicSize < 1 || mechanicSize < 1;

            if (isPopulationLow || mainFrame.getGamaData().getNight() > 15) {
                mainFrame.showScreen(GameScreen.GAMEOVER);
            } else if (mainFrame.getGamaData().getNight() >= 15 && allOccupied) {
                mainFrame.showScreen(GameScreen.GAMEWIN);
            }

            String str = "Dog: " + dogSize + "\nCivilian: " + CivilianSize + "\nSoldier: " + SoldierSize + "\nMedic: "
                    + medicSize + "\nMechanic: " + mechanicSize;
            updateText(GameText.STAT, str);
        } else {
            updateText(GameText.STAT, "Noting here");
        }
    }

    // -------- Button --------//
    @Override
    public void createButton() {
        buttons = new HashMap<>();
        buttons.put(GameButton.SETTING, new Button(""));
        buttons.get(GameButton.SETTING).setIcon(ImageResource.SETTING_GAME.getScaledIcon(80, 80));
        buttons.put(GameButton.ENDBUTTON, new Button("END TURN", 50));
        buttons.put(GameButton.SOUNDON, new Button(""));
        buttons.get(GameButton.SOUNDON).setIcon(ImageResource.SOUND.getScaledIcon(80, 80));
        buttons.put(GameButton.SOUNDOFF, new Button(""));
        buttons.get(GameButton.SOUNDOFF).setVisible(false);
        buttons.get(GameButton.SOUNDOFF).setIcon(ImageResource.SOUND_OFF.getScaledIcon(80, 80));
    }

    @Override
    public void setButtonBounds() {
        buttons.get(GameButton.SETTING).setBounds(1820, 20, 80, 80);
        buttons.get(GameButton.ENDBUTTON).setBounds(1475, 905, 500, 50);
        buttons.get(GameButton.SOUNDON).setBounds(1530, 20, 80, 80);
        buttons.get(GameButton.SOUNDOFF).setBounds(1530, 20, 80, 80);
    }

    @Override
    public void addButtonListener(GameButton button) {
        ActionListener actionListener = (ActionEvent e) -> {
            System.out.println(e.getActionCommand());
            switch (button) {
                case SETTING -> settingButton();
                case ENDBUTTON -> endButton();
                case SOUNDON -> soundButton();
                case SOUNDOFF -> soundButton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void settingButton() {
        mainFrame.showScreen(GameScreen.MAIN_MENU);
    }

    private void endButton() {
        map.resetPerRoads();
        mainFrame.getField().endTurn(1);
        mainFrame.getField().getNextRoundDogCoordinates()
                .forEach((dog) -> map.getRoad(dog.getA(), dog.getB()).setPreviewDog(true));
        scrollPanes.values().forEach((action) -> action.removeAllPanel());

        map.setSelect(null);
        map.resetActionRoads();
        map.repaintAllRoads();
        resetText();

    }

    // -------- Scroll Panel --------//
    public void createScrollPane() {
        scrollPanes = new HashMap<>();
        for (GameScroll scroll : GameScroll.values()) {
            scrollPanes.put(scroll, new ScrollPane());
        }
    }

    public void setScrollPaneBounds() {
        scrollPanes.get(GameScroll.ENTITY).setBounds(1580, 440, 300, 150);
        scrollPanes.get(GameScroll.ACTION).setBounds(1580, 740, 300, 100);
    }

    // -------- Map --------//
    private void createMap() {
        map = new WorldMap(this, mainFrame);
    }

    private void setMapPosition() {
        map.setBounds(500, 50, 900, 900);
    }

    // -------- Scroll Entity --------//
    public void loadEntityButton(int x, int y) {
        scrollPanes.get(GameScroll.ENTITY).removeAllPanel();
        List<Civilian> allAlive = mainFrame.getField().getBlock(new Tuple(x, y)).getAllAlive();

        allAlive.forEach((alive) -> {
            if (alive.getActionRunnable() == null && alive.isContacted()) {
                Button btn = new Button(alive.getEntityType().name(), 30);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.addActionListener(_ -> loadActionButton(alive));
                scrollPanes.get(GameScroll.ENTITY).getPanel().add(btn);
            }
        });

        scrollPanes.get(GameScroll.ENTITY).getPanel().revalidate();
        scrollPanes.get(GameScroll.ENTITY).getPanel().repaint();
    }

    private void loadActionButton(Civilian alive) {
        scrollPanes.get(GameScroll.ENTITY).removeAllPanel();
        List<ActionType> actions = new ArrayList<>();

        if (checkValidateAction(ActionType.SHOOT, alive) && alive.isArmed()) {
            actions.add(ActionType.SHOOT);
        }

        if (checkValidateAction(ActionType.MOVE, alive)) {
            actions.add(ActionType.MOVE);
        }

        if (checkValidateAction(ActionType.ARM, alive) && alive.getEntityType() != EntityType.SOLDIER) {
            actions.add(ActionType.ARM);
        }

        switch (alive.getEntityType()) {
            case MECHANIC -> {
                if (checkValidateAction(ActionType.BUILD, alive)) {
                    actions.add(ActionType.BUILD);
                }
            }
            case MEDIC -> {
                if (checkValidateAction(ActionType.HEAL, alive)) {
                    actions.add(ActionType.HEAL);
                }
            }
            case SOLDIER -> {
                if (checkValidateAction(ActionType.SHOOT, alive)) {
                    actions.add(ActionType.SHOOT);
                }
            }
            default -> {
            }
        }

        actions.forEach(action -> {
            Button btn = new Button(action.name(), 30);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(_ -> actionButton(action, alive));
            scrollPanes.get(GameScroll.ENTITY).getPanel().add(btn);
        });

        scrollPanes.get(GameScroll.ENTITY).getPanel().revalidate();
        scrollPanes.get(GameScroll.ENTITY).getPanel().repaint();
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
            case BUILD -> validateAction.apply(((Mechanic) alive)::validateBuildBarricade);
            case ARM -> alive.validateArm();
            case HEAL -> ((Medic) alive).validateCure();
        };
    }

    private void actionButton(ActionType action, Civilian alive) {
        scrollPanes.get(GameScroll.ENTITY).removeAllPanel();
        BiConsumer<Function<Direction, Boolean>, ActionType> validateAction = (validate, actionType) -> {
            int newX;
            int newY;
            for (Direction dir : Direction.values()) {
                if (validate.apply(dir)) {
                    Tuple offset = dir.getOffset();
                    newX = map.getSelect().getGridX() + offset.getA();
                    newY = map.getSelect().getGridY() + offset.getB();
                    switch (actionType) {
                        case MOVE, BUILD -> map.getRoad(newX, newY).setCanMove(true);
                        case SHOOT -> map.getRoad(newX, newY).setCanShot(true);
                        default -> {
                        }
                    }
                }
            }
        };

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
            map.repaintAllRoads();
            scrollPanes.get(GameScroll.ENTITY).removeAllPanel();
            setMode(GameMode.ACTION);
        } else {
            loadEntityButton(map.getSelect().getGridX(), map.getSelect().getGridY());
            loadInActionButton();
        }
    }

    public void loadInActionButton() {
        scrollPanes.get(GameScroll.ACTION).removeAllPanel();
        List<Civilian> allAlive = mainFrame.getField().getAllCivilians();

        if (allAlive != null) {
            allAlive.forEach((alive) -> {
                if (alive.getActionRunnable() != null) {
                    Button btn = new Button(alive.getEntityType().name(), 30);
                    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btn.addActionListener(_ -> inActionButton(alive));
                    scrollPanes.get(GameScroll.ACTION).getPanel().add(btn);
                }
            });
        }

        scrollPanes.get(GameScroll.ACTION).getPanel().revalidate();
        scrollPanes.get(GameScroll.ACTION).getPanel().repaint();
    }

    private void inActionButton(Civilian civilian) {
        mainFrame.getField().removeAction(civilian.getActionType(), civilian, civilian.getActionRunnable());
        loadInActionButton();
        map.repaintAllRoads();
    }

    public void resetButton() {
        scrollPanes.get(GameScroll.ENTITY).removeAllPanel();
        scrollPanes.get(GameScroll.ACTION).removeAllPanel();
    }

    public void soundButton() {
        if (isSoundOn) {
            isSoundOn = false;
            buttons.get(GameButton.SOUNDON).setVisible(false);
            buttons.get(GameButton.SOUNDOFF).setVisible(true);
        } else {
            isSoundOn = true;
            buttons.get(GameButton.SOUNDON).setVisible(true);
            buttons.get(GameButton.SOUNDOFF).setVisible(false);
        }
        mainMenu.soundbotton();
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
