package com.example.paint;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SaveTest {
    File file = new File("/export/home/jwischer/Desktop/test.png");
    WritableImage writableImage = new WritableImage(300,300);

    @Test
    void saveTest(){
        //Draw two diagonal lines on the Writable Image
        for(int i=1; i<300; i++) {
            writableImage.getPixelWriter().setColor(i, i, Color.CORAL);
            writableImage.getPixelWriter().setColor(i,300-i,Color.DARKRED);
        }
        //Save it to the given path
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
}
