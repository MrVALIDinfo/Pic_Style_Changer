package com.example.pic_style_changer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainWindow extends Application {

    private AnchorPane anchorPane;
    private ImageView originalView;
    private ImageView modifiedView;
    private Image loadedImage;
    private Image resultImage;

    @Override
    public void start(Stage primaryStage) {

        Button fileButton = new Button("File");
        ComboBox<String> comboBox = new ComboBox<>();
        Button submitButton = new Button("Submit");

        comboBox.getItems().addAll("Grayscale", "Invert", "Blur");
        fileButton.setPrefSize(70, 30);
        submitButton.setPrefSize(70, 30);
        comboBox.setPrefWidth(150);

        HBox topBar = new HBox(10, fileButton, comboBox, submitButton);
        AnchorPane.setTopAnchor(topBar, 10.0);
        AnchorPane.setLeftAnchor(topBar, 10.0);

        anchorPane = new AnchorPane();
        anchorPane.getChildren().add(topBar);

        originalView = new ImageView();
        modifiedView = new ImageView();
        originalView.setFitWidth(300);
        originalView.setFitHeight(300);
        modifiedView.setFitWidth(300);
        modifiedView.setFitHeight(300);

        originalView.setStyle("-fx-border-color: black;");
        modifiedView.setStyle("-fx-border-color: black;");

        Button saveButton = new Button("Save");
        saveButton.setPrefSize(80, 30);

        VBox modifiedSection = new VBox(10, modifiedView, saveButton);
        HBox imageRow = new HBox(40, originalView, modifiedSection);

        AnchorPane.setTopAnchor(imageRow, 70.0);
        AnchorPane.setLeftAnchor(imageRow, 20.0);
        anchorPane.getChildren().add(imageRow);

        Scene scene = new Scene(anchorPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ColorChanger");
        primaryStage.show();

        fileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                loadedImage = new Image(selectedFile.toURI().toString());
                originalView.setImage(loadedImage);
                modifiedView.setImage(null);
            }
        });

        submitButton.setOnAction(e -> {
            if (loadedImage != null && comboBox.getValue() != null) {
                resultImage = MainLogic.applyFilter(loadedImage, comboBox.getValue());
                modifiedView.setImage(resultImage);
            }
        });

        saveButton.setOnAction(e -> {
            if (resultImage != null) {
                SaveMethod.saveImage(resultImage, primaryStage);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
