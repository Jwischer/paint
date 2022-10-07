package com.example.paint;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
/**
 * Contains all the class instances to make the two top menus in JPaint
 */
public class MyMenu{
    MenuBar menuBar;
    VBox vbox;
    ToolBar toolbar;

    MyMenu(Stage stage, TabPane tabPane, BorderPane borderPane) {
        menuBar = new MenuBar();
        vbox = new VBox();
        toolbar = new ToolBar();
        menuBar.setId("MenuBar");
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
        //Create and add pencil option to tools menu
        CheckMenuItem pencil = new CheckMenuItem("Pencil");
        drawingSubMenu.getItems().add(pencil); pencil.setId("Pencil");
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
        //Create and add polygon option to tools menu
        CheckMenuItem polygon = new CheckMenuItem("N-Sided Polygon");
        drawingSubMenu.getItems().add(polygon);
        //Create and add eraser option to tools menu
        CheckMenuItem eraser = new CheckMenuItem("Eraser");
        drawingSubMenu.getItems().add(eraser);
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
        CheckMenuItem triangle = new CheckMenuItem("Triangle");
        drawingSubMenu.getItems().add(triangle);
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
        toolbar.getItems().add(autosaveLabel);

        //Instantiate ButtonFunctions using the menu options
        KeyboardShortcuts keyboardShortcuts = new KeyboardShortcuts(pencil, line, dashedLine, square, rectangle, circle, ellipse, triangle, undoOption, redoOption, eraser, polygon, copyOption, pasteOption, selectImage,cutOption);
        FileMenuFunctions fileMenuFunctions = new FileMenuFunctions(stage, openFileNT, openFileST, save, saveas, exit, tabPane, tabArrays, autosaveLabel, autosaveTimer);
        SettingsMenuFunctions settingsMenuFunctions = new SettingsMenuFunctions(strokeWidth);
        ToolbarFunctions toolbarFunctions = new ToolbarFunctions(aboutButton, colorPicker, tabArrays, resize, tabPane);
        ToolsMenuFunctions toolsMenuFunctions = new ToolsMenuFunctions(border,clear, pencil, line, dashedLine, rectangle, square, ellipse, circle, triangle, undoOption, redoOption, colorPicker, settingsMenuFunctions, tabPane, tabArrays, eyedropper, eraser, polygon, selectImage, copyOption, pasteOption, cutOption);
    }
}