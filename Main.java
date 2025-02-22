import gui.MainFrame;
import gui.data.GameData;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame Game = new MainFrame(null, new GameData());
            Game.setVisible(true);
        });
    }
}
