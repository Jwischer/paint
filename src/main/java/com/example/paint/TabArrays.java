package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TimerTask;

/**
 * Contains the variables that should be unique to each tab
 */
public class TabArrays {
    Tab[] tab;
    StackCanvas[] stackCanvas;
    String[] path;
    Button[] close;
    boolean[] saveWarning;
    int maxTabs = 40;
    //SaveTimer[] saveTimer;
    SaveTimer saveTimer;
    Stack<WritableImage>[] undoArr = new Stack[maxTabs];
    Stack<WritableImage>[] redoArr = new Stack[maxTabs];
    TabArrays(){
        //Initialize stacks
        this.maxTabs = 40;
        for(int i=0; i<maxTabs;i++) {
            undoArr[i] = new Stack<WritableImage>();
            redoArr[i] = new Stack<WritableImage>();
            System.out.println("undoArr at " + i + " initialized");
        }
        //this.saveTimer = new SaveTimer[maxTabs];
        this.tab = new Tab[maxTabs];
        this.stackCanvas = new StackCanvas[maxTabs];
        this.path = new String[maxTabs];
        this.close = new Button[maxTabs];
        this.saveWarning = new boolean[maxTabs];
        //Initialize the first canvas
        stackCanvas[0] = new StackCanvas();
        stackCanvas[0].canvas = new Canvas();
    }
}
