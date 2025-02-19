package gui.components;

import gui.utils.FontLoader;
import gui.utils.SoundPlayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class MainButton extends JButton {

    public MainButton(String text) {
        super(text);
        Font customFont = FontLoader.loadFont("BlackOpsOne-Regular.ttf");
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.white);
        setFont(customFont.deriveFont(70f));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.yellow);
                if (!"Rule".equals(text)) {
                    setFont(customFont.deriveFont(75f));
                }
                SoundPlayer.playSound("Select.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.white);
                if (!"Rule".equals(text)) {
                    setFont(customFont.deriveFont(70f));
                }
            }
        });
    }
}
