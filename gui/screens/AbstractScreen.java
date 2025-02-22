package gui.screens;

import gui.core.MainFrame;
import javax.swing.JPanel;

public abstract class AbstractScreen extends JPanel {
    protected MainFrame mainFrame;
    public AbstractScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    protected abstract void initializeUI();
    protected void initialize() { initializeUI(); }
}
