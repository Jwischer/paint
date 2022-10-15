package com.example.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * Contains setup for the scene
 */
public class PaintMain extends Application {
    BorderPane border;
    TabPane tabPane;
    MyMenu myMenu;
    Stage stage;
    Scene scene;

    /**
     *
     * @param stage
     * @throws IOException
     */
    public void start(Stage stage) throws IOException {
        //set up border pane
        this.stage = new Stage();
        this.tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.border = new BorderPane(); border.setId("Window");
        //Create a new window
        this.scene = new Scene(border, 320, 240);
        stage.setTitle("JPaint");
        //Instantiate MyMenu
        this.myMenu = new MyMenu(stage, scene, tabPane, border);
        //Set the menu variable of MyMenu to the top
        border.setTop(myMenu.vbox);
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

    /**
     *
     * @param args
     */
    //Launch the scene on program start
    public static void main(String[] args) {
        launch();
    }

    /**
     * Opens the given url in the default browser
     * @param url
     */
    public void browser(String url) {
        getHostServices().showDocument(url);
    }
}