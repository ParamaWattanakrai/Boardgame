package gui;

import gui.components.MainButton;
import gui.data.RoadData;
import gui.utils.ImageLoader;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class MainMenu extends JPanel {

    public MainMenu(MainFrame mainFrame) {
        setLayout(null);

        MainButton newGameButton = new MainButton("New Game");
        newGameButton.setBounds(850, 500, 220, 50);
        newGameButton.addActionListener((_) -> {
            MainFrame.reSetGamaData();
            MainFrame.reSetRoadData();
            for (int i = 0; i < 25; i++) MainFrame.getRoadData().add(new RoadData(i));
            mainFrame.showGame();        
        });

        MainButton resumeButton = new MainButton("Resume");
        resumeButton.setBounds(850, 670, 220, 50);
        resumeButton.addActionListener((_) -> {
            if(!MainFrame.getRoadData().isEmpty()){
                mainFrame.showGame();
            }
        }); 

        MainButton quitButton = new MainButton("Quit");
        quitButton.setBounds(850, 850, 220, 50);
        quitButton.addActionListener((_) -> {
            System.exit(0);
        });

        MainButton ruleButton = new MainButton("Rule");
        ruleButton.setBounds(110, 905, 220, 50);
        ruleButton.setFont(new Font("Arial", Font.BOLD, 65));
        ruleButton.addActionListener((_) -> {
            mainFrame.showRule();
        });

        add(newGameButton);
        add(resumeButton);
        add(quitButton);
        add(ruleButton);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("img/MainBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
