package com.example.paint;

import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasResizeTest {
    Canvas[] canvas = new Canvas[40];
    int xVal;
    int yVal;
    int canvasIndex = 0;

    @Test
    void canvasResizeTest() {
        //Initialize canvas[0]
        canvas[0] = new Canvas(300,300);
        //Cycle through all possible integers and check that resize sizes them properly
        for(int i=1; i<Integer.MAX_VALUE; i++) {
            xVal = i;
            yVal = i;
            resize();
            assertEquals(canvas[canvasIndex].getHeight(), i);
            assertEquals(canvas[canvasIndex].getWidth(), i);
        }
    }

    void resize(){
        int pixelx = xVal;
        int pixely = yVal;
        //Change width and height of canvas to match
        canvas[canvasIndex].setWidth(pixelx);
        canvas[canvasIndex].setHeight(pixely);
    }
}