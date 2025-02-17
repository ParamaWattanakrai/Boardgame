package GUI;

import GUI.utils.TextPanel;
import java.awt.*;
import javax.swing.*;

public class LeftPanel extends JPanel {
    private TextPanel night = new TextPanel();
    private TextPanel stat = new TextPanel();
    private TextPanel task = new TextPanel();

    public LeftPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.black);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

    public TextPanel getNight() {
        return night;
    }

    public void setNight(TextPanel night) {
        this.night = night;
    }

    public TextPanel getStat() {
        return stat;
    }

    public void setStat(TextPanel stat) {
        this.stat = stat;
    }

    public TextPanel getTask() {
        return task;
    }

    public void setTask(TextPanel task) {
        this.task = task;
    }

}
