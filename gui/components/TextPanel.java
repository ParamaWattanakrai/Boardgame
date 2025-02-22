package gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import gui.utils.FontLoader;

public class TextPanel extends JTextArea {
    private final Font font = FontLoader.loadFont("BlackOpsOne-Regular.ttf");
     
    public TextPanel(float size) {
        setupStyle(size);
    }

    public TextPanel(String text, float size) {
        super(text);
        setupStyle(size);
    }

    private void setupStyle(float size){
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        setWrapStyleWord(true);
        setLineWrap(true);
        setEditable(false);
        setFocusable(false);
        setPreferredSize(new Dimension(200, 50));
        setFont(font.deriveFont(size));
        setOpaque(false);
        setBorder(null);
        setForeground(Color.white);
    }
}
