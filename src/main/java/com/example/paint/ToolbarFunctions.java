package com.example.paint;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * Contains the functions that are used in the bottom toolbar
 */
public class ToolbarFunctions {
    ColorPicker colorPicker;

    /**
     *
     * @param aboutButton
     * @param colorPicker
     * @param tabArrays
     * @param resize
     * @param tabPane
     */
    ToolbarFunctions(Button aboutButton, ColorPicker colorPicker, TabArrays tabArrays, Button resize, TabPane tabPane){
        this.colorPicker = colorPicker; colorPicker.setId("ColorPicker");
        //Color that the color picker has selected
        final Color[] pickerColor = new Color[1];
        //Set color picker default to black
        colorPicker.setValue(Color.BLACK);
        //Event Handler for color picker
        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {}
        });

        aboutButton.setOnAction(actionEvent  -> {
            //Create an instance of aboutMenu
            AboutMenu aboutMenu =  new AboutMenu();
        });

        resize.setOnAction(actionEvent -> {
            //Open window with text boxes for input of x and y
            ResizeWindow resizeWindow = new ResizeWindow(tabPane, tabArrays);
        });
    }
}
