package gui;

import gui.components.MainButton;
import gui.data.GameData;
import gui.data.RoadData;
import gui.utils.ImageLoader;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JFrame {

    private static ArrayList<RoadData> roadData = new ArrayList<>();
    private static GameData gamaData = new GameData();

    public MainMenu() {
        super("Out Bark");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageLoader.loadImage("img/MainBg.png");
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        
        panel.setLayout(null);

        MainButton newGameButton = new MainButton("New Game");
        newGameButton.setBounds(850, 500, 220, 50);
        newGameButton.addActionListener((_) -> {
            new Game();
        });

        MainButton resumeButton = new MainButton("Resume");
        resumeButton.setBounds(850, 670, 220, 50);
        resumeButton.addActionListener((_) -> {
            
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
            
        });

        panel.add(newGameButton);
        panel.add(resumeButton);
        panel.add(quitButton);
        panel.add(ruleButton);

        add(panel);
        setVisible(true);
    }

    public static ArrayList<RoadData> getRoadData() {
        return roadData;
    }

    public static void setRoadData(ArrayList<RoadData> roadData) {
        MainMenu.roadData = roadData;
    }

    
    public static GameData getGamaData() {
        return gamaData;
    }

    public static void setGamaData(GameData gamaData) {
        MainMenu.gamaData = gamaData;
    }
}
