package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MyMenu {
    Canvas canvas;
    MenuBar menuBar;

    MyMenu(Canvas canvas, MenuBar menuBar, Stage stage) {
        //Set variables equal to pass through variables
        this.canvas = canvas;
        this.menuBar = menuBar;

        //Menu Bar
        Menu filemenu = new Menu("File");
        Menu toolsmenu = new Menu("Tools");

        //Create and add Open File option to file menu
        MenuItem openfile = new MenuItem("Open File...");
        filemenu.getItems().add(openfile);
        //Create and add Save option to file menu
        MenuItem save = new MenuItem("Save");
        filemenu.getItems().add(save);
        //Create and add Save As option to file menu
        MenuItem saveas = new MenuItem("Save As...");
        filemenu.getItems().add(saveas);
        //Create and add Exit option to file menu
        MenuItem exit = new MenuItem("Exit");
        filemenu.getItems().add(exit);
        //Create and add Add Border option to tools menu
        MenuItem border = new MenuItem("Add Border");
        toolsmenu.getItems().add(border);
        //Create and add Clear Canvas option to tools menu
        MenuItem clear = new MenuItem("Clear Canvas");
        toolsmenu.getItems().add(clear);
        //add menus to menu bar
        menuBar.getMenus().addAll(filemenu);
        menuBar.getMenus().addAll(toolsmenu);

        //Instantiate ButtonFunctions using the menu options
        ButtonFunctions buttonFunctions = new ButtonFunctions(openfile, save, saveas, exit, border, clear, canvas, stage);
    }
}