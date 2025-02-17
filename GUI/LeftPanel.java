package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.components.GamePanel;

public class LeftPanel extends JPanel {
    private GamePanel night = new GamePanel();
    private GamePanel stat = new GamePanel();
    private GamePanel task = new GamePanel();

    public LeftPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        add(night, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.5;
        add(stat, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        add(task, gbc);
    }

    public GamePanel getNight() {
        return night;
    }

    public void setNight(GamePanel night) {
        this.night = night;
    }

    public GamePanel getStat() {
        return stat;
    }

    public void setStat(GamePanel stat) {
        this.stat = stat;
    }

    public GamePanel getTask() {
        return task;
    }

    public void setTask(GamePanel task) {
        this.task = task;
    }

}
