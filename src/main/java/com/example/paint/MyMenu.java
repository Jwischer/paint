package com.example.paint;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Contains all the class instances to make the two top menus in JPaint
 */
public class MyMenu{
    MenuBar menuBar;
    VBox vbox;
    ToolBar toolbar;
    Logger logger;
    GridPane buttonPane;

    MyMenu(Stage stage, Scene scene, TabPane tabPane) throws IOException {
        this.menuBar = new MenuBar();
        this.vbox = new VBox();
        this.toolbar = new ToolBar();
        this.buttonPane = new GridPane();
        toolbar.getItems().add(buttonPane);
        TextField polyInput = new TextField("3");
        //instantiate imageLoader to grab images
        ImageLoader imageLoader = new ImageLoader();
        //Menu Bar
        Menu fileMenu = new Menu("_File"); //Shortcut Alt + F
        Menu toolsMenu = new Menu("_Tools"); toolsMenu.setId("ToolsMenu"); //Shortcut Alt + T
        Menu settingsMenu = new Menu("_Settings"); //Shortcut Alt + S
        Menu drawingSubMenu = new Menu("_Drawing"); //Shortcut Alt + D (When Tools is Open)
        Menu openFileSubMenu = new Menu("_Open File"); //Shortcut Alt + O (When File is Open)
        toolsMenu.getItems().add(drawingSubMenu);
        fileMenu.getItems().add(openFileSubMenu);
        //Create new Color Picker
        ColorPicker colorPicker = new ColorPicker();
        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(toolbar);

        //Create a new button for about section
        Button aboutButton = new Button();
        aboutButton.setGraphic(imageLoader.aboutView);
        //Create and add Open File option to file menu
        MenuItem openFileNT = new MenuItem("In New Tab");
        openFileSubMenu.getItems().add(openFileNT);
        //Create and add Open File option to file menu
        MenuItem openFileST = new MenuItem("In Same Tab");
        openFileSubMenu.getItems().add(openFileST);
        //Create and add Save option to file menu
        MenuItem save = new MenuItem("Save");
        fileMenu.getItems().add(save);
        //Create and add Save As option to file menu
        MenuItem saveas = new MenuItem("Save As...");
        fileMenu.getItems().add(saveas);
        //Create and add Exit option to file menu
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().add(exit);
        //Create and add Add Border option to tools menu
        MenuItem border = new MenuItem("Add Border");
        toolsMenu.getItems().add(border);
        //Create and add Clear Canvas option to tools menu
        MenuItem clear = new MenuItem("Clear Canvas");
        toolsMenu.getItems().add(clear);
        //---------------------------------------------------//
        //Create and add pencil option to tools menu
        ToggleButton pencil = new ToggleButton();
        buttonPane.add(pencil, 0, 0);
        pencil.setGraphic(imageLoader.pencilView);
        //Create and add Line option to tools menu
        ToggleButton line = new ToggleButton();
        buttonPane.add(line, 1, 0);
        line.setGraphic(imageLoader.lineView);
        //Create and add dashed line option to tools menu
        ToggleButton dashedLine = new ToggleButton();
        buttonPane.add(dashedLine, 2, 0);
        dashedLine.setGraphic(imageLoader.dashedView);
        //Create and add Rectangle option to tools menu
        ToggleButton rectangle = new ToggleButton();
        buttonPane.add(rectangle, 3, 0);
        rectangle.setGraphic(imageLoader.rectangleView);
        //Create and add Square option to tools menu
        ToggleButton square = new ToggleButton();
        buttonPane.add(square, 4, 0);
        square.setGraphic(imageLoader.squareView);
        //Create and add oval option to tools menu
        ToggleButton ellipse = new ToggleButton();
        buttonPane.add(ellipse, 0, 1);
        ellipse.setGraphic(imageLoader.ellipseView);
        //Create and add circle option to tools menu
        ToggleButton circle = new ToggleButton();
        buttonPane.add(circle, 1, 1);
        circle.setGraphic(imageLoader.circleView);
        //Create and add eraser option to tools menu
        ToggleButton eraser = new ToggleButton();
        buttonPane.add(eraser, 2, 1);
        eraser.setGraphic(imageLoader.eraserView);
        //Add triangle option to tools menu
        ToggleButton triangle = new ToggleButton();
        buttonPane.add(triangle, 3, 1);
        triangle.setGraphic(imageLoader.triangleView);
        //Create and add polygon option to tools menu
        ToggleButton polygon = new ToggleButton();
        buttonPane.add(polygon, 4, 1);
        polygon.setGraphic(imageLoader.polyView);
        //------------------------------------------------------------------------//
        //Create and add Stroke Width option to tools menu
        MenuItem strokeWidth = new MenuItem("Stroke Width");
        settingsMenu.getItems().add(strokeWidth);
        //Create and add undo option to tools menu
        MenuItem undoOption = new MenuItem("Undo");
        toolsMenu.getItems().add(undoOption);
        //Create and add redo option to tools menu
        MenuItem redoOption = new MenuItem("Redo");
        toolsMenu.getItems().add(redoOption);
        MenuItem selectImage = new MenuItem("Select");
        toolsMenu.getItems().add(selectImage);
        MenuItem copyOption = new MenuItem("Copy");
        toolsMenu.getItems().add(copyOption);
        MenuItem pasteOption = new MenuItem("Paste");
        toolsMenu.getItems().add(pasteOption);
        MenuItem cutOption = new MenuItem("Cut");
        //toolsMenu.getItems().add(cutOption);
        CheckMenuItem autosaveTimer = new CheckMenuItem("Autosave Timer");
        settingsMenu.getItems().add(autosaveTimer);
        //add menus to menu bar
        menuBar.getMenus().addAll(fileMenu);
        menuBar.getMenus().addAll(toolsMenu);
        menuBar.getMenus().addAll(settingsMenu);
        Button eyedropper = new Button("_Grab Color"); //Shortcut Alt + G
        Button resize = new Button("_Resize Canvas"); //Shortcut Alt + R
        toolbar.getItems().add(resize);
        toolbar.getItems().add(colorPicker);
        toolbar.getItems().add(eyedropper);
        toolbar.getItems().add(aboutButton);
        TabArrays tabArrays = new TabArrays();
        Label autosaveLabel = new Label("Time Until Autosave: ");
        toolbar.getItems().add(polyInput);
        toolbar.getItems().add(autosaveLabel);

        this.logger = new Logger(pencil, line, dashedLine, rectangle, square, ellipse, circle, eraser, triangle, polygon);
        //Instantiate ButtonFunctions using the menu options
        FileMenuFunctions fileMenuFunctions = new FileMenuFunctions(stage, openFileNT, openFileST, save, saveas, exit, tabPane, tabArrays, autosaveLabel, autosaveTimer);
        SettingsMenuFunctions settingsMenuFunctions = new SettingsMenuFunctions(strokeWidth);
        ToolbarFunctions toolbarFunctions = new ToolbarFunctions(aboutButton, colorPicker, tabArrays, resize, tabPane);
        ToolsMenuFunctions toolsMenuFunctions = new ToolsMenuFunctions(border,clear, pencil, line, dashedLine, rectangle, square, ellipse, circle, triangle, undoOption, redoOption, colorPicker, settingsMenuFunctions, tabPane, tabArrays, eyedropper, eraser, polygon, selectImage, copyOption, pasteOption, cutOption, polyInput);
        KeyboardShortcuts keyboardShortcuts = new KeyboardShortcuts(scene, pencil, line, dashedLine, square, rectangle, circle, ellipse, triangle, undoOption, redoOption, eraser, polygon, copyOption, pasteOption, selectImage,cutOption);

        stage.setOnCloseRequest(windowEvent -> {
            try {
                logger.logger.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}