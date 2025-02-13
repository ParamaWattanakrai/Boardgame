package GUI;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextPanel extends JPanel{
    private final JLabel label = new JLabel("<html>" + "Game ???" + "</html>");

    public TextPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBounds(560, 0, 200, 560);
        add(label);
    }
    
    public void setText(int num) {
        this.label.setText(Frame.gameData.get(num).toHTML());    
    }
}