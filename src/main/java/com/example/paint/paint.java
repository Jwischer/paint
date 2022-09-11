package com.example.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.scene.control.MenuBar;

public class paint extends Application {
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(320,240);
        MenuBar menu = new MenuBar();
        //set up border pane
        BorderPane border = new BorderPane();
        StackPane stackPane = new StackPane();
        ScrollPane center = new ScrollPane();
        //Create a new window
        Scene scene = new Scene(border, 320, 240);
        stage.setTitle("JPaint");
        //Instantiate MyMenu
        MyMenu menuBar = new MyMenu(canvas, menu, stage);

        //Set the menu variable of MyMenu to the top
        border.setTop(menuBar.menuBar);
        //Set the stack pane to the center
        border.setCenter(center);
        center.setContent(stackPane);
        center.setFitToWidth(true);
        center.setFitToHeight(true);

        //add objects to scene
        stackPane.getChildren().add(canvas);
        //add style sheet
        File f = new File("style.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        //Load Window
        stage.setScene(scene);
        stage.show();


    }

    //Launch the scene on program start
    public static void main(String[] args) {
        launch();
    }
}