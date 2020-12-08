package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Grid {

    private final Group mainGroup;
    private final Group verticalGroup;
    private final Group horizontalGroup;
    private final MyPane pane;


    public Grid(MyPane myPane) {
        mainGroup = new Group();
        verticalGroup = new Group();
        horizontalGroup = new Group();
        pane = myPane;
    }

    public Group getMainGroup() {
        return mainGroup;
    }

    public Group getVerticalGroup() {
        return verticalGroup;
    }

    public Group getHorizontalGroup() {
        return horizontalGroup;
    }

    public void initialize() {
        mainGroup.getChildren().addAll(verticalGroup, horizontalGroup);
        drawVerticalLines();
        drawHorizontalLines();
    }

    private void drawVerticalLines() {
        var scale = pane.getScale();
        for (int i = 0; i < pane.getWidth() / 10 / scale; i++) {
            Line line = new Line(i * 10 * scale, 0, i * 10 * scale, pane.getHeight());
            line.setStrokeWidth(0.25);
            line.setFill(Color.LIGHTSLATEGRAY);
            verticalGroup.getChildren().add(line);
        }
    }

    private void drawHorizontalLines() {
        var scale = pane.getScale();
        for (int i = 0; i < pane.getHeight() / 10 / scale; i++) {
            Line line = new Line(0, i * 10 * scale, pane.getWidth(), i * 10 * scale);
            line.setStrokeWidth(0.25);
            line.setFill(Color.LIGHTSLATEGRAY);
            horizontalGroup.getChildren().add(line);
        }
    }

    public void redraw() {
        verticalGroup.getChildren().clear();
        horizontalGroup.getChildren().clear();
        drawHorizontalLines();
        drawVerticalLines();
    }

}
