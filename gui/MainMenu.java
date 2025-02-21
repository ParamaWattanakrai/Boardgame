package gui;

import gui.components.MainButton;
import gui.utils.FontLoader;
import gui.utils.ImageLoader;
import gui.utils.SoundPlayer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import src.map.MetaSettings;

public class MainMenu extends JPanel {
    private Font customFont = FontLoader.loadFont("BlackOpsOne-Regular.ttf");

    public MainMenu(MainFrame mainFrame) {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 1, 2, 1, 1, 5, 3, 2, 12);
        setLayout(null);

        MainButton newGameButton = new MainButton("New Game");
        newGameButton.setBounds(715, 490, 500, 70);
        newGameButton.addActionListener((_) -> {
            MainFrame.resetGamaData();
            MainFrame.resetGame(metaSettings);
            
            mainFrame.showGame();        
        });

        MainButton resumeButton = new MainButton("Resume");
        resumeButton.setBounds(715, 660, 500, 70);
        resumeButton.addActionListener((_) -> {
            if(MainFrame.getField() != null){
                mainFrame.showGame();
            } else{
                SoundPlayer.playSound("Incorrect.wav");
            }
        }); 

        MainButton quitButton = new MainButton("Quit");
        quitButton.setBounds(715, 840, 500, 70);
        quitButton.addActionListener((_) -> {
            System.exit(0);
        });

        MainButton ruleButton = new MainButton("Rule");
        ruleButton.setBounds(120, 910, 220, 50);
        ruleButton.setFont(customFont.deriveFont(70f));
        ruleButton.addActionListener((_) -> {
            mainFrame.showRule();
        });

        add(newGameButton);
        add(resumeButton);
        add(quitButton);
        add(ruleButton);
        setVisible(true);
        SoundPlayer.loopSound("MainManu.wav");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("MainBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
