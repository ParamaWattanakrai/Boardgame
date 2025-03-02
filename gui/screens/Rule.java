package gui.screens;

import gui.MainFrame;
import gui.components.Button;
import gui.enums.GameScreen;
import gui.enums.ImageResource;
import gui.enums.RuleButton;
import gui.interfaces.ButtonActions;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;

public class Rule extends BaseScreen implements ButtonActions<RuleButton> {
    private HashMap<RuleButton, Button> buttons;
    private List<Image> images;
    private int imageIndex = 0;

    public Rule(MainFrame mainFrame) {
        super(mainFrame);
        initialize();
    }

    @Override
    protected void initializeUI() {
        setLayout(null);
        createButton();
        setButtonBounds();
        buttons.values().forEach(this::add);
        buttons.keySet().forEach(this::addButtonListener);
        setVisible(true);
    }

    @Override
    public void createButton() {
        buttons = new HashMap<>();
        buttons.put(RuleButton.BACK, new Button(""));
        buttons.put(RuleButton.NEXTPAGE, new Button("NEXT"));
        buttons.get(RuleButton.BACK).setIcon(new ImageIcon(ImageResource.SETTING_RULE.getScaledImage(80, 80)));

        images = new ArrayList<>();
        images.add(ImageResource.RULEPAGE1.getImage());
        images.add(ImageResource.RULEPAGE2.getImage());
        images.add(ImageResource.RULEPAGE3.getImage());
        images.add(ImageResource.RULEPAGE4.getImage());
        // images.add(ImageResource.RULEPAGE5.getImage());
    }

    @Override
    public void setButtonBounds() {
        buttons.get(RuleButton.BACK).setBounds(1820, 20, 80, 80);
        buttons.get(RuleButton.NEXTPAGE).setBounds(1550, 900, 500, 70);
    }

    @Override
    public void addButtonListener(RuleButton button) {
        ActionListener actionListener = (_) -> {
            switch (button) {
                case BACK -> backButton();
                case NEXTPAGE -> NextPage();
            }
        };
        buttons.get(button).addActionListener(actionListener);
    }

    private void backButton() {
        mainFrame.showScreen(GameScreen.MAIN_MENU);
    }

    private void NextPage() {
        imageIndex++;
        if (imageIndex >= images.size()) {
            imageIndex = 0;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageResource.RULE_BACKGROUND.getImage(), 0, 0, getWidth(), getHeight(), null);
        g.drawImage(images.get(imageIndex), 0, 0, getWidth(), getHeight(), null);
    }
}
