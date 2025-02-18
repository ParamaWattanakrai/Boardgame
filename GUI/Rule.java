package gui;

import gui.components.MainButton;
import gui.utils.ImageLoader;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Rule extends JPanel{
    private MainButton setting = new MainButton("");

    public Rule(MainFrame mainFrame) {
        setLayout(null);
        setting.setBounds(1820, 20, 80, 80);
        Image settingimg = ImageLoader.loadImage("img/settingRule.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        setting.setIcon(new ImageIcon(settingimg));
        setting.addActionListener((_) -> {
            mainFrame.showMainMenu();
        });

        add(setting);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("img/RuleBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
