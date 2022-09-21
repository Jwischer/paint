package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class MyMenu{
    MenuBar menuBar = new MenuBar();
    VBox vbox = new VBox();
    ToolBar toolbar = new ToolBar();


    MyMenu(Stage stage, TabPane tabPane, BorderPane borderPane) {
        //instantiate imageLoader to grab images
        ImageLoader imageLoader = new ImageLoader();
        //Menu Bar
        Menu fileMenu = new Menu("File");
        Menu toolsMenu = new Menu("Tools");
        Menu settingsMenu = new Menu("Settings");
        Menu drawingSubMenu = new Menu("Drawing");
        Menu openFileSubMenu = new Menu("Open File");
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
        //Create and add pencil option to tools menu
        CheckMenuItem pencil = new CheckMenuItem("Pencil");
        drawingSubMenu.getItems().add(pencil);
        //Create and add Line option to tools menu
        CheckMenuItem line = new CheckMenuItem("Draw Line");
        drawingSubMenu.getItems().add(line);
        //Create and add dashed line option to tools menu
        CheckMenuItem dashedLine = new CheckMenuItem("Draw Dashed Line");
        drawingSubMenu.getItems().add(dashedLine);
        //Create and add Rectangle option to tools menu
        CheckMenuItem rectangle = new CheckMenuItem("Draw Rectangle");
        drawingSubMenu.getItems().add(rectangle);
        //Create and add Square option to tools menu
        CheckMenuItem square = new CheckMenuItem("Draw Square");
        drawingSubMenu.getItems().add(square);
        //Create and add oval option to tools menu
        CheckMenuItem ellipse = new CheckMenuItem("Draw Ellipse");
        drawingSubMenu.getItems().add(ellipse);
        //Create and add circle option to tools menu
        CheckMenuItem circle = new CheckMenuItem("Draw Circle");
        drawingSubMenu.getItems().add(circle);
        //Create and add Stroke Width option to tools menu
        MenuItem strokeWidth = new MenuItem("Stroke Width");
        settingsMenu.getItems().add(strokeWidth);
        //Create and add undo option to tools menu
        MenuItem undoOption = new MenuItem("Undo");
        toolsMenu.getItems().add(undoOption);
        //add menus to menu bar
        menuBar.getMenus().addAll(fileMenu);
        menuBar.getMenus().addAll(toolsMenu);
        menuBar.getMenus().addAll(settingsMenu);
        Button eyedropper = new Button("Grab Color");
        toolbar.getItems().add(eyedropper);
        toolbar.getItems().add(colorPicker);
        toolbar.getItems().add(aboutButton);
        TabArrays tabArrays = new TabArrays();

        //Instantiate ButtonFunctions using the menu options
        FileMenuFunctions fileMenuFunctions = new FileMenuFunctions(stage, openFileNT, openFileST, save, saveas, exit, tabPane, tabArrays);
        SettingsMenuFunctions settingsMenuFunctions = new SettingsMenuFunctions(strokeWidth);
        ToolbarFunctions toolbarFunctions = new ToolbarFunctions(aboutButton, colorPicker, tabArrays);
        ToolsMenuFunctions toolsMenuFunctions = new ToolsMenuFunctions(border,clear, pencil, line, dashedLine, rectangle, square, ellipse, circle, undoOption, colorPicker, settingsMenuFunctions, tabPane, tabArrays, eyedropper);
    }
}