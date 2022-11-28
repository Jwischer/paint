package com.example.paint;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Used to store images for buttons
 */
public class ImageLoader {
    ImageView aboutView;
    ImageView pencilView;
    ImageView lineView;
    ImageView dashedView;
    ImageView rectangleView;
    ImageView squareView;
    ImageView ellipseView;
    ImageView circleView;
    ImageView polyView;
    ImageView eraserView;
    ImageView triangleView;
    ImageLoader(){
        //Load images for use on buttons
        this.aboutView = new ImageView(new Image(this.getClass().getResourceAsStream("/AboutImage.jpg")));
        this.pencilView = new ImageView(new Image(this.getClass().getResourceAsStream("/PencilImage.png")));
        this.lineView = new ImageView(new Image(this.getClass().getResourceAsStream("/LineImage.png")));
        this.dashedView = new ImageView(new Image(this.getClass().getResourceAsStream("/DashedLineImage.png")));
        this.rectangleView = new ImageView(new Image(this.getClass().getResourceAsStream("/RectangleImage.png")));
        this.squareView = new ImageView(new Image(this.getClass().getResourceAsStream("/SquareImage.png")));
        this.ellipseView = new ImageView(new Image(this.getClass().getResourceAsStream("/EllipseImage.png")));
        this.circleView = new ImageView(new Image(this.getClass().getResourceAsStream("/CircleImage.png")));
        this.polyView = new ImageView(new Image(this.getClass().getResourceAsStream("/NSidedImage.png")));
        this.eraserView = new ImageView(new Image(this.getClass().getResourceAsStream("/EraserImage.png")));
        this.triangleView = new ImageView(new Image(this.getClass().getResourceAsStream("/TriangleImage.png")));
    }
}
