package sample.components;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.MyPane;

public class LabelComponent extends MyComponent<Label> {
    public LabelComponent(MyPane pane, String str, Color color) {
        super(pane);
        item = new Label(str);
//        width = item.getWidth();
//        height = item.getHeight();
        item.setTextFill(color);
        initialize();
    }

    @Override
    public void redraw() {
        item.setPrefHeight(height * pane.getScale());
        item.setFont(Font.font(height / 2 * getPane().getScale()));
        item.setPrefWidth(width * pane.getScale());
        super.redraw();
    }
}
