package com.example.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.round;

public class ButtonFunctions {
    ButtonFunctions(MenuItem openfile, MenuItem save, MenuItem saveas, MenuItem exit, MenuItem border, MenuItem clear,
                    MenuItem drawLine, MenuItem strokeWidth, MenuItem undo, Button aboutButton , Canvas canvas, Stage stage, ColorPicker colorPicker){
        //Create a slider for stroke width
        Slider strokeSlider = new Slider(0,50,10);
        //Text box for user width input
        TextField sliderTextField = new TextField("10");
        HBox sliderBox = new HBox();
        //Change slider appearance
        strokeSlider.setShowTickMarks(true);
        strokeSlider.setShowTickLabels(true);
        strokeSlider.setSnapToTicks(true);
        strokeSlider.setMinorTickCount(10);
        strokeSlider.setMajorTickUnit(10);
        //Add slider and text box to slider menu
        sliderBox.getChildren().add(strokeSlider);
        sliderBox.getChildren().add(sliderTextField);
        strokeWidth.setGraphic(sliderBox);
        //Create a fileChooser for opening and saving images
        FileChooser fileChooser = new FileChooser();
        //Create a string array to store the path to the opened image
        final String[] path = new String[1];
        //obtain Canvas graphics context for drawing
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Color that the color picker has selected
        final Color[] pickerColor = new Color[1];
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
        //Set color picker default to black
        colorPicker.setValue(Color.BLACK);

        //Open File Menu Function
        openfile.setOnAction(e -> {
            //Set up file chooser to default to all images and only open images
            fileChooser.getExtensionFilters().setAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg")
            );
            fileChooser.setTitle("Open Image File");
            //Create new file at path given by fileChooser
            File file = fileChooser.showOpenDialog(null);
            //Set path variable equal to the opened file
            path[0] = file.getPath();
            if (file != null) {
                //Create new Image of the selected image
                Image image = new Image(file.toURI().toString());
                //Set canvas height and width equal to the image
                canvas.setHeight(image.getHeight());
                canvas.setWidth(image.getWidth());
                //draw the image on the canvas
                gc.drawImage(image, 0, 0);
            }
        });

        //Save Menu Function
        save.setOnAction(e -> {
            //Convert current state of canvas to a writable image
            WritableImage writableImage = canvasToWritableImage(canvas);
            //Save the writable image to the same location as the file that was opened
            saveToFile(writableImage, path[0]);
        });

        //Save As Menu Function
        saveas.setOnAction((ActionEvent event) -> {
            //Save as either jpg or png with png as default
            fileChooser.getExtensionFilters().setAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("Other Format", "*.*")
            );
            //Create file at the path given by fileChooser
            File file = fileChooser.showSaveDialog(null);
            //Get the save location as a string
            String saveLocation = file.toURI().toString();
            String[] saveloc = saveLocation.split(":", 2);
            //Convert current state of canvas to writable image
            WritableImage writableImage = canvasToWritableImage(canvas);
            //save that writable image at location saveLoc[1]
            saveToFile(writableImage, saveloc[1]);
        });

        //Exit Menu Function
        exit.setOnAction(e -> {
            //Close application
            stage.close();
        });

        //Border Menu Function
        border.setOnAction((ActionEvent event) -> {
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


        drawLine.setOnAction(e -> {
            lineDrawing[0] = true;
        });

        canvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("Mouse Pressed");
            //If drawing a line
            if(lineDrawing[0]){
                //Record first position
                firstPos[0] = event.getX();
                firstPos[1] = event.getY();
                //Start looking for mouse release
                lineDrawing[0] = false;
                lineDrawing[1] = true;
                System.out.println("1");
                event.setDragDetect(true);
                canvasUndo[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvasUndo[0] = canvas.snapshot(null, canvasUndo[0]);
                gc.setLineWidth(drawWidth[0]);
            }
        });

        canvas.setOnMouseDragged((MouseEvent event) -> {
            if(!lineDrawing[0] && lineDrawing[1]) {
                gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
                canvasReplace(canvas, canvasUndo[0]);
                gc.strokeLine(firstPos[0], firstPos[1], event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("Mouse Released");
            //if looking for end of line
            if(lineDrawing[1]){
                canvasUndo[0] = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvasUndo[0] = canvas.snapshot(null, canvasUndo[0]);
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

        //Event Handler for color picker
        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                pickerColor[0] = colorPicker.getValue();
                gc.setStroke(pickerColor[0]);
            }
        });

        sliderTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                strokeSlider.setValue(Integer.parseInt(sliderTextField.getCharacters().toString()));
            }
        });

        strokeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                drawWidth[0] = strokeSlider.getValue();
                sliderTextField.setText(String.valueOf(round(strokeSlider.getValue())));
            }
        });

        undo.setOnAction(e -> {
            canvasReplace(canvas, canvasUndo[0]);
        });

        aboutButton.setOnAction(actionEvent  -> {
            System.out.println("About button pressed");
            //Create an instance of aboutMenuB
            AboutMenu aboutMenu = new AboutMenu();
        });

    }
    public static void saveToFile (WritableImage image, String location) {
        File outputFile = new File(location);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", outputFile);
        } catch (IOException ex) {}
    }

    public static WritableImage canvasToWritableImage(Canvas canvas){
        //Make a new Writable Image of the canvas width and height
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        //Set the writable image equal to a snapshot of the current canvas
        writableImage = canvas.snapshot(null,writableImage);
        //return the writable image
        return writableImage;
    }

    public static void clearCanvas(Canvas canvas){
        //get Graphics Context of Canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Set fill color to white
        gc.setFill(Color.WHITE);
        //Make a white filled rectangle that covers the whole canvas
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
    }

    public static void canvasReplace(Canvas canvas,WritableImage image){
        canvas.setHeight(image.getHeight());
        canvas.setWidth(image.getWidth());
        canvas.getGraphicsContext2D().drawImage(image,0,0);
    }
}