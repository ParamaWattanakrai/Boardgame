package gui.components;

import gui.utils.MinimalScrollBarUI;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {
    private JPanel panel;
    public ScrollPane() {
        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        setViewportView(panel);
        setupStyle();
    }

    private void setupStyle(){
        setOpaque(false);
        setBorder(null);
        getViewport().setOpaque(false);
        getVerticalScrollBar().setOpaque(false);
        getVerticalScrollBar().setUI(new MinimalScrollBarUI());
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(20);
    }

    public void removeAllPanel(){
        panel.removeAll();
        panel.revalidate(); 
        panel.repaint();  
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}