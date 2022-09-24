package com.example.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import static java.lang.Math.abs;

public class ToolsMenuFunctions {
    ToolsMenuFunctions(MenuItem border, MenuItem clear, CheckMenuItem pencil, CheckMenuItem drawLine,CheckMenuItem drawDashedLine, CheckMenuItem drawRectangle, CheckMenuItem drawSquare, CheckMenuItem drawEllipse, CheckMenuItem drawCircle, MenuItem undo, ColorPicker colorPicker, SettingsMenuFunctions settingsMenuFunctions, TabPane tabPane, TabArrays tabArrays, Button eyedropper){
        //Stores the positions for drawing a line
        final double[] firstPos = {0,0};
        //Width of drawn lines
        final double[] drawWidth = {10};
        //Stores snapshots of canvas for undoing
        final WritableImage[] canvasUndo = new WritableImage[1];
        //Image used for drawing previews
        final WritableImage[] previewImage = new WritableImage[1];
        //Color that the color picker has selected
        final Color[] pickerColor = new Color[1];
        final int selectedTab[] = {0};
        final Canvas[] canvas = {new Canvas()};
        final boolean getColor[] = {false};
        final Color[] grabberColor = new Color[1];

        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                if(tabPane.getSelectionModel().getSelectedIndex() >= 0) {
                    selectedTab[0] = tabPane.getSelectionModel().getSelectedIndex();
                    canvas[0] = tabArrays.stackCanvas[selectedTab[0]].canvas;
                }

                //Set Mouse Events
                canvas[0].setOnMousePressed((MouseEvent event) -> {
                    if(getColor[0]){
                        System.out.println("Color Grabbed");
                        WritableImage pixelImage = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                        pixelImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, pixelImage);
                        grabberColor[0] = pixelImage.getPixelReader().getColor((int)event.getX(), (int)event.getY());
                        System.out.println(grabberColor[0].toString());
                        colorPicker.setValue(grabberColor[0]);
                        getColor[0] = false;
                    }
                    GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
                    pickerColor[0] = colorPicker.getValue();
                    gc.setStroke(pickerColor[0]);
                    System.out.println("Mouse Pressed");
                    //If drawing a line
                    if(pencil.isSelected() || drawLine.isSelected() || drawRectangle.isSelected() || drawSquare.isSelected() || drawEllipse.isSelected() || drawCircle.isSelected()) {
                        tabArrays.saveWarning[tabPane.getSelectionModel().getSelectedIndex()] = true;
                        System.out.println("Changed " + tabPane.getSelectionModel().getSelectedIndex() + " to " + tabArrays.saveWarning[tabPane.getSelectionModel().getSelectedIndex()]);
                        if(pencil.isSelected()){
                            gc.beginPath();
                            gc.moveTo(event.getX(), event.getY());
                            gc.stroke();
                        }
                        drawWidth[0] = settingsMenuFunctions.strokeSlider.getValue();
                        pickerColor[0] = colorPicker.getValue();
                        canvasUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                        //Record first position
                        firstPos[0] = event.getX();
                        firstPos[1] = event.getY();
                        event.setDragDetect(true);
                        //Save current status of canvas
                        previewImage[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                        previewImage[0] = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, canvasUndo[0]);
                        //Set line width to slider value
                        gc.setLineWidth(drawWidth[0]);
                    }
                });

