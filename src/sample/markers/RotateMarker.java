package sample.markers;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import sample.components.MyComponent;

public class RotateMarker extends Marker {
    private double prevAngle;
    private double prevX;
    private double prevY;

    public RotateMarker(MyComponent<?> node) {
        super(node);
    }

    @Override
    public void initialize() {
        circle.setFill(Color.BLUE);

        circle.setOnMousePressed(e -> {
            prevAngle = getNode().getAngle();
            prevX = getPosition().getX();
            prevY = getPosition().getY();
        });

        circle.setOnMouseDragged(e -> {
            double x, y, radius = getPosition().distance(getNode().getPosition()) * getNode().getPane().getScale();
            x = e.getX() - getNode().getPosition().getX() * getNode().getPane().getScale() -
                    getNode().getPane().getInnerScaleCenter().getX();
            y = e.getY() - getNode().getPosition().getY() * getNode().getPane().getScale() -
                    getNode().getPane().getInnerScaleCenter().getY();
            double ang = -Math.atan(y / x);




        });

        getNode().getPane().getChildren().add(circle);
        setPosition(new Point2D(getNode().getPane().getInnerCenter().getX() + getNode().getPosition().getX() + getNode().getWidth(),
                getNode().getPane().getInnerCenter().getY() + getNode().getPosition().getY()));
        circle.setCenterX(getPosition().getX() * getNode().getPane().getScale());
        circle.setCenterY(getPosition().getY() * getNode().getPane().getScale());

    }

    @Override
    public void relocate() {
        //setPosition(new Point2D());
        circle.setCenterX(getPosition().getX() * getNode().getPane().getScale());
        circle.setCenterY(getPosition().getY() * getNode().getPane().getScale());
    }
}
