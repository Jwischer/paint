package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class TabArrays {
    Tab[] tab;
    StackCanvas[] stackCanvas;
    String[] path;
    Button[] close;
    int maxTabs;
    TabArrays(){
        this.maxTabs = 40;
        this.tab = new Tab[maxTabs];
        this.stackCanvas = new StackCanvas[maxTabs];
        this.path = new String[maxTabs];
        this.close = new Button[maxTabs];

        stackCanvas[0] = new StackCanvas();
        stackCanvas[0].canvas = new Canvas();
    }
}
