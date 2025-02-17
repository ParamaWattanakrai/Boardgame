package GUI;

import GUI.components.MainButton;
import GUI.utils.ImageLoader;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JFrame {
    
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
            System.exit(0);
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
            System.exit(0);
        });

        panel.add(newGameButton);
        panel.add(resumeButton);
        panel.add(quitButton);
        panel.add(ruleButton);

        add(panel);
        setVisible(true);
    }
}
