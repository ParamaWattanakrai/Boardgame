package GUI.components;

import GUI.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GamePanel extends JPanel {
    private final JTextArea textArea;

    public GamePanel() {
        textArea = new JTextArea();
        settingText();
    }

    public GamePanel(String str) {
        textArea = new JTextArea(str);
        settingText();
    }

    private void settingText(){
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        textArea.setWrapStyleWord(true);  
        textArea.setLineWrap(true);     
        textArea.setEditable(false);    
        textArea.setFocusable(false);     
        textArea.setPreferredSize(new Dimension(200, 50));
        textArea.setFont(new Font("Arial", Font.PLAIN,50));
        textArea.setOpaque(false);  
        textArea.setBorder(null); 

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setText(int num) {
        this.textArea.setText(Game.getRoadData().get(num).toString().replace(", ", "\n"));

    }

    public void setText(String str) {
        this.textArea.setText(str);
    }
}
