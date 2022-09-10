package com.example.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.MenuBar;

public class paint extends Application {
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(320,240);
        MenuBar menu = new MenuBar();
        //set up border pane
        BorderPane border = new BorderPane();
        StackPane center = new StackPane();
        StackPane left = new StackPane();
        //Create a new window
        Scene scene = new Scene(border, 320, 240);
        stage.setTitle("JPaint");
        //Instantiate MyMenu
        MyMenu menuBar = new MyMenu(canvas, menu, stage);

        //Set the menu variable of MyMenu to the top
        border.setTop(menuBar.menuBar);
        //Set the canvas to the center
        border.setCenter(center);
        //Set Color Picker to be left
        border.setLeft(left);

        //add objects to scene
        center.getChildren().add(canvas);

        //Load Window
        stage.setScene(scene);
        stage.show();


    }

    //Launch the scene on program start
    public static void main(String[] args) {
        launch();
    }
}