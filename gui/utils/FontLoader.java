package gui.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {
    private static Font font;

    public static Font loadFont(String FontFile) {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("gui/assets/font/" + FontFile));    
            font = font.deriveFont(24f);

        }catch(FontFormatException | IOException e){
            System.out.println("Load font Error");
        }
        return font;
    }
}
