package gui;

import gui.components.GamePanel;
import gui.components.MainButton;
import gui.map.Map;
import gui.utils.ImageLoader;
import java.awt.*;
import javax.swing.*;

public class Game extends JPanel {
    private final GamePanel nightTitel = new GamePanel("Night", 70f);
    private final GamePanel statTitel = new GamePanel("Stat", 70f);
    private final GamePanel taskTitel = new GamePanel("Task", 70f);
    private final GamePanel dataTitel = new GamePanel("Data", 70f);

    private static GamePanel night = new GamePanel("", 60f);
    private static GamePanel stat = new GamePanel("wow", 50f);
    private static GamePanel task = new GamePanel("", 30f);
    private static GamePanel data = new GamePanel("", 25f);
    private static MainButton setting = new MainButton("");
    private static Map map = new Map();

    public Game(MainFrame mainFrame) {
        setLayout(null);
        nightTitel.setBounds(60, 25, 220, 200);
        night.setBounds(60,95, 220, 200);

        statTitel.setBounds(60, 210, 220, 200);
        stat.setBounds(60, 280, 220, 200);

        taskTitel.setBounds(60, 640, 220, 200);
        task.setBounds(60, 720, 250, 200);

        map.setBounds(482, 54, 959, 900);

        dataTitel.setBounds(1600, 430, 220, 500);
        data.setBounds(1600, 520, 220, 500);

        setting.setBounds(1820, 20, 80, 80);
        setting.setIcon(new ImageIcon(ImageLoader.loadImage("settings.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        setting.addActionListener((_) -> {
            mainFrame.showMainMenu();
        });

        add(nightTitel);
        add(night);

        add(statTitel);
        add(stat);

        add(taskTitel);
        add(task);
        
        add(map);

        add(dataTitel);
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
