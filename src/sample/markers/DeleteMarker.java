package sample.markers;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import sample.Controller;
import sample.components.MyComponent;

public class DeleteMarker extends Marker {


    public DeleteMarker(MyComponent<?> node) {
        super(node);
        circle.setFill(Color.RED);
    }

    @Override
    public void initialize() {
        circle.setOnMouseClicked(e -> {
            Controller.list.remove(getNode());
            getNode().getPane().getChildren().remove(getNode().getItem());
            getNode().getMarkers().forEach(m -> getNode().getPane().getChildren().remove(m.getCircle()));
        });
        getNode().getPane().getChildren().add(circle);
        relocate();
    }

    @Override
    public void relocate() {
        setPosition(new Point2D((getNode().getPosition().getX() + getNode().getWidth() / 2) * getNode().getPane().getScale(),
                (getNode().getPosition().getY() + getNode().getHeight() / 4) * getNode().getPane().getScale()));
        circle.setCenterX(getNode().getPane().getInnerScaleCenter().getX() + getPosition().getX());
        circle.setCenterY(getNode().getPane().getInnerScaleCenter().getY() + getPosition().getY());
    }
}
