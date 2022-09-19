package com.example.paint;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CloseAppPopup {
    Label question;
    Button yes;
    Button yesandsave;
    Button no;
    Stage stage;
    Scene scene;
    HBox hBox = new HBox();
    CloseAppPopup(Stage stageToClose){
        this.yes = new Button("Close Without Saving");
        this.yesandsave = new Button("Save and Close");
        this.no = new Button("Go Back");
        this.question = new Label("Are you sure you want to close?");
        GridPane gridPane = new GridPane();
        this.scene = new Scene(gridPane);
        this.stage = new Stage();
        hBox.getChildren().add(yes);
        hBox.getChildren().add(yesandsave);
        hBox.getChildren().add(no);
        hBox.setSpacing(100);
        stage.setScene(scene);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(question, 1, 1);
        gridPane.add(hBox, 1, 3);
        GridPane.setHalignment(hBox, HPos.CENTER);
        GridPane.setHalignment(question, HPos.CENTER);
        stage.show();

        yes.setOnAction(actionEvent -> {
            System.out.println("Y");
            stage.close();
            stageToClose.close();
        });

        yes.setOnAction(actionEvent -> {
            System.out.println("Y");
            //add save function here
            stage.close();
            stageToClose.close();
        });

        no.setOnAction(actionEvent -> {
            System.out.println("N");
            stage.close();
        });
    }
}
