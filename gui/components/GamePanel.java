package gui.components;

import gui.utils.FontLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class GamePanel extends JTextArea {

    public GamePanel(String text, float size) {
        super(text);
        Font customFont = FontLoader.loadFont("BlackOpsOne-Regular.ttf");
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        setWrapStyleWord(true);
        setLineWrap(true);
        setEditable(false);
        setFocusable(false);
        setPreferredSize(new Dimension(200, 50));
        setFont(customFont.deriveFont(size));
        setOpaque(false);
        setBorder(null);
        setForeground(Color.white);

    }
}
