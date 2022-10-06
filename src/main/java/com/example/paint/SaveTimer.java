package com.example.paint;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.paint.FileMenuFunctions.canvasToWritableImage;
import static com.example.paint.FileMenuFunctions.saveToFile;

public class SaveTimer {
    boolean timerOn;
    int timeUntilSave;
    int delay;

    SaveTimer(TabPane tabPane, TabArrays tabArrays) {
        this.timerOn = true;
        this.timeUntilSave = 20; //seconds
        this.delay = 20; //seconds
        //Remove timeline stuff when I rewrite the time until save code
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delay), ev -> {}));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.currentTimeProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println((int)(delay-newValue.toSeconds()+1));
        });
        //End of timeline stuff

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                Platform.runLater(()-> {
                    System.out.println("Test");
                    if (timerOn) {
                        if(timeUntilSave==0) {
                            System.out.println("Timeline Test");
                            //Stuff to do when timer expires
                            //Save canvas
                            //Convert current state of canvas to a writable image
                            for(int i = 0; i<40; i++) {
                                if(tabArrays.stackCanvas[i] != null && tabArrays.path[i] != null) {
                                    WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[i].canvas);
                                    saveToFile(writableImage, tabArrays.path[i]);
                                }
                            }
                            System.out.println("Hi");
                            timeUntilSave = delay;
                        } else{timeUntilSave--;}
                    }
                });
            }
        }, 1000,1000); //Call TimerTask every second
    }
}
