package gui;

import gui.components.GamePanel;
import gui.data.RoadData;
import gui.map.Map;
import gui.utils.ImageLoader;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class Game extends JFrame {
    private static GamePanel night = new GamePanel("Night");
    private static GamePanel stat = new GamePanel("Stat");
    private static GamePanel task = new GamePanel("Task");
    private static GamePanel data = new GamePanel("Data");
    private static Map map = new Map();

    public Game() {
        super("Out Bark");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // new game
        for (int i = 0; i < 25; i++) MainMenu.getRoadData().add(new RoadData(i));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageLoader.loadImage("img/GameBg.png");
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };

        panel.setLayout(null);
        
        night.setBounds(60, 40, 220, 200);
        night.setText("Stat\n" + MainMenu.getGamaData().getNight());

        stat.setBounds(60, 220, 220, 200);

        task.setBounds(60, 650, 220, 200);
        task.setText("Task\n" + Arrays.toString(MainMenu.getGamaData().getTask()));

        map.setBounds(482, 54, 959, 900);
        
        data.setBounds(1600, 450, 220, 500);
        data.setFont(new Font("Arial", Font.BOLD,30));
        
        panel.add(night);
        panel.add(stat);
        panel.add(task);
        panel.add(map);
        panel.add(data);

        add(panel);
        setVisible(true);
    }

    public static GamePanel getData() {
        return data;
    }

    public static void setData(GamePanel data) {
        Game.data = data;
    }

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        Game.map = map;
    }

    public static GamePanel getNight() {
        return night;
    }

    public static void setNight(GamePanel night) {
        Game.night = night;
    }

    public static GamePanel getStat() {
        return stat;
    }

    public static void setStat(GamePanel stat) {
        Game.stat = stat;
    }

    public static GamePanel getTask() {
        return task;
    }

    public static void setTask(GamePanel task) {
        Game.task = task;
    }
}
