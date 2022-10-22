package com.example.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.ImagingOpException;
import java.nio.Buffer;

import static java.lang.Math.abs;
/**
 * Contains all the functions for drawing shapes, undo/redo, and selecting some of the image
 */
public class ToolsMenuFunctions {
    /**
     *
     * @param border
     * @param clear
     * @param pencil
     * @param drawLine
     * @param drawDashedLine
     * @param drawRectangle
     * @param drawSquare
     * @param drawEllipse
     * @param drawCircle
     * @param drawTriangle
     * @param undo
     * @param redo
     * @param colorPicker
     * @param settingsMenuFunctions
     * @param tabPane
     * @param tabArrays
     * @param eyedropper
     * @param eraser
     * @param drawPolygon
     * @param selectImage
     * @param copyOption
     * @param pasteOption
     * @param cutOption
     */
    ToolsMenuFunctions(MenuItem border, MenuItem clear, ToggleButton pencil, ToggleButton drawLine,ToggleButton drawDashedLine, ToggleButton drawRectangle, ToggleButton drawSquare, ToggleButton drawEllipse, ToggleButton drawCircle, ToggleButton drawTriangle ,MenuItem undo, MenuItem redo,ColorPicker colorPicker, SettingsMenuFunctions settingsMenuFunctions, TabPane tabPane, TabArrays tabArrays, Button eyedropper, ToggleButton eraser, ToggleButton drawPolygon, MenuItem selectImage, MenuItem copyOption, MenuItem pasteOption, MenuItem cutOption, TextField polyInput,Button mirrorX,Button mirrorY, Button ninetyDeg,Button oneeightyDeg,Button twoseventyDeg){
        polyInput.setPrefWidth(30);
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
        canvas[0] = tabArrays.stackCanvas[0].canvas;
        final boolean selectImagePart[] = {false, false};
        final boolean pasteImage[] = {false};
        final boolean cutImage[] = {false};
        final WritableImage selectedImage[] = new WritableImage[1];
        final WritableImage copiedImage[] = new WritableImage[1];
        final WritableImage selectedUndo[] = new WritableImage[1];
        final WritableImage selectedRedo[] = new WritableImage[1];
        final WritableImage pasteUndo[] = new WritableImage[1];
        CheckMenuItem selectionBox = new CheckMenuItem();
        final Rectangle2D boundRectangle[] = new Rectangle2D[1];

        //Set Mouse Events
        //Initialize mouse pressed for first tab
        //Set Mouse Events
        canvas[0].setOnMousePressed((MouseEvent event) -> {
            firstPos[0] = event.getX();
            firstPos[1] = event.getY();
            GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
            if(selectImagePart[0]){
                selectedUndo[0] = canvas[0].snapshot(null, selectedUndo[0]);
                //Start drawing a rectangle
                selectionBox.setSelected(true);
                pencil.setSelected(false);
                drawLine.setSelected(false);
                drawDashedLine.setSelected(false);
                drawRectangle.setSelected(false);
                drawSquare.setSelected(false);
                drawEllipse.setSelected(false);
                drawCircle.setSelected(false);
                eraser.setSelected(false);
                drawPolygon.setSelected(false);
                drawTriangle.setSelected(false);
            } else if(selectImagePart[1]){
                canvasReplace(canvas[0], selectedUndo[0]);
                selectImagePart[1] = false;
            }
            if(pasteImage[0]){
                pasteUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                pasteUndo[0] = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, pasteUndo[0]);
                gc.drawImage(copiedImage[0], abs(event.getX()-(copiedImage[0].getWidth()/2)), abs(event.getY()-(copiedImage[0].getHeight()/2)));
            }
            //Grab Color Code
            if(getColor[0]){
                System.out.println("Color Grabbed");
                //Turn the current canvas into a writable image
                WritableImage pixelImage = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                pixelImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, pixelImage);
                //Set the color picker to the clicked on color
                grabberColor[0] = pixelImage.getPixelReader().getColor((int)event.getX(), (int)event.getY());
                System.out.println(grabberColor[0].toString());
                colorPicker.setValue(grabberColor[0]);
                getColor[0] = false;
            }
            pickerColor[0] = colorPicker.getValue();
            //Set stroke to color pickers value
            gc.setStroke(pickerColor[0]);
            System.out.println("Mouse Pressed");
            //If drawing anything
            if(pencil.isSelected() || drawLine.isSelected() || drawDashedLine.isSelected() || drawRectangle.isSelected() || drawSquare.isSelected() || drawEllipse.isSelected() || drawCircle.isSelected() || eraser.isSelected() || drawPolygon.isSelected() || selectionBox.isSelected() || pasteImage[0] || drawTriangle.isSelected()) {
                //Show a warning if trying to close before saving
                tabArrays.saveWarning[tabPane.getSelectionModel().getSelectedIndex()] = true;
                System.out.println("Changed " + tabPane.getSelectionModel().getSelectedIndex() + " to " + tabArrays.saveWarning[tabPane.getSelectionModel().getSelectedIndex()]);
                if (pencil.isSelected() || eraser.isSelected()) {
                    //Begin making a pencil path if the pencil tool is selected
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke();
                }
                drawWidth[0] = settingsMenuFunctions.strokeSlider.getValue();
                //Update undo canvas
                canvasUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                if (!pasteImage[0]) {
                    tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, canvasUndo[0]);
                    tabArrays.undoArr[selectedTab[0]].push(canvasUndo[0]);
                    tabArrays.redoArr[selectedTab[0]].clear();
                }
                //Record first position
                event.setDragDetect(true);
                //Save current status of canvas
                previewImage[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, previewImage[0]);
                //Set line width to slider value
                if (selectImagePart[0]){
                    gc.setLineWidth(2);
                } else {
                    gc.setLineWidth(drawWidth[0]);
                }
            }
        });

        canvas[0].setOnMouseDragged((MouseEvent event) -> {
            System.out.println(selectionBox.isSelected());
            GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
            //If second position of line is not set draw a preview
            if (pasteImage[0]){
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, pasteUndo[0]);
                gc.drawImage(copiedImage[0], abs(event.getX()-(copiedImage[0].getWidth()/2)), abs(event.getY()-(copiedImage[0].getHeight()/2)));
            } else if (pencil.isSelected() || eraser.isSelected()) {
                if (eraser.isSelected()) {
                    gc.setStroke(Color.WHITE);
                }
                gc.setLineDashes(0);
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
            } else if (drawLine.isSelected()) {
                //make line solid
                gc.setLineDashes(0);
                //Clear canvas of line previews
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                //Draw a line from the first point to current mouse position
                gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
            } else if (drawDashedLine.isSelected()) {
                //Set up dashed line
                gc.setLineDashes(20);
                gc.setLineDashOffset(5);
                //Clear canvas of dashed line previews
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                //Draw a dashed line from the first point to current mouse position
                gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
                //gc.strokeLine(0, 0, 300, 300);
            } else if (drawRectangle.isSelected()) {
                gc.setLineDashes(0);
                //Clear canvas of rectangle previews
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                //Draw a rectangle from first point to current mouse position
                //Each of these if statements deals with a different canvas quadrant
                if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                    gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else {
                    gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(firstPos[0] - event.getX()), abs(firstPos[1] - event.getY()));
                }
            } else if (drawSquare.isSelected()) {
                gc.setLineDashes(0);
                //Clear canvas of square previews
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                //Draw a square from first point to current mouse position
                //Each of these if statements deals with a different canvas quadrant
                if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                    gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                } else {
                    gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                }
            } else if (drawEllipse.isSelected()) {
                gc.setLineDashes(0);
                //Clear canvas of ellipse previews
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                //Draw ellipse
                //Each of these if statements deals with a different canvas quadrant
                if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                    gc.strokeOval(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeOval(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else {
                    gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                }
            } else if (drawCircle.isSelected()) {
                gc.setLineDashes(0);
                //Clear canvas of circle previews
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                //Draw ellipse
                //Each of these if statements deals with a different canvas quadrant
                if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                    gc.strokeOval(firstPos[0], firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeOval(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                } else {
                    gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                }
            } else if (drawPolygon.isSelected()) {
                gc.setLineDashes(0);
                System.out.println("poly");
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                Polygon polygon = new Polygon();
                int[] center = {(int) (firstPos[0]), (int) (firstPos[1])};
                int radius = (int) (event.getX() - firstPos[0]);
                int sides = Integer.parseInt(polyInput.getCharacters().toString());
                setPolygonSides(polygon, center[0], center[1], radius, sides);
                double[] xPoints = new double[sides];
                double[] yPoints = new double[sides];
                //Assign points of the polygon to arrays
                for (int i = 0; i < sides; i++) {
                    xPoints[i] = polygon.getPoints().get(i * 2);
                    yPoints[i] = polygon.getPoints().get(i * 2 + 1);
                }
                gc.strokePolygon(xPoints, yPoints, sides);
            } else if(drawTriangle.isSelected()){
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                Polygon polygon = new Polygon();
                //Declare the center and sides of the polygon
                int[] center = {(int) (firstPos[0]), (int) (firstPos[1])};
                int radius = (int) (event.getX() - firstPos[0]);
                int sides = 3;
                //set points of the polygon based on passed variables
                setPolygonSides(polygon, center[0], center[1], radius, sides);
                double[] xPoints = new double[sides];
                double[] yPoints = new double[sides];
                //Assign points of the triangle to arrays
                for (int i = 0; i < sides; i++) {
                    xPoints[i] = polygon.getPoints().get(i * 2);
                    yPoints[i] = polygon.getPoints().get(i * 2 + 1);
                }
                //Stroke polygon from the arrays
                gc.strokePolygon(xPoints, yPoints, sides);
            } else if (selectionBox.isSelected()) {
                //Draw dashed box
                System.out.println("Sel");
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2);
                gc.setLineDashes(20);
                gc.setLineDashOffset(5);
                //Clear canvas of rectangle previews
                canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                //Draw a rectangle from first point to current mouse position
                //Each of these if statements deals with a different canvas quadrant
                if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                    gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                    gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else {
                    gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(firstPos[0] - event.getX()), abs(firstPos[1] - event.getY()));
                }
            }
        });

        canvas[0].setOnMouseReleased((MouseEvent event) -> {
            System.out.println("mouse released");
            //If selecting image
            if(selectImagePart[0]){
                System.out.println("Selected");
                selectImagePart[0] = false;
                selectImagePart[1] = true;
                //Create and set bounds
                if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                    boundRectangle[0] = new Rectangle2D(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                    boundRectangle[0] = new Rectangle2D(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                    boundRectangle[0] = new Rectangle2D(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                } else {
                    boundRectangle[0] = new Rectangle2D(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(firstPos[0] - event.getX()), abs(firstPos[1] - event.getY()));
                }
                //Create snapshot parameters with the created bound
                SnapshotParameters params = new SnapshotParameters();
                params.setViewport(boundRectangle[0]);
                //Take a snapshot with the bounds
                selectedRedo[0] = canvas[0].snapshot(null, selectedRedo[0]);
                canvasReplace(canvas[0], tabArrays.undoArr[selectedTab[0]].peek());
                selectedImage[0] = canvas[0].snapshot(params, null);
                canvasReplace(canvas[0],selectedRedo[0]);
                //No longer draw selection box
                selectionBox.setSelected(false);
            }
            if(pasteImage[0]){
                pasteImage[0] = false;
            }
        });



        //Reinitialize mouse down and drag when selected tab is changed
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                if(tabPane.getSelectionModel().getSelectedIndex() >= 0) {
                    //Update selected tab whenever it changes
                    selectedTab[0] = tabPane.getSelectionModel().getSelectedIndex();
                    canvas[0] = tabArrays.stackCanvas[selectedTab[0]].canvas;
                }

                //Set Mouse Events
                canvas[0].setOnMousePressed((MouseEvent event) -> {
                    firstPos[0] = event.getX();
                    firstPos[1] = event.getY();
                    GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
                    if(selectImagePart[0]){
                        selectedUndo[0] = canvas[0].snapshot(null, selectedUndo[0]);
                        //Start drawing a rectangle
                        selectionBox.setSelected(true);
                        pencil.setSelected(false);
                        drawLine.setSelected(false);
                        drawDashedLine.setSelected(false);
                        drawRectangle.setSelected(false);
                        drawSquare.setSelected(false);
                        drawEllipse.setSelected(false);
                        drawCircle.setSelected(false);
                        eraser.setSelected(false);
                        drawPolygon.setSelected(false);
                        drawTriangle.setSelected(false);
                    } else if(selectImagePart[1]){
                        canvasReplace(canvas[0], selectedUndo[0]);
                        selectImagePart[1] = false;
                    }
                    if(pasteImage[0]){
                        pasteUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                        pasteUndo[0] = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, pasteUndo[0]);
                        gc.drawImage(copiedImage[0], abs(event.getX()-(copiedImage[0].getWidth()/2)), abs(event.getY()-(copiedImage[0].getHeight()/2)));
                    }
                    //Grab Color Code
                    if(getColor[0]){
                        System.out.println("Color Grabbed");
                        //Turn the current canvas into a writable image
                        WritableImage pixelImage = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                        pixelImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, pixelImage);
                        //Set the color picker to the clicked on color
                        grabberColor[0] = pixelImage.getPixelReader().getColor((int)event.getX(), (int)event.getY());
                        System.out.println(grabberColor[0].toString());
                        colorPicker.setValue(grabberColor[0]);
                        getColor[0] = false;
                    }
                    pickerColor[0] = colorPicker.getValue();
                    //Set stroke to color pickers value
                    gc.setStroke(pickerColor[0]);
                    System.out.println("Mouse Pressed");
                    //If drawing anything
                    if(pencil.isSelected() || drawLine.isSelected() || drawDashedLine.isSelected() || drawRectangle.isSelected() || drawSquare.isSelected() || drawEllipse.isSelected() || drawCircle.isSelected() || eraser.isSelected() || drawPolygon.isSelected() || selectionBox.isSelected() || pasteImage[0] || drawTriangle.isSelected()) {
                        //Show a warning if trying to close before saving
                        tabArrays.saveWarning[tabPane.getSelectionModel().getSelectedIndex()] = true;
                        System.out.println("Changed " + tabPane.getSelectionModel().getSelectedIndex() + " to " + tabArrays.saveWarning[tabPane.getSelectionModel().getSelectedIndex()]);
                        if (pencil.isSelected() || eraser.isSelected()) {
                            //Begin making a pencil path if the pencil tool is selected
                            gc.beginPath();
                            gc.moveTo(event.getX(), event.getY());
                            gc.stroke();
                        }
                        drawWidth[0] = settingsMenuFunctions.strokeSlider.getValue();
                        //Update undo canvas
                        canvasUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                        if (!pasteImage[0]) {
                            tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, canvasUndo[0]);
                            tabArrays.undoArr[selectedTab[0]].push(canvasUndo[0]);
                            tabArrays.redoArr[selectedTab[0]].clear();
                        }
                        //Record first position
                        event.setDragDetect(true);
                        //Save current status of canvas
                        previewImage[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
                        tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, previewImage[0]);
                        //Set line width to slider value
                        if (selectImagePart[0]){
                            gc.setLineWidth(2);
                        } else {
                            gc.setLineWidth(drawWidth[0]);
                        }
                    }
                });

                canvas[0].setOnMouseDragged((MouseEvent event) -> {
                    System.out.println(selectionBox.isSelected());
                    GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
                    //If second position of line is not set draw a preview
                    if (pasteImage[0]){
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, pasteUndo[0]);
                        gc.drawImage(copiedImage[0], abs(event.getX()-(copiedImage[0].getWidth()/2)), abs(event.getY()-(copiedImage[0].getHeight()/2)));
                    } else if (pencil.isSelected() || eraser.isSelected()) {
                        if (eraser.isSelected()) {
                            gc.setStroke(Color.WHITE);
                        }
                        gc.setLineDashes(0);
                        gc.lineTo(event.getX(), event.getY());
                        gc.stroke();
                    } else if (drawLine.isSelected()) {
                        //make line solid
                        gc.setLineDashes(0);
                        //Clear canvas of line previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a line from the first point to current mouse position
                        gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
                    } else if (drawDashedLine.isSelected()) {
                        //Set up dashed line
                        gc.setLineDashes(20);
                        gc.setLineDashOffset(5);
                        //Clear canvas of dashed line previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a dashed line from the first point to current mouse position
                        gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
                        //gc.strokeLine(0, 0, 300, 300);
                    } else if (drawRectangle.isSelected()) {
                        gc.setLineDashes(0);
                        //Clear canvas of rectangle previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a rectangle from first point to current mouse position
                        //Each of these if statements deals with a different canvas quadrant
                        if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                            gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else {
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(firstPos[0] - event.getX()), abs(firstPos[1] - event.getY()));
                        }
                    } else if (drawSquare.isSelected()) {
                        gc.setLineDashes(0);
                        //Clear canvas of square previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a square from first point to current mouse position
                        //Each of these if statements deals with a different canvas quadrant
                        if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                            gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else {
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        }
                    } else if (drawEllipse.isSelected()) {
                        gc.setLineDashes(0);
                        //Clear canvas of ellipse previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw ellipse
                        //Each of these if statements deals with a different canvas quadrant
                        if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                            gc.strokeOval(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeOval(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else {
                            gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        }
                    } else if (drawCircle.isSelected()) {
                        gc.setLineDashes(0);
                        //Clear canvas of circle previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw ellipse
                        //Each of these if statements deals with a different canvas quadrant
                        if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                            gc.strokeOval(firstPos[0], firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeOval(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        } else {
                            gc.strokeOval(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]), abs(event.getX() - firstPos[0]));
                        }
                    } else if (drawPolygon.isSelected()) {
                        gc.setLineDashes(0);
                        System.out.println("poly");
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        Polygon polygon = new Polygon();
                        int[] center = {(int) (firstPos[0]), (int) (firstPos[1])};
                        int radius = (int) (event.getX() - firstPos[0]);
                        int sides = Integer.parseInt(polyInput.getCharacters().toString());
                        setPolygonSides(polygon, center[0], center[1], radius, sides);
                        double[] xPoints = new double[sides];
                        double[] yPoints = new double[sides];
                        //Assign points of the polygon to arrays
                        for (int i = 0; i < sides; i++) {
                            xPoints[i] = polygon.getPoints().get(i * 2);
                            yPoints[i] = polygon.getPoints().get(i * 2 + 1);
                        }
                        gc.strokePolygon(xPoints, yPoints, sides);
                    } else if(drawTriangle.isSelected()){
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        Polygon polygon = new Polygon();
                        //Declare the center and sides of the polygon
                        int[] center = {(int) (firstPos[0]), (int) (firstPos[1])};
                        int radius = (int) (event.getX() - firstPos[0]);
                        int sides = 3;
                        //set points of the polygon based on passed variables
                        setPolygonSides(polygon, center[0], center[1], radius, sides);
                        double[] xPoints = new double[sides];
                        double[] yPoints = new double[sides];
                        //Assign points of the triangle to arrays
                        for (int i = 0; i < sides; i++) {
                            xPoints[i] = polygon.getPoints().get(i * 2);
                            yPoints[i] = polygon.getPoints().get(i * 2 + 1);
                        }
                        //Stroke polygon from the arrays
                        gc.strokePolygon(xPoints, yPoints, sides);
                    } else if (selectionBox.isSelected()) {
                        //Draw dashed box
                        System.out.println("Sel");
                        gc.setStroke(Color.BLACK);
                        gc.setLineWidth(2);
                        gc.setLineDashes(20);
                        gc.setLineDashOffset(5);
                        //Clear canvas of rectangle previews
                        canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, previewImage[0]);
                        //Draw a rectangle from first point to current mouse position
                        //Each of these if statements deals with a different canvas quadrant
                        if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                            gc.strokeRect(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                            gc.strokeRect(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else {
                            gc.strokeRect(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(firstPos[0] - event.getX()), abs(firstPos[1] - event.getY()));
                        }
                    }
                });

                canvas[0].setOnMouseReleased((MouseEvent event) -> {
                    System.out.println("mouse released");
                    //If selecting image
                    if(selectImagePart[0]){
                        System.out.println("Selected");
                        selectImagePart[0] = false;
                        selectImagePart[1] = true;
                        //Create and set bounds
                        if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] < 0) {
                            boundRectangle[0] = new Rectangle2D(firstPos[0], firstPos[1] - abs(event.getY() - firstPos[1]), abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] < 0 && event.getY() - firstPos[1] >= 0) {
                            boundRectangle[0] = new Rectangle2D(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else if (event.getX() - firstPos[0] >= 0 && event.getY() - firstPos[1] >= 0) {
                            boundRectangle[0] = new Rectangle2D(firstPos[0], firstPos[1], abs(event.getX() - firstPos[0]), abs(event.getY() - firstPos[1]));
                        } else {
                            boundRectangle[0] = new Rectangle2D(firstPos[0] - abs(event.getX() - firstPos[0]), firstPos[1] - abs(event.getY() - firstPos[1]), abs(firstPos[0] - event.getX()), abs(firstPos[1] - event.getY()));
                        }
                        //Create snapshot parameters with the created bound
                        SnapshotParameters params = new SnapshotParameters();
                        params.setViewport(boundRectangle[0]);
                        //Take a snapshot with the bounds
                        selectedRedo[0] = canvas[0].snapshot(null, selectedRedo[0]);
                        canvasReplace(canvas[0], tabArrays.undoArr[selectedTab[0]].peek());
                        selectedImage[0] = canvas[0].snapshot(params, null);
                        canvasReplace(canvas[0],selectedRedo[0]);
                        //No longer draw selection box
                        selectionBox.setSelected(false);
                    }
                    if(pasteImage[0]){
                        pasteImage[0] = false;
                    }
                });
            }
        });

        //Border Menu Function
        border.setOnAction((ActionEvent event) -> {
            GraphicsContext gc = tabArrays.stackCanvas[selectedTab[0]].canvas.getGraphicsContext2D();
            pickerColor[0] = colorPicker.getValue();
            canvasUndo[0] = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
            tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, canvasUndo[0]);
            tabArrays.undoArr[selectedTab[0]].push(canvasUndo[0]);
            tabArrays.redoArr[selectedTab[0]].clear();
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
            tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, canvasUndo[0]);
            tabArrays.undoArr[selectedTab[0]].push(canvasUndo[0]);
            tabArrays.redoArr[selectedTab[0]].clear();
            //Clear the canvas
            clearCanvas(tabArrays.stackCanvas[selectedTab[0]].canvas);
        });

        undo.setOnAction(e -> {
            //Canvas to undo to
            WritableImage undoImage = tabArrays.undoArr[selectedTab[0]].pop();
            //Canvas before undo
            WritableImage currCanvas = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
            tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, currCanvas);
            //Undo the last action
            canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, undoImage);
            //Push the canvas before undoing onto redo stack
            tabArrays.redoArr[selectedTab[0]].push(currCanvas);
        });

        redo.setOnAction(e -> {
            //Canvas to redo to
            WritableImage redoImage = tabArrays.redoArr[selectedTab[0]].pop();
            //Canvas before redo
            WritableImage currCanvas = new WritableImage((int) tabArrays.stackCanvas[selectedTab[0]].canvas.getWidth(), (int) tabArrays.stackCanvas[selectedTab[0]].canvas.getHeight());
            tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, currCanvas);
            //Redo the last undo
            canvasReplace(tabArrays.stackCanvas[selectedTab[0]].canvas, redoImage);
            //Push canvas before redoing onto the undo stack
            tabArrays.undoArr[selectedTab[0]].push(currCanvas);
        });

        eyedropper.setOnAction(actionEvent -> {
            getColor[0] = true;
        });

        selectImage.setOnAction(actionEvent -> {
            //toggle select image part bool
            selectImagePart[0] = !selectImagePart[0];
        });

        copyOption.setOnAction(actionEvent -> {
            //Put the selected image into copied image
            copiedImage[0] = selectedImage[0];
            //Undo the dotted box
            canvasReplace(canvas[0], selectedUndo[0]);
            //No longer select images
            selectImagePart[1] = false;
        });

        pasteOption.setOnAction(actionEvent -> {
            System.out.println("paste");
            //Look to paste image on next mouse click
            pasteImage[0] = true;
        });

        cutOption.setOnAction(actionEvent -> {
            //Put the selected image into copied image
            copiedImage[0] = selectedImage[0];
            //Undo the dotted box
            canvasReplace(canvas[0], selectedUndo[0]);
            //Set cut bool to true
            cutImage[0] = true;
            //No longer select images
            selectImagePart[1] = false;
        });

        ninetyDeg.setOnAction(actionEvent -> {
            GraphicsContext gc = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getGraphicsContext2D();
            if(selectImagePart[1]){
                canvasReplace(canvas[0], selectedUndo[0]);
                Image rotImage = selectedImage[0];
                Rectangle2D boundRect = boundRectangle[0];
                int angle = 90;
                BufferedImage bImage = SwingFXUtils.fromFXImage(rotImage, null);
                bImage = rotateImageByDegrees(bImage, angle);
                rotImage = SwingFXUtils.toFXImage(bImage, null);
                double xDraw = boundRect.getMaxX()-rotImage.getWidth();
                double yDraw = boundRect.getMaxY()-rotImage.getHeight();
                gc.drawImage(rotImage, xDraw, yDraw);
                selectImagePart[1]=false;
            } else {
                Image rotImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, null);
                double newH = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getWidth();
                double newW = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getHeight();
                int angle = 90;
                BufferedImage bImage = SwingFXUtils.fromFXImage(rotImage, null);
                bImage = rotateImageByDegrees(bImage, angle);
                rotImage = SwingFXUtils.toFXImage(bImage, null);
                tabArrays.stackCanvas[selectedTab[0]].canvas.setHeight(newH);
                tabArrays.stackCanvas[selectedTab[0]].canvas.setWidth(newW);
                gc.drawImage(rotImage, 0, 0);
            }
        });

        oneeightyDeg.setOnAction(actionEvent -> {
            GraphicsContext gc = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getGraphicsContext2D();
            if(selectImagePart[1]){
                canvasReplace(canvas[0], selectedUndo[0]);
                Image rotImage = selectedImage[0];
                Rectangle2D boundRect = boundRectangle[0];
                int angle = 180;
                BufferedImage bImage = SwingFXUtils.fromFXImage(rotImage, null);
                bImage = rotateImageByDegrees(bImage, angle);
                rotImage = SwingFXUtils.toFXImage(bImage, null);
                double xDraw = boundRect.getMaxX()-rotImage.getWidth();
                double yDraw = boundRect.getMaxY()-rotImage.getHeight();
                gc.drawImage(rotImage, xDraw, yDraw);
                selectImagePart[1]=false;
            }else {
                Image rotImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, null);
                int angle = 180;
                BufferedImage bImage = SwingFXUtils.fromFXImage(rotImage, null);
                bImage = rotateImageByDegrees(bImage, angle);
                rotImage = SwingFXUtils.toFXImage(bImage, null);
                gc.drawImage(rotImage, 0, 0);
            }
        });

        twoseventyDeg.setOnAction(actionEvent -> {
            GraphicsContext gc = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getGraphicsContext2D();
            if(selectImagePart[1]){
                canvasReplace(canvas[0], selectedUndo[0]);
                Image rotImage = selectedImage[0];
                Rectangle2D boundRect = boundRectangle[0];
                int angle = 270;
                BufferedImage bImage = SwingFXUtils.fromFXImage(rotImage, null);
                bImage = rotateImageByDegrees(bImage, angle);
                rotImage = SwingFXUtils.toFXImage(bImage, null);
                double xDraw = boundRect.getMaxX()-rotImage.getWidth();
                double yDraw = boundRect.getMaxY()-rotImage.getHeight();
                gc.drawImage(rotImage, xDraw, yDraw);
                selectImagePart[1]=false;
            } else {
                Image rotImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, null);
                double newH = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getWidth();
                double newW = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getHeight();
                int angle = 270;
                BufferedImage bImage = SwingFXUtils.fromFXImage(rotImage, null);
                bImage = rotateImageByDegrees(bImage, angle);
                rotImage = SwingFXUtils.toFXImage(bImage, null);
                tabArrays.stackCanvas[selectedTab[0]].canvas.setHeight(newH);
                tabArrays.stackCanvas[selectedTab[0]].canvas.setWidth(newW);
                gc.drawImage(rotImage, 0, 0);
            }
        });

        mirrorX.setOnAction(actionEvent -> {
            GraphicsContext gc = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getGraphicsContext2D();
            if(selectImagePart[1]){
                canvasReplace(canvas[0], selectedUndo[0]);
                Image rotImage = selectedImage[0];
                Rectangle2D boundRect = boundRectangle[0];
                double xDraw = boundRect.getMaxX()-rotImage.getWidth();
                double yDraw = boundRect.getMaxY()-rotImage.getHeight();
                gc.drawImage(rotImage, 0, 0, rotImage.getWidth(), rotImage.getHeight(), xDraw, yDraw+rotImage.getHeight(), rotImage.getWidth(), -rotImage.getHeight());
                selectImagePart[1]=false;
            }else {
                tabArrays.undoArr[selectedTab[0]].push(tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null,null));
                Image rotImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, null);
                gc.drawImage(rotImage, 0, 0, rotImage.getWidth(), rotImage.getHeight(), 0, rotImage.getHeight(), rotImage.getWidth(), -rotImage.getHeight());
            }
        });

        mirrorY.setOnAction(actionEvent -> {
            GraphicsContext gc = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getGraphicsContext2D();
            if(selectImagePart[1]){
                canvasReplace(canvas[0], selectedUndo[0]);
                Image rotImage = selectedImage[0];
                Rectangle2D boundRect = boundRectangle[0];
                double xDraw = boundRect.getMaxX()-rotImage.getWidth();
                double yDraw = boundRect.getMaxY()-rotImage.getHeight();
                gc.drawImage(rotImage, 0, 0, rotImage.getWidth(), rotImage.getHeight(), xDraw+rotImage.getWidth(), yDraw, -rotImage.getWidth(), rotImage.getHeight());
                selectImagePart[1]=false;
            }else {
                tabArrays.undoArr[selectedTab[0]].push(tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null,null));
                Image rotImage = tabArrays.stackCanvas[selectedTab[0]].canvas.snapshot(null, null);
                gc.drawImage(rotImage, 0, 0, rotImage.getWidth(), rotImage.getHeight(), rotImage.getWidth(), 0, -rotImage.getWidth(), rotImage.getHeight());
            }
        });

        //When one tool is selected, unselect the other tools
        pencil.setOnAction(actionEvent -> {
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawLine.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawDashedLine.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawRectangle.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawSquare.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawEllipse.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawCircle.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            eraser.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        eraser.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            drawPolygon.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawPolygon.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            selectionBox.setSelected(false);
            drawTriangle.setSelected(false);
        });
        drawTriangle.setOnAction(actionEvent -> {
            pencil.setSelected(false);
            drawLine.setSelected(false);
            drawDashedLine.setSelected(false);
            drawRectangle.setSelected(false);
            drawSquare.setSelected(false);
            drawEllipse.setSelected(false);
            drawCircle.setSelected(false);
            eraser.setSelected(false);
            selectionBox.setSelected(false);
            drawPolygon.setSelected(false);
        });
    }

    /**
     * Converts given canvas to a writable image
     * @param canvas
     * @return
     */
    public static WritableImage canvasToWritableImage(Canvas canvas){
        //Make a new Writable Image of the canvas width and height
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        //Set the writable image equal to a snapshot of the current canvas
        writableImage = canvas.snapshot(null,writableImage);
        //return the writable image
        return writableImage;
    }

    /**
     * Replaces the canvas with the given writable image
     * @param canvas
     * @param image
     */
    public static void canvasReplace(Canvas canvas,WritableImage image){
        canvas.setHeight(image.getHeight());
        canvas.setWidth(image.getWidth());
        canvas.getGraphicsContext2D().drawImage(image,0,0);
    }

    /**
     * Clears the given canvas
     * @param canvas
     */
    public static void clearCanvas(Canvas canvas){
        //get Graphics Context of Canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Set fill color to white
        gc.setFill(Color.WHITE);
        //Make a white filled rectangle that covers the whole canvas
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Generates a polygon with the given sides, radius, and center and stores it in the given polygon object
     * @param polygon
     * @param centerX
     * @param centerY
     * @param radius
     * @param sides
     */
    private static void setPolygonSides(Polygon polygon, double centerX, double centerY, double radius, int sides) {
        polygon.getPoints().clear();
        final double angleStep = Math.PI * 2 / sides;
        double angle = 0; // assumes one point is located directly beneath the center point
        for (int i = 0; i < sides; i++, angle += angleStep) {
            polygon.getPoints().addAll(
                    Math.sin(angle) * radius + centerX, // x coordinate of the corner
                    Math.cos(angle) * radius + centerY // y coordinate of the corner
            );
        }
    }

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, new ImageObserver() {
            @Override
            public boolean imageUpdate(java.awt.Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
        return rotated;
    }
}