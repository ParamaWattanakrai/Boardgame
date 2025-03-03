package gui.utils;

import gui.enums.SoundResource;
import javax.swing.*;

public class GifPlayer extends JLabel {
    private Timer timer;
    private boolean isPlaying = false;

    public GifPlayer() {
        setLayout(null);
        setHorizontalAlignment(JLabel.CENTER);
    }

    public void playGif(ImageIcon gif) {
        if (isPlaying) return;
        setVisible(true);
        isPlaying = true;

        setIcon(gif);

        timer = new Timer(2400, (_) -> {
            SoundPlayer.playSound(SoundResource.Shot.getSound());
            setIcon(null);
            isPlaying = false;
            timer.stop();
            setVisible(false);
        });

        timer.setRepeats(false);
        timer.start();
    }
}
