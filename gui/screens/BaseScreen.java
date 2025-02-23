package gui.screens;

import gui.MainFrame;
import javax.swing.JPanel;

public abstract class BaseScreen extends JPanel {
    protected MainFrame mainFrame;
    public BaseScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    protected abstract void initializeUI();
    protected void initialize() { initializeUI(); }
}
