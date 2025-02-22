package gui.interfaces;

public interface TextDisplay <T extends Enum<T>>{
    void createTextPanel();
    void setTextPanelBounds();
    void updateText(T panel, String text);
}