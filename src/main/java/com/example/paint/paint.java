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
        MyMenu menuBar = new MyMenu(canvas, menu);

        //set up border pane
        BorderPane border = new BorderPane();
        StackPane center = new StackPane();
        border.setTop(menu);
        border.setCenter(center);

        //Create a new window
        Scene scene = new Scene(border, 320, 240);
        stage.setTitle("Pain(t)");

        //add objects to scene
        center.getChildren().add(canvas);

        //Load Window
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {
        launch();
    }
}