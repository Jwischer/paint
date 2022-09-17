package com.example.paint;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ToolsMenuFunctions {
    ColorPicker colorPicker;
    ToolsMenuFunctions(Canvas canvas,  MenuItem border, MenuItem clear, MenuItem drawLine, MenuItem undo, ColorPicker colorPicker, SettingsMenuFunctions settingsMenuFunctions){
        //lineDrawing[0] - if line drawing has begun
        //lineDrawing[1] - if firstPos has been set
        final boolean[] lineDrawing = {false, false};
        //Stores the positions for drawing a line
        final double[] firstPos = {0,0};
        final double[] lastPos = {0,0};
        //Width of drawn lines
        final double[] drawWidth = {0};
        //Stores snapshots of canvas for undoing
        final WritableImage[] canvasUndo = new WritableImage[1];
        //Image used for line preview
        final WritableImage[] linePreviewImage = new WritableImage[1];
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Color that the color picker has selected
        final Color[] pickerColor = new Color[1];
        //Default Line Width to 10
        gc.setLineWidth(10);

        //Border Menu Function
        border.setOnAction((ActionEvent event) -> {
            pickerColor[0] = colorPicker.getValue();
            canvasUndo[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvasUndo[0] = canvas.snapshot(null, canvasUndo[0]);
            //Make a writable image of current state of canvas
            WritableImage writableImage = canvasToWritableImage(canvas);
            //Make canvas bigger to make room for border
            canvas.setHeight(canvas.getHeight()+50);
            canvas.setWidth(canvas.getWidth()+50);
            //Set stroke color and line width
            gc.setStroke(pickerColor[0]);
            gc.setLineWidth(50);
            //Draw the rectangle around the edge of the canvas
            gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
            //draw the original canvas image at the new center of the canvas
            gc.drawImage(writableImage,25,25);
        });

        //Clear Canvas Menu Function
        clear.setOnAction(e -> {
            canvasUndo[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvasUndo[0] = canvas.snapshot(null, canvasUndo[0]);
            //Clear the canvas
            clearCanvas(canvas);
        });

        //When draw line is clicked
        drawLine.setOnAction(e -> {
            pickerColor[0] = colorPicker.getValue();
            drawWidth[0] = settingsMenuFunctions.strokeSlider.getValue();
            //Start drawing when mouse is clicked
            lineDrawing[0] = true;
        });

        canvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("Mouse Pressed");
            //If drawing a line
            if(lineDrawing[0]){
                canvasUndo[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                //Record first position
                firstPos[0] = event.getX();
                firstPos[1] = event.getY();
                //Start looking for mouse release
                lineDrawing[0] = false;
                lineDrawing[1] = true;
                event.setDragDetect(true);
                //Save current status of canvas
                linePreviewImage[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                linePreviewImage[0] = canvas.snapshot(null, canvasUndo[0]);
                //Set line width to slider value
                gc.setLineWidth(drawWidth[0]);
            }
        });

        canvas.setOnMouseDragged((MouseEvent event) -> {
            //If second position of line is not set draw a preview
            if(!lineDrawing[0] && lineDrawing[1]) {
                //Clear canvas of line previews
                canvasReplace(canvas, linePreviewImage[0]);
                //Draw a line from the first point to current mouse position
                gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("Mouse Released");
            //if looking for end of line
            if(lineDrawing[1]){
                //Record second position
                lastPos[0] = event.getX();
                lastPos[1] = event.getY();
                //Stop looking for mouse events
                lineDrawing[1] = false;
                System.out.println("2");
                gc.strokeLine(firstPos[0], firstPos[1], lastPos[0], lastPos[1]);
                event.setDragDetect(false);
            }
        });

        undo.setOnAction(e -> {
            canvasReplace(canvas, canvasUndo[0]);
        });


    }

    public static WritableImage canvasToWritableImage(Canvas canvas){
        //Make a new Writable Image of the canvas width and height
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        //Set the writable image equal to a snapshot of the current canvas
        writableImage = canvas.snapshot(null,writableImage);
        //return the writable image
        return writableImage;
    }

    public static void canvasReplace(Canvas canvas,WritableImage image){
        canvas.setHeight(image.getHeight());
        canvas.setWidth(image.getWidth());
        canvas.getGraphicsContext2D().drawImage(image,0,0);
    }

    public static void clearCanvas(Canvas canvas){
        //get Graphics Context of Canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Set fill color to white
        gc.setFill(Color.WHITE);
        //Make a white filled rectangle that covers the whole canvas
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
    }
}
