package sample.markers;

import javafx.geometry.Point2D;
import sample.components.MyComponent;

public class VerticalMarker extends Marker{

    private double prevY;
    private double prevHeight;
    private double prevCenterY;

    public VerticalMarker(MyComponent<?> node) {
        super(node);
    }

    @Override
    public void initialize() {
        prevY = 0;
        prevHeight = getNode().getHeight();
        prevCenterY = getNode().getPosition().getY();
        circle.setOnMousePressed(e -> {
            prevY = e.getY() / getNode().getPane().getScale() - getNode().getPane().getInnerScaleCenter().getY();
            prevHeight = getNode().getHeight();
            prevCenterY = getNode().getPosition().getY();
        });
        circle.setOnMouseDragged(e -> {
            getNode().setHeight(prevHeight -
                    (e.getY() / getNode().getPane().getScale() -
                            getNode().getPane().getInnerScaleCenter().getY() - prevY));
            getNode().setPosition(new Point2D(getNode().getPosition().getX(), prevCenterY + (e.getY() / getNode().getPane().getScale() -
                    getNode().getPane().getInnerScaleCenter().getY() - prevY) / 2));
        });
        circle.setOnMouseReleased(e -> prevY = e.getY() / getNode().getPane().getScale() - getNode().getPane().getInnerScaleCenter().getY());
        getNode().getPane().getChildren().add(circle);
        relocate();
    }

    @Override
    public void relocate() {
        setPosition(new Point2D(getNode().getPosition().getX() * getNode().getPane().getScale(),
                getNode().getPosition().getY() * getNode().getPane().getScale()));
        circle.setCenterX(getNode().getPane().getInnerScaleCenter().getX() + getPosition().getX() + getNode().getWidth() / 2 * getNode().getPane().getScale());
        circle.setCenterY(getNode().getPane().getInnerScaleCenter().getY() + getPosition().getY());
    }
}
