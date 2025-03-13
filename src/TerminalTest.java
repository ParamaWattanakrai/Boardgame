package src;

import src.map.*;

public class TerminalTest {
    public static void main(String[] args) {
        MetaSettings metaSettings = new MetaSettings(30, 5, 5, 160, 171, 50, 1, 2, 1, 1, 5, 3, 2, 12);
        Field field = new Field(metaSettings);
        field.printField();
    }
}
