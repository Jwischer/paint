package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;

public class TabArrays {
    Tab[] tab;
    StackCanvas[] stackCanvas;
    TabArrays(){
        int maxTabs = 20;
        this.tab = new Tab[maxTabs];
        this.stackCanvas = new StackCanvas[maxTabs];

        stackCanvas[0] = new StackCanvas();
        stackCanvas[0].canvas = new Canvas();
    }
}
