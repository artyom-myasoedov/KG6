package sample.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import sample.MyPane;

public class EllipseComponent extends MyComponent<Ellipse> {

    public EllipseComponent(MyPane pane, Color color) {
        super(pane);
        item = new Ellipse(width, height);
        item.setFill(color);
        initialize();
    }

    @Override
    public void redraw() {
        item.setRadiusX(width * pane.getScale() / 2);
        item.setRadiusY(height * pane.getScale() / 2);
        super.redraw();
    }
}
