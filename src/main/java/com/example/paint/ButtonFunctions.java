package com.example.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ButtonFunctions {
    ButtonFunctions(MenuItem openfile, MenuItem save, MenuItem saveas, MenuItem background, Canvas canvas){
        FileChooser fileChooser = new FileChooser();
        ImageView myImageView = new ImageView();
        final String[] path = new String[1];
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Set up file chooser to only import images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        //Menu Functions
        openfile.setOnAction(e -> {
            fileChooser.setTitle("Open Image File");
            File file = fileChooser.showOpenDialog(null);
            path[0] = file.getPath();
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                canvas.setHeight(image.getHeight());
                canvas.setWidth(image.getWidth());
                gc.drawImage(image, 0, 0);
                myImageView.setImage(image);
            }
        });

        save.setOnAction(e -> {
            System.out.println(path[0]);
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            writableImage = canvas.snapshot(null,writableImage);
            saveToFileCanvas(writableImage, path[0]);
        });

        saveas.setOnAction((ActionEvent event) -> {

            File file = fileChooser.showSaveDialog(null);
            String saveLocation = file.toURI().toString();
            String[] saveloc = saveLocation.split(":", 2);
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            writableImage = canvas.snapshot(null,writableImage);
            saveToFileCanvas(writableImage, saveloc[1]);
        });

        background.setOnAction((ActionEvent event) -> {
            gc.setStroke(Color.DARKRED);
            gc.setFill(Color.BLUE);
            gc.setLineWidth(10);
            gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
            gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(myImageView.getImage(),0,0);
        });
    }
    public static void saveToFileCanvas (WritableImage image, String location) {
        File outputFile = new File(location);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", outputFile);
        } catch (IOException ex) {
            //Logger.getLogger(JavaFX_DrawOnCanvas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}