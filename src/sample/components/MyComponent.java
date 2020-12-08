package sample.components;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import sample.MyPane;
import sample.markers.*;

import java.util.ArrayList;
import java.util.List;

public abstract class MyComponent<T extends Node> {
    protected Point2D position;
    protected double width;
    protected double height;
    protected boolean clicked;
    protected T item;
    protected final MyPane pane;
    protected final List<Marker> markers;
    protected int angle;

    public MyComponent(MyPane pane) {
        this.pane = pane;
        position = Point2D.ZERO;
        width = 50;
        height = 50;
        markers = new ArrayList<>();
        clicked = true;
        angle = 0;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
        redraw();
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
        redraw();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        redraw();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        redraw();
    }

    public T getItem() {
        return item;
    }

    public MyPane getPane() {
        return pane;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public void initialize() {
        item.setCursor(Cursor.HAND);
        markers.add(new DeleteMarker(this));
        markers.add(new MoveMarker(this));
        markers.add(new HorizontalMarker(this));
        markers.add(new VerticalMarker(this));
        //markers.add(new RotateMarker(this));
        getPane().getChildren().add(item);
        markers.forEach(Marker::initialize);
        setVisibleMarkers();
        item.setOnMouseClicked(e -> setVisibleMarkers());
        redraw();
    }

    private void setVisibleMarkers() {
        clicked = !clicked;
        markers.forEach(m -> m.getCircle().setVisible(clicked));
    }

    public void redraw() {
        markers.forEach(Marker::relocate);
        item.relocate(getPane().getInnerScaleCenter().getX() + position.getX() * pane.getScale(),
                getPane().getInnerScaleCenter().getY() + position.getY() * pane.getScale());
        item.setRotate(angle);
    }
}
