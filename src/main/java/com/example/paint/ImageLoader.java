package com.example.paint;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Used to store images for buttons
 */
public class ImageLoader {
    Image aboutImage;
    ImageView aboutView;
    ImageLoader(){
        //Image for About Button
        this.aboutImage = new Image("file:src/main/resources/AboutImage.jpg");
        this.aboutView = new ImageView(aboutImage);
    }
}
