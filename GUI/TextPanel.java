package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {
    private final JTextArea textArea = new JTextArea("-------------");

    public TextPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());

        textArea.setWrapStyleWord(true);  
        textArea.setLineWrap(true);     
        textArea.setEditable(false);    
        textArea.setFocusable(false);     
        textArea.setPreferredSize(new Dimension(200, 50));
        textArea.setFont(new Font("Arial", Font.PLAIN,70));
        textArea.setOpaque(false);  
        textArea.setBorder(null); 
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void setText(int num) {
        
        this.textArea.setText(Frame.gameData.get(num).toString().replace(", ", "\n"));
    }

    public void setText(String str) {
        this.textArea.setText(str);
    }
}
