package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MyMenu {
    Canvas canvas;
    MenuBar menuBar;

    MyMenu(Canvas canvas, MenuBar menuBar) {
        this.canvas = canvas;
        this.menuBar = menuBar;

        //Menu Bar
        Menu filemenu = new Menu("File");
        Menu toolsmenu = new Menu("Tools");

        //Create and add menu items to menu
        MenuItem openfile = new MenuItem("Open File...");
        filemenu.getItems().add(openfile);
        filemenu.getItems().add(new SeparatorMenuItem());
        MenuItem save = new MenuItem("Save");
        filemenu.getItems().add(save);
        MenuItem saveas = new MenuItem("Save As...");
        filemenu.getItems().add(saveas);
        MenuItem exit = new MenuItem("Exit");
        filemenu.getItems().add(exit);

        MenuItem background = new MenuItem("Add Background");
        toolsmenu.getItems().add(background);

        //add menus to menu bar
        menuBar.getMenus().addAll(filemenu);
        menuBar.getMenus().addAll(toolsmenu);

        ButtonFunctions buttonFunctions = new ButtonFunctions(openfile, save, saveas, background, canvas);
    }
}