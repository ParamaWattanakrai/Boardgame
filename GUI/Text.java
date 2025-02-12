package GUI;

import javax.swing.*;

public class Text {
    private final JPanel panel = new JPanel();
    
    public Text() {
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBounds(560, 0, 200, 560);

        JLabel label = new JLabel("<html>" + "Game ???" + "</html>");
        panel.add(label);
    }

    public JPanel getPanel() {
        return panel;
    }
}