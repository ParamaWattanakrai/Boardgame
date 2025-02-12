package GUI.utils;

import java.awt.*;
import javax.swing.*;

public class ImageLoader {
    public static Image loadImage(String imagePath) {
        return new ImageIcon(ImageLoader.class.getResource("../"+imagePath)).getImage();
    }
}
