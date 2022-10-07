package com.example.paint;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static com.example.paint.FileMenuFunctions.*;
import static com.example.paint.FileMenuFunctions.OnTabClose;

/**
 * The window that pops up when trying to save as a different file format than was opened
 */
public class DataCorruptionMenu {
    Label question;
    Button yes;
    Button no;
    Stage stage;
    Scene scene;
    HBox hBox = new HBox();

    /**
     *
     * @param image
     * @param location
     */
    DataCorruptionMenu(WritableImage image, String location){
        //Set up scene
        this.yes = new Button("Save Anyways");
        this.no = new Button("Don't Save");
        this.question = new Label("Saving as this format may cause data loss.\n Are you sure you want to save?");
        GridPane gridPane = new GridPane();
        this.scene = new Scene(gridPane);
        this.stage = new Stage();
        hBox.getChildren().add(yes);
        hBox.getChildren().add(no);
        hBox.setSpacing(60);
        stage.setScene(scene);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(question, 1, 1);
        gridPane.add(hBox, 1, 3);
        GridPane.setHalignment(hBox, HPos.CENTER);
        GridPane.setHalignment(question, HPos.CENTER);
        stage.show();

        yes.setOnAction(actionEvent -> {
            saveToFile(image, location, false);
            stage.close();
        });

        no.setOnAction(actionEvent -> {
            stage.close();
        });
    }
}
