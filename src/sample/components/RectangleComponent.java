package sample.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.MyPane;

public class RectangleComponent extends MyComponent<Rectangle> {

    public RectangleComponent(MyPane pane, Color color) {
        super(pane);
        item = new Rectangle(width, height, color);
        initialize();
    }

    @Override
    public void redraw() {
        item.setHeight(height * pane.getScale());
        item.setWidth(width * pane.getScale());
        super.redraw();
    }

}
