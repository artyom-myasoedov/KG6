package sample.markers;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.components.MyComponent;

public abstract class Marker {
    private static final double RADIUS = 3;
    private Point2D position;
    protected Circle circle;
    private final MyComponent<?> node;

    public Marker(MyComponent<?> node) {
        this.node = node;
        position = Point2D.ZERO;
        circle = new Circle(RADIUS, Color.LIGHTSLATEGRAY);
        circle.setCursor(Cursor.HAND);
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Circle getCircle() {
        return circle;
    }

    public MyComponent<?> getNode() {
        return node;
    }

    public abstract void initialize();

    public abstract void relocate();
}
