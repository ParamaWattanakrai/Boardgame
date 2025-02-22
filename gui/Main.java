package gui;
import gui.core.MainFrame;
import gui.data.GameData;

public class Main {
    public static void main(String[] args) {
        new MainFrame(null, new GameData());
    }
}
