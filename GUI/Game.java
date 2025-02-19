package gui;

import gui.components.GamePanel;
import gui.components.MainButton;
import gui.map.Map;
import gui.utils.FontLoader;
import gui.utils.ImageLoader;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class Game extends JPanel {
    private static GamePanel night = new GamePanel("Night");
    private static GamePanel stat = new GamePanel("Stat");
    private static GamePanel task = new GamePanel("Task");
    private static GamePanel data = new GamePanel("Data");
    private static MainButton setting = new MainButton("");

    private static Map map = new Map();
    private Font customFont = FontLoader.loadFont("BlackOpsOne-Regular.ttf");

    public Game(MainFrame mainFrame) {
        setLayout(null);
        
        night.setBounds(60, 40, 220, 200);
        night.setText("Night\n" + MainFrame.getGamaData().getNight());

        stat.setBounds(60, 220, 220, 200);

        task.setBounds(60, 650, 220, 200);
        task.setText("Task\n" + Arrays.toString(MainFrame.getGamaData().getTask()));

        map.setBounds(482, 54, 959, 900);
        
        data.setBounds(1600, 450, 220, 500);
        data.setFont(customFont.deriveFont(25f));

        setting.setBounds(1820, 20, 80, 80);
        Image settingimg = ImageLoader.loadImage("settings.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        setting.setIcon(new ImageIcon(settingimg));
        setting.addActionListener((_) -> {
            mainFrame.showMainMenu();
        });

        add(night);
        add(stat);
        add(task);
        add(map);
        add(data);
        add(setting);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = ImageLoader.loadImage("GameBg.png");
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
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

    public static MainButton getSetting() {
        return setting;
    }

    public static void setSetting(MainButton setting) {
        Game.setting = setting;
    }
}
