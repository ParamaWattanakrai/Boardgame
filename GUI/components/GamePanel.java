package gui.components;

import gui.MainMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class GamePanel extends JTextArea {

    public GamePanel(String text) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        setWrapStyleWord(true);  
        setLineWrap(true);     
        setEditable(false);    
        setFocusable(false);     
        setPreferredSize(new Dimension(200, 50));
        setFont(new Font("Arial", Font.BOLD,50));
        setOpaque(false);  
        setBorder(null); 
        setForeground(Color.white);
    }

    public void setData(int num) {
        this.setText(MainMenu.getRoadData().get(num).toString().replace(", ", "\n"));
    }


}
