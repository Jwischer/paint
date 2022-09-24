package com.example.paint;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ResizeWindow {
    GridPane gridPane = new GridPane();
    TextField xVal = new TextField("X");
    TextField yVal = new TextField("Y");
    Button confirm = new Button("Apply");
    Stage stage = new Stage();
    Scene scene = new Scene(gridPane,350,75);
    ResizeWindow(TabPane tabPane, TabArrays tabArrays){
        //Set up scene
        gridPane.add(xVal, 0 , 0);
        gridPane.add(yVal, 2, 0);
        gridPane.add(confirm,1,1);
        stage.setScene(scene);
        stage.show();

        confirm.setOnAction(actionEvent -> {
            //Get values in text boxes
            int pixelx = Integer.valueOf(xVal.getCharacters().toString());
            int pixely = Integer.valueOf(yVal.getCharacters().toString());
            int canvasIndex = tabPane.getSelectionModel().getSelectedIndex();
            //Change width and height of canvas to match
            tabArrays.stackCanvas[canvasIndex].canvas.setWidth(pixelx);
            tabArrays.stackCanvas[canvasIndex].canvas.setHeight(pixely);
            stage.close();
        });
    }
}
