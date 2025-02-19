package gui.utils;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageLoader {
    public static Image loadImage(String imagePath) {
        return new ImageIcon(ImageLoader.class.getResource("../assets/img/"+imagePath)).getImage();
    }
}
