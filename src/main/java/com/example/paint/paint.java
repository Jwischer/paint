package com.example.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class paint extends Application {
    public void start(Stage stage) throws IOException {
        //set up border pane
        TabPane tabPane = new TabPane();
        BorderPane border = new BorderPane();
        //Create a new window
        Scene scene = new Scene(border, 320, 240);
        StackCanvas stackCanvas = new StackCanvas();
        stage.setTitle("JPaint");
        //Instantiate MyMenu
        MyMenu menuBar = new MyMenu(stage, tabPane, border);

        //Set the menu variable of MyMenu to the top
        border.setTop(menuBar.vbox);
        //Set the stack pane to the center
        border.setCenter(tabPane);
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

    public void browser(String url) {
        getHostServices().showDocument(url);
    }
}