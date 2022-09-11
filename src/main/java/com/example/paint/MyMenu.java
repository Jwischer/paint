package com.example.paint;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

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
        Menu settingsmenu = new Menu("Settings");

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
        //Create and add Color Picker option to tools menu
        MenuItem pickColor = new MenuItem("Choose Color");
        toolsmenu.getItems().add(pickColor);
        //Create and add Line option to tools menu
        MenuItem line = new MenuItem("Draw Line");
        toolsmenu.getItems().add(line);
        //Create and add Line option to tools menu
        MenuItem strokeWidth = new MenuItem("Stroke Width");
        settingsmenu.getItems().add(strokeWidth);
        //add menus to menu bar
        menuBar.getMenus().addAll(filemenu);
        menuBar.getMenus().addAll(toolsmenu);
        menuBar.getMenus().addAll(settingsmenu);

        //Instantiate ButtonFunctions using the menu options
        ButtonFunctions buttonFunctions = new ButtonFunctions(openfile, save, saveas, exit, border, clear, pickColor, line, strokeWidth, canvas, stage);
    }
}