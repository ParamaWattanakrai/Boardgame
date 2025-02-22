package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.enums.GameScreen;
import gui.enums.buttons.RuleButton;
import gui.interfaces.ButtonActions;
import gui.utils.ImageLoader;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class Rule extends AbstractScreen implements ButtonActions<RuleButton>{
    private HashMap<RuleButton, Button> buttons;

    public Rule(MainFrame mainFrame) {
        super(mainFrame);
        initialize();
    }

    @Override
    protected void initializeUI() {
        setLayout(null);
                
        createButton(); setButtonBounds();
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        setVisible(true);
    }
    
    @Override
    public void createButton() {
        buttons = new HashMap<>();
        buttons.put(RuleButton.BACK, new Button(""));
        buttons.get(RuleButton.BACK).setIcon(new ImageIcon(ImageLoader.loadImage("settingRule.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
    }

    @Override
    public void setButtonBounds() {
        buttons.get(RuleButton.BACK).setBounds(1820, 20, 80, 80);
    }

    @Override
    public void addButtonListener(RuleButton button) {
        ActionListener actionListener = (ActionEvent e) -> {
            System.out.println(e.getActionCommand());
            switch (button) {
                case BACK -> backButton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void backButton() {
        mainFrame.showScreen(GameScreen.MAIN_MENU);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("RuleBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
