package sample.markers;

import javafx.geometry.Point2D;
import sample.components.MyComponent;

public class HorizontalMarker extends Marker {
    private double prevX;
    private double prevWidth;
    private double prevCenterX;

    public HorizontalMarker(MyComponent<?> node) {
        super(node);
    }

    @Override
    public void initialize() {
        prevX = 0;
        prevWidth = getNode().getWidth();
        prevCenterX = getNode().getPosition().getX();
        circle.setOnMousePressed(e -> {
            prevX = e.getX() / getNode().getPane().getScale() - getNode().getPane().getInnerScaleCenter().getX();
            prevWidth = getNode().getWidth();
            prevCenterX = getNode().getPosition().getX();
        });
        circle.setOnMouseDragged(e -> {
            getNode().setWidth(prevWidth -
                    (e.getX() / getNode().getPane().getScale() -
                            getNode().getPane().getInnerScaleCenter().getX() - prevX));
            getNode().setPosition(new Point2D(prevCenterX + (e.getX() / getNode().getPane().getScale() -
                    getNode().getPane().getInnerScaleCenter().getX() - prevX) / 2, getNode().getPosition().getY()));
        });
        circle.setOnMouseReleased(e -> prevX = e.getX() / getNode().getPane().getScale() - getNode().getPane().getInnerScaleCenter().getX());
        getNode().getPane().getChildren().add(circle);
        relocate();
    }

    @Override
    public void relocate() {
        setPosition(new Point2D(getNode().getPosition().getX() * getNode().getPane().getScale(),
                getNode().getPosition().getY() * getNode().getPane().getScale()));
        circle.setCenterX(getNode().getPane().getInnerScaleCenter().getX() + getPosition().getX());
        circle.setCenterY(getNode().getPane().getInnerScaleCenter().getY() + getPosition().getY() + getNode().getHeight() / 2 * getNode().getPane().getScale());
    }
}
