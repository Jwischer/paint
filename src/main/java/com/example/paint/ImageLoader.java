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
        //Image for About Button
        this.aboutView = new ImageView(new Image("file:src/main/resources/AboutImage.jpg"));
        this.pencilView = new ImageView(new Image("file:src/main/resources/PencilImage.png"));
        this.lineView = new ImageView(new Image("file:src/main/resources/LineImage.png"));
        this.dashedView = new ImageView(new Image("file:src/main/resources/DashedLineImage.png"));
        this.rectangleView = new ImageView(new Image("file:src/main/resources/RectangleImage.png"));
        this.squareView = new ImageView(new Image("file:src/main/resources/SquareImage.png"));
        this.ellipseView = new ImageView(new Image("file:src/main/resources/EllipseImage.png"));
        this.circleView = new ImageView(new Image("file:src/main/resources/CircleImage.png"));
        this.polyView = new ImageView(new Image("file:src/main/resources/NSidedImage.png"));
        this.eraserView = new ImageView(new Image("file:src/main/resources/EraserImage.png"));
        this.triangleView = new ImageView(new Image("file:src/main/resources/TriangleImage.png"));
    }
}