                canvas[0].setOnMouseDragged((MouseEvent event) -> {
                    GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
                    //If second position of line is not set draw a preview
                    if(pencil.isSelected()){
                        gc.lineTo(event.getX(), event.getY());
                        gc.stroke();
                    }
                    else if(drawLine.isSelected()) {
                        gc.setLineDashes(0);
                        //Clear canvas of line previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a line from the first point to current mouse position
                        gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
                    } else if(drawDashedLine.isSelected()){
                        gc.setLineDashes(20);
                        gc.setLineDashOffset(5);
                        //Clear canvas of line previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a line from the first point to current mouse position
                        gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
                        //gc.strokeLine(0, 0, 300, 300);
                    } else if (drawRectangle.isSelected()){
                        //Clear canvas of rectangle previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a rectangle from first point to current mouse position
                        if(event.getX()-firstPos[0] >= 0 && event.getY()-firstPos[1] < 0){
                            gc.strokeRect(firstPos[0], firstPos[1]-abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX()-firstPos[0] < 0 && event.getY()-firstPos[1] >= 0){
                            gc.strokeRect(firstPos[0]-abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if(event.getX()-firstPos[0]>=0 && event.getY()-firstPos[1]>=0) {
                            gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else{
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(firstPos[0]-event.getX()), abs(firstPos[1]-event.getY()));
                        }
                    } else if (drawSquare.isSelected()){
                        //Clear canvas of square previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a square from first point to current mouse position
                        if(event.getX()-firstPos[0] >= 0 && event.getY()-firstPos[1] < 0){
                            gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else if (event.getX()-firstPos[0] < 0 && event.getY()-firstPos[1] >= 0){
                            gc.strokeRect(firstPos[0]-abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else if(event.getX()-firstPos[0]>=0 && event.getY()-firstPos[1]>=0) {
                            gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else{
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        }
                    } else if (drawEllipse.isSelected()){
                        //Clear canvas of ellipse previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw ellipse
                        if(event.getX()-firstPos[0] >= 0 && event.getY()-firstPos[1] < 0){
                            gc.strokeOval(firstPos[0],firstPos[1]-abs(event.getY()-firstPos[1]),abs(event.getX()-firstPos[0]),abs(event.getY()-firstPos[1]));
                            System.out.println("1"); //yshift
                        } else if (event.getX()-firstPos[0] < 0 && event.getY()-firstPos[1] >= 0){
                            gc.strokeOval(firstPos[0]-abs(event.getX()-firstPos[0]),firstPos[1],abs(event.getX()-firstPos[0]),abs(event.getY()-firstPos[1]));
                            System.out.println("2"); //xshift
                        } else if(event.getX()-firstPos[0]>=0 && event.getY()-firstPos[1]>=0) {
                            gc.strokeOval(firstPos[0],firstPos[1],abs(event.getX()-firstPos[0]),abs(event.getY()-firstPos[1]));
                            System.out.println("3");
                        } else{
                            gc.strokeOval(firstPos[0]-abs(event.getX()-firstPos[0]),firstPos[1]-abs(event.getY()-firstPos[1]),abs(event.getX()-firstPos[0]),abs(event.getY()-firstPos[1]));
                            System.out.println("4"); //double shift
                        }
                    } else if (drawCircle.isSelected()){
                        //Clear canvas of circle previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw ellipse
                        if(event.getX()-firstPos[0] >= 0 && event.getY()-firstPos[1] < 0){
                            gc.strokeOval(firstPos[0],firstPos[1]-abs(event.getX()-firstPos[0]),abs(event.getX()-firstPos[0]),abs(event.getX()-firstPos[0]));
                            System.out.println("1"); //yshift
                        } else if (event.getX()-firstPos[0] < 0 && event.getY()-firstPos[1] >= 0){
                            gc.strokeOval(firstPos[0]-abs(event.getX()-firstPos[0]),firstPos[1],abs(event.getX()-firstPos[0]),abs(event.getX()-firstPos[0]));
                            System.out.println("2"); //xshift
                        } else if(event.getX()-firstPos[0]>=0 && event.getY()-firstPos[1]>=0) {
                            gc.strokeOval(firstPos[0],firstPos[1],abs(event.getX()-firstPos[0]),abs(event.getX()-firstPos[0]));
                            System.out.println("3");
                        } else{
                            gc.strokeOval(firstPos[0]-abs(event.getX()-firstPos[0]),firstPos[1]-abs(event.getX()-firstPos[0]),abs(event.getX()-firstPos[0]),abs(event.getX()-firstPos[0]));
                            System.out.println("4"); //double shift
                        }
                    }
                });
                System.out.println("changed to tab " + selectedTab[0]);
                System.out.println(canvas[0].toString());
            }
        });

        //Border Menu Function
        border.setOnAction((ActionEvent event) -> {
            GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
            pickerColor[0] = colorPicker.getValue();
            canvasUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
            canvasUndo[0] = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, canvasUndo[0]);
            //Make a writable image of current state of canvas
            WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[selectedTab[0]].canvas);
            //Make canvas bigger to make room for border
            tabArrays.stackCanvas[selectedTab[0]].canvas.setHeight(tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight()+50);
            tabArrays.stackCanvas[selectedTab[0]].canvas.setWidth(tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth()+50);
            //Set stroke color and line width
            gc.setStroke(pickerColor[0]);
            gc.setLineWidth(50);
            //Draw the rectangle around the edge of the canvas
            gc.strokeRect(0, 0, tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
            //draw the original canvas image at the new center of the canvas
            gc.drawImage(writableImage,25,25);
        });

        //Clear Canvas Menu Function
        clear.setOnAction(e -> {
            canvasUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
            canvasUndo[0] = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, canvasUndo[0]);
            //Clear the canvas
            clearCanvas(tabArrays.stackCanvas[selectedTab[0]].canvas);
        });

        undo.setOnAction(e -> {
            canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, canvasUndo[0]);
        });

        eyedropper.setOnAction(actionEvent -> {
            getColor[0] = true;
        });

        //When one tool is selected, unselect the other tools
        pencil.setOnAction(actionEvent -> {
            drawLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
        });
        drawLine.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
        });
        drawRectangle.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
        });
        drawSquare.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
        });
        drawEllipse.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawCircle.setSelected(false);
        });
        drawCircle.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
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
