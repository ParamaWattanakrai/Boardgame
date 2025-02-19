package gui.components;

import gui.utils.SoundPlayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class MainButton extends JButton{

    public MainButton(String text) {
        super(text);
        setBorderPainted(false);   
        setFocusPainted(false);     
        setContentAreaFilled(false); 
        setForeground(Color.white);
        setFont(new Font("Arial", Font.BOLD, 33));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.yellow);  
                if (!"Rule".equals(text)) {
                    setFont(new Font("Arial", Font.BOLD, 35));
                }
                SoundPlayer.playSound("Select.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.white);  
                if (!"Rule".equals(text)) {
                    setFont(new Font("Arial", Font.BOLD, 33));
                }

            }
        });
        
    }
}
