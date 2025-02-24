package gui.components;

import gui.utils.FontLoader;
import gui.utils.SoundManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Button extends JButton {
    private final Font font = FontLoader.loadFont("BlackOpsOne-Regular.ttf");
    private boolean pop = true;
    private float fontSize = 70;

    public Button() {
        setupStyle();
    }

    public Button(String text,float fontSize) {
        super(text);
        this.fontSize = fontSize;
        setupStyle();
    }

    public Button(String text) {
        super(text);
        setupStyle();
    }

    private void setupStyle(){
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.white);
        setFont(font.deriveFont(fontSize));
        MouseListener();
    }

    public void setPop(boolean pop){
        this.pop = pop;
    }

    private void MouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.yellow);
                if (pop) {
                    setFont(font.deriveFont(fontSize + 5));
                }
                SoundManager.playSelectSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.white);
                if (pop) {
                    setFont(font.deriveFont(fontSize));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                SoundManager.playButtonClickSound();
            }
        });
    }
}
