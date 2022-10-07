package com.example.paint;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SaveTest extends Application {
    Canvas canvas = new Canvas(300,300); //300x300 white canvas
    File file = new File("D:\\test.png");
    WritableImage writableImage = new WritableImage(300,300);

    @Test
    void saveTest(){
        for(int i=1; i<300; i++) {
            writableImage.getPixelWriter().setColor(i, i, Color.CORAL);
            writableImage.getPixelWriter().setColor(i,300-i,Color.DARKRED);
        }
        saveToFile(writableImage, file.getPath());
    }


    public static void saveToFile(WritableImage image, String location) {
        File outputFile = new File(location);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", outputFile);
        } catch (IOException ex) {

        }
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
