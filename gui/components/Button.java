package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import gui.utils.FontLoader;
import gui.utils.SoundPlayer;

public class Button extends JButton {
    private final Font font = FontLoader.loadFont("BlackOpsOne-Regular.ttf");
    private boolean pop = true;

    public Button() {
        setupStyle();
    }

    public Button(String text) {
        super(text);
        setupStyle();
    }

    public Button(String text, boolean pop) {
        super(text);
        this.pop = pop;
        setupStyle();
    }

    private void setupStyle(){
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.white);
        setFont(font.deriveFont(70f));
        MouseListener();
    }

    private void MouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.yellow);
                if (pop) {
                    setFont(font.deriveFont(75f));
                }
                SoundPlayer.playSound("Select.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.white);
                if (pop) {
                    setFont(font.deriveFont(70f));
                }
            }
        });
    }
}
