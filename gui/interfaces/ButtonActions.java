package gui.interfaces;

public interface ButtonActions<T extends Enum<T>> {
    void createButton();
    void setButtonBounds();
    void addButtonListener(T button);
}