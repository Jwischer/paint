package com.example.paint;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import static java.lang.Math.abs;

public class ToolsMenuFunctions {
    ToolsMenuFunctions(Canvas canvas,  MenuItem border, MenuItem clear,CheckMenuItem pencil, CheckMenuItem drawLine, CheckMenuItem drawRectangle,CheckMenuItem drawSquare,CheckMenuItem drawEllipse,CheckMenuItem drawCircle, MenuItem undo, ColorPicker colorPicker, SettingsMenuFunctions settingsMenuFunctions){
        //Stores the positions for drawing a line
        final double[] firstPos = {0,0};
        final double[] lastPos = {0,0};
        //Width of drawn lines
        final double[] drawWidth = {0};
        //Stores snapshots of canvas for undoing
        final WritableImage[] canvasUndo = new WritableImage[1];
        //Image used for drawing previews
        final WritableImage[] previewImage = new WritableImage[1];
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

        canvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("Mouse Pressed");
            //If drawing a line
            if(pencil.isSelected() || drawLine.isSelected() || drawRectangle.isSelected() || drawSquare.isSelected() || drawEllipse.isSelected() || drawCircle.isSelected())
                drawWidth[0] = settingsMenuFunctions.strokeSlider.getValue();
                pickerColor[0] = colorPicker.getValue();
                canvasUndo[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                //Record first position
                firstPos[0] = event.getX();
                firstPos[1] = event.getY();
                event.setDragDetect(true);
                //Save current status of canvas
                previewImage[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                previewImage[0] = canvas.snapshot(null, canvasUndo[0]);
                //Set line width to slider value
                gc.setLineWidth(drawWidth[0]);
        });

        canvas.setOnMouseDragged((MouseEvent event) -> {
            //If second position of line is not set draw a preview
            if(drawLine.isSelected()) {
                //Clear canvas of line previews
                canvasReplace(canvas, previewImage[0]);
                //Draw a line from the first point to current mouse position
                gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
            } else if (drawRectangle.isSelected()){
                //Clear canvas of rectangle previews
                canvasReplace(canvas, previewImage[0]);
                //Draw a rectangle from first point to current mouse position
                if(event.getX()-firstPos[0] >= 0 && event.getY()-firstPos[1] < 0){
                    gc.strokeRect(firstPos[0], firstPos[1]-abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX()-firstPos[0] < 0 && event.getY()-firstPos[1] >= 0){
                    gc.strokeRect(firstPos[0]-abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if(event.getX()-firstPos[0]>=0 && event.getY()-firstPos[1]>=0) {
                    gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else{
                    gc.strokeRect(event.getX(), event.getY(), abs(firstPos[0]-event.getX()), abs(firstPos[1]-event.getY()));
                }
            } else if (drawSquare.isSelected()){
                //Clear canvas of square previews
                canvasReplace(canvas, previewImage[0]);
                //Draw a square from first point to current mouse position
                if(event.getX()-firstPos[0] >= 0 && event.getY()-firstPos[1] < 0){
                    gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                    System.out.println("1");
                } else if (event.getX()-firstPos[0] < 0 && event.getY()-firstPos[1] >= 0){
                    gc.strokeRect(firstPos[0]-abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                    System.out.println("2");
                } else if(event.getX()-firstPos[0]>=0 && event.getY()-firstPos[1]>=0) {
                    gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                    System.out.println("3");
                } else{
                    gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                    System.out.println("4");
                }
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
