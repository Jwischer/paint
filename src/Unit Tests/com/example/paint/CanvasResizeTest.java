package com.example.paint;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasResizeTest {
    Canvas[] canvas = new Canvas[40];
    int xVal;
    int yVal;
    int canvasIndex = 0;

    @Test
    void canvasResizeTest() {
        canvas[0] = new Canvas(300,300);
        for(int i=1; i<Integer.MAX_VALUE; i++) {
            //System.out.println(i);
            xVal = i;
            yVal = i;
            resize();
            assertEquals(canvas[canvasIndex].getHeight(), i);
            assertEquals(canvas[canvasIndex].getWidth(), i);
        }
    }

    void resize(){
        //Get values in text boxes
        int pixelx = xVal;
        int pixely = yVal;
        //Change width and height of canvas to match
        canvas[canvasIndex].setWidth(pixelx);
        canvas[canvasIndex].setHeight(pixely);
    }
}