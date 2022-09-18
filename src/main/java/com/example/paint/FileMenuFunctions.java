package com.example.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class FileMenuFunctions{
    FileChooser fileChooser;

    FileMenuFunctions(Stage stage, MenuItem openfile, MenuItem save, MenuItem saveas, MenuItem exit, TabPane tabPane, TabArrays tabArrays){
        this.fileChooser = new FileChooser();
        //Create a string array to store the path to the opened image
        final String[] path = new String[1];
        final int nextTab[] = {0};
        final int maxTabs[] = {20};

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
                        tabArrays.tab[nextTab[0]] = new Tab(file.getName());
                        tabArrays.stackCanvas[nextTab[0]] = new StackCanvas();
                        tabArrays.tab[nextTab[0]].setOnCloseRequest(event -> {
                            System.out.println("Closed Tab");
                            for(int i = tabPane.getSelectionModel().getSelectedIndex(); i<maxTabs[0]-1; i++){
                                System.out.println(tabPane.getSelectionModel().getSelectedIndex());
                                tabArrays.stackCanvas[i] = tabArrays.stackCanvas[i+1];
                                tabArrays.tab[i] = tabArrays.tab[i+1];
                            }
                            nextTab[0]--;
                            System.out.println(nextTab[0]);
                        });
                    }
                GraphicsContext gc = tabArrays.stackCanvas[nextTab[0]].canvas.getGraphicsContext2D();
                tabArrays.tab[nextTab[0]].setContent(tabArrays.stackCanvas[nextTab[0]].scrollPane);
                tabPane.getTabs().add(tabArrays.tab[nextTab[0]]);
                //Create new Image of the selected image
                Image image = new Image(file.toURI().toString());
                //Set canvas height and width equal to the image
                tabArrays.stackCanvas[nextTab[0]].canvas.setHeight(image.getHeight());
                tabArrays.stackCanvas[nextTab[0]].canvas.setWidth(image.getWidth());
                System.out.println(tabPane.getSelectionModel().getSelectedIndex());
                //draw the image on the canvas
                gc.drawImage(image, 0, 0);
                nextTab[0]++;
            });

        //Save Menu Function
        save.setOnAction(e -> {
            int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
            //Convert current state of canvas to a writable image
            WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[selectedTab].canvas);
            //Save the writable image to the same location as the file that was opened
            saveToFile(writableImage, path[0]);
        });

        //Save As Menu Function
        saveas.setOnAction((ActionEvent event) -> {
            int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
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
            WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[selectedTab].canvas);
            //save that writable image at location saveLoc[1]
            saveToFile(writableImage, saveloc[1]);
        });

        //Exit Menu Function
        exit.setOnAction(e -> {
            //Close application
            stage.close();
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
}
