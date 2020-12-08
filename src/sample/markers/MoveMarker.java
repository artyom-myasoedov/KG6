package sample.markers;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import sample.components.MyComponent;

public class MoveMarker extends Marker {

    public MoveMarker(MyComponent<?> node) {
        super(node);
        circle.setFill(Color.LIGHTSLATEGRAY);
    }

    @Override
    public void initialize() {
        circle.setOnMouseDragged(e -> {
            getNode().setPosition(new Point2D(e.getX() / getNode().getPane().getScale() - getNode().getPane().getInnerCenter().getX() - getNode().getWidth() / 2,
                    e.getY() / getNode().getPane().getScale() - getNode().getPane().getInnerCenter().getY() - getNode().getHeight() / 2));
        });
        getNode().getPane().getChildren().add(circle);
        relocate();
    }

    @Override
    public void relocate() {
        setPosition(new Point2D((getNode().getPosition().getX() + getNode().getWidth() / 2) * getNode().getPane().getScale(),
                (getNode().getPosition().getY() + getNode().getHeight() / 2) * getNode().getPane().getScale()));
        circle.setCenterX(getNode().getPane().getInnerScaleCenter().getX() + getPosition().getX());
        circle.setCenterY(getNode().getPane().getInnerScaleCenter().getY() + getPosition().getY());
    }
}
