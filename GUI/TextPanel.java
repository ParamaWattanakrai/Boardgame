package GUI;

import javax.swing.*;

public class TextPanel {
    private final JPanel panel = new JPanel();
    private final JLabel label = new JLabel("<html>" + "Game ???" + "</html>");

    public TextPanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBounds(560, 0, 200, 560);
        panel.add(label);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getLabel() {
        return label;
    }
    
    public void setText(int num) {
        this.label.setText(Frame.gameData.get(num).toHTML());    
    }
}