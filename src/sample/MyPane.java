package sample;


import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyPane extends Pane {
    private final static double defaultHeight = 250;
    private final static double defaultWidth = 450;
    private final static double defaultX = 275;
    private final static double defaultY = 225;
    private final Grid grid;
    private final Rectangle backGround;
    private final Point2D center;
    private double scale;

    public MyPane() {
        super();
        grid = new Grid(this);
        backGround = new Rectangle(450, 250, Color.WHITE);
        center = new Point2D(500, 375);
    }

    public Grid getGrid() {
        return grid;
    }

    public Point2D getCenter() {
        return center;
    }

    public Point2D getInnerScaleCenter() {
        return new Point2D(defaultWidth / 2 * scale, defaultHeight / 2 * scale);
    }

    public Point2D getInnerCenter() {
        return new Point2D(defaultWidth / 2, defaultHeight / 2);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        redraw();
    }

    public void setGridVisible(boolean visible) {
        grid.getMainGroup().setVisible(visible);
    }

    public void setBackGroundColor(Color color) {
        backGround.setFill(color);
    }

    public void initialize() {
        setLayoutX(275);
        setLayoutY(225);
        setHeight(defaultHeight);
        setWidth(defaultWidth);
        getChildren().add(backGround);
        scale = 1;
        grid.initialize();
        getChildren().add(grid.getMainGroup());
        setGridVisible(false);
    }

    public void redraw() {
        resize();
        relocate();
        grid.redraw();
    }

    private void resize() {
        setHeight(defaultHeight * scale);
        setWidth(defaultWidth * scale);
        backGround.setHeight(defaultHeight * scale);
        backGround.setWidth(defaultWidth * scale);
    }

    private void relocate() {
        setLayoutX(center.getX() - defaultWidth / 2 * scale);
        setLayoutY(center.getY() - defaultHeight / 2 * scale);
    }


}
