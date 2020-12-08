package sample.components;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import sample.MyPane;

public class ImageComponent extends RectangleComponent{
    public ImageComponent(MyPane pane, Image image) {
        super(pane, Color.WHITE);
        item.setFill(new ImagePattern(image));
    }
}
