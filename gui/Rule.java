package gui;

import gui.components.Button;
import gui.enums.GameScreen;
import gui.enums.button.RuleButton;
import gui.utils.ImageLoader;
import gui.utils.SoundPlayer;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Rule extends JPanel {
    private HashMap<RuleButton, Button> buttons;
    private MainFrame mainFrame;

    public Rule(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        createButton();
        setButtonPosition();
        initializeUI();
    }

    private void initializeUI(){
        setLayout(null);
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        setVisible(true);
    }
    
    private void createButton() {
        buttons = new HashMap<>();
        buttons.put(RuleButton.BACK, new Button(""));
        buttons.get(RuleButton.BACK).setIcon(new ImageIcon(ImageLoader.loadImage("settingRule.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
    }

    private void setButtonPosition() {
        buttons.get(RuleButton.BACK).setBounds(1820, 20, 80, 80);
    }

    private void addButtonListener(RuleButton button) {
        ActionListener actionListener = (ActionEvent e) -> {
            switch (button) {
                case BACK -> backButton();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void backButton() {
        mainFrame.showScreen(GameScreen.MAIN_MENU);
        SoundPlayer.playSound("Press.wav");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("RuleBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
