package sample;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.components.*;

import javax.imageio.ImageIO;
import javax.print.PrintException;

public class Controller {

    @FXML
    private Label labelSide;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private AnchorPane midAnchor;

    @FXML
    private Button buttonPrint;

    @FXML
    private Button buttonLoadImage;

    @FXML
    private Button buttonSave;

    @FXML
    private CheckBox checkBoxGrid;

    @FXML
    private Button buttonAddText;

    @FXML
    private Button buttonFlipOver;

    @FXML
    private ChoiceBox<String> choiceBoxShape;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button buttonSetBackground;

    @FXML
    private Button buttonAddShape;

    @FXML
    private Button buttonScaleInc;

    @FXML
    private Button buttonScaleDec;

    @FXML
    private TextField textField;

    @FXML
    private Label labelScale;

    private Boolean current = true;

    private final Map<Boolean, MyPane> mapPane = new HashMap<>();

    private final Map<Boolean, String> mapSide = new HashMap<>();

    public final static List<MyComponent<?>> list = new ArrayList<>();

    private final Printer printer = new Printer();

    @FXML
    void initialize() {
        mapCompletion();

        buttonScaleInc.setOnAction(e -> {
            if (mapPane.get(current).getScale() < 2.2) {
                mapPane.get(current).setScale(mapPane.get(current).getScale() + 0.1);
                int scale = (int) (100 * mapPane.get(current).getScale());
                labelScale.setText("Масштаб " + scale + "%");
                list.forEach(MyComponent::redraw);
            }
        });

        buttonScaleDec.setOnAction(e -> {
            if (mapPane.get(current).getScale() > 0.2) {
                mapPane.get(current).setScale(mapPane.get(current).getScale() - 0.1);
                int scale = (int) (100 * mapPane.get(current).getScale());
                labelScale.setText("Масштаб " + scale + "%");
                list.forEach(MyComponent::redraw);
            }
        });

        buttonFlipOver.setOnAction(e -> {
            labelSide.setText(mapSide.get(!current));
            mapPane.get(current).setVisible(false);
            current = !current;
            mapPane.get(current).setVisible(true);
            mapPane.get(current).setGridVisible(checkBoxGrid.isSelected());
            mapPane.get(current).setScale(mapPane.get(!current).getScale());
        });

        checkBoxGrid.setOnAction(e -> mapPane.get(current).setGridVisible(checkBoxGrid.isSelected()));

        buttonSetBackground.setOnAction(e -> mapPane.get(current).
                setBackGroundColor(colorPicker.getValue()));

        buttonSave.setOnAction(e -> save());

        buttonAddShape.setOnAction(e -> {
            switch (choiceBoxShape.getValue()) {
                case "Прямоугольник":
                    list.add(new RectangleComponent(mapPane.get(current), colorPicker.getValue()));
                    break;
                case "Овал":
                    list.add(new EllipseComponent(mapPane.get(current), colorPicker.getValue()));
                default:
                    System.out.println("");
                    break;
            }
        });

        buttonLoadImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load");
            File file = fileChooser.showOpenDialog(new Stage());
            Image image = null;
            try {
                image = new Image(file.toURI().toURL().toString());
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
            list.add(new ImageComponent(mapPane.get(current), image));
        });

        buttonAddText.setOnAction(e -> list.add(new LabelComponent(mapPane.get(current), textField.getText(), colorPicker.getValue())));

        buttonPrint.setOnAction(e -> {
            File file = new File("src\\forPrint.png");
            double prevScale = mapPane.get(current).getScale();
            mapPane.values().forEach(x -> {
                x.setScale(1);
                x.setGridVisible(false);
                x.setVisible(true);
            });
            list.forEach(MyComponent::redraw);

            ImageView imageView1 = new ImageView(mapPane.get(current).snapshot(new SnapshotParameters(), null));
            mapPane.get(!current).setGridVisible(false);
            ImageView imageView2 = new ImageView(mapPane.get(!current).snapshot(new SnapshotParameters(), null));

            BufferedImage bufferedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
            bufferedImage.getGraphics().drawImage(SwingFXUtils.fromFXImage(imageView1.getImage(), null), 0, 0, null);
            bufferedImage.getGraphics().drawImage(SwingFXUtils.fromFXImage(imageView2.getImage(), null), 0, 260, null);

            try (FileOutputStream fileWriter = new FileOutputStream(file)) {
                ImageIO.write(bufferedImage, "png", fileWriter);
            } catch (IOException ex) {
                showAlert(ex);
            }

            mapPane.values().forEach(x -> {
                x.setGridVisible(checkBoxGrid.isSelected());
                x.setScale(prevScale);
            });
            list.forEach(MyComponent::redraw);
            mapPane.get(!current).setVisible(false);
            try {
                printer.print();
            } catch (PrintException printException) {
                printException.printStackTrace();
            }
        });
    }

    private void mapCompletion() {
        mapSide.put(true, "Лицевая сторона");
        mapSide.put(false, "Оборотная сторона");
        mapPane.put(true, new MyPane());
        mapPane.put(false, new MyPane());
        mapPane.get(true).initialize();
        mapPane.get(false).initialize();
        midAnchor.getChildren().add(mapPane.get(current));
        midAnchor.getChildren().add(mapPane.get(!current));
        mapPane.get(!current).setVisible(false);
        choiceBoxShape.setValue("Прямоугольник");
        choiceBoxShape.setItems(FXCollections.observableArrayList("Прямоугольник", "Овал"));
    }

    private void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        double prevScale = mapPane.get(current).getScale();
        mapPane.values().forEach(x -> {
            x.setScale(1);
            x.setGridVisible(false);
            x.setVisible(true);
        });
        list.forEach(MyComponent::redraw);

        ImageView imageView1 = new ImageView(mapPane.get(current).snapshot(new SnapshotParameters(), null));
        mapPane.get(!current).setGridVisible(false);
        ImageView imageView2 = new ImageView(mapPane.get(!current).snapshot(new SnapshotParameters(), null));

        BufferedImage bufferedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(SwingFXUtils.fromFXImage(imageView1.getImage(), null), 0, 0, null);
        bufferedImage.getGraphics().drawImage(SwingFXUtils.fromFXImage(imageView2.getImage(), null), 0, 260, null);

        try (FileOutputStream fileWriter = new FileOutputStream(file)) {
            ImageIO.write(bufferedImage, "png", fileWriter);
        } catch (IOException e) {
            showAlert(e);
        }

        mapPane.values().forEach(x -> {
            x.setGridVisible(checkBoxGrid.isSelected());
            x.setScale(prevScale);
        });
        list.forEach(MyComponent::redraw);
        mapPane.get(!current).setVisible(false);
    }

    private void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Ошибка!");
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }
}
