package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StackCanvas {
    ScrollPane scrollPane;
    StackPane stackPane;
    Canvas canvas;
    AnchorPane anchorPane;
    StackCanvas(){
        this.anchorPane = new AnchorPane();
        this.scrollPane = new ScrollPane();
        this.stackPane = new StackPane();
        this.canvas = new Canvas();
        this.scrollPane.setContent(this.anchorPane);
        this.anchorPane.getChildren().add(stackPane);
        this.stackPane.getChildren().add(this.canvas);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
    }
}
