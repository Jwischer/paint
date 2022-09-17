package com.example.paint;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class ToolbarFunctions {
    ColorPicker colorPicker;
    ToolbarFunctions(Canvas canvas, Button aboutButton, ColorPicker colorPicker){
        this.colorPicker = colorPicker;
        //Color that the color picker has selected
        final Color[] pickerColor = new Color[1];
        //Set color picker default to black
        colorPicker.setValue(Color.BLACK);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Event Handler for color picker
        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                pickerColor[0] = colorPicker.getValue();
                gc.setStroke(pickerColor[0]);
            }
        });

        aboutButton.setOnAction(actionEvent  -> {
            //Create an instance of aboutMenu
            AboutMenu aboutMenu =  new AboutMenu();
        });
    }
}
