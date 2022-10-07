package com.example.paint;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.paint.FileMenuFunctions.canvasToWritableImage;
import static com.example.paint.FileMenuFunctions.saveToFile;

/**
 * Contains a timer that controls the autosave feature
 */
public class SaveTimer {
    boolean timerOn;
    int timeUntilSave;
    int delay;

    /**
     *
     * @param tabArrays
     * @param autosaveTimer
     */
    SaveTimer(TabArrays tabArrays, Label autosaveTimer) {
        this.timerOn = true;
        this.timeUntilSave = 300; //seconds between auto saves
        this.delay = timeUntilSave; //seconds until next autosave

        Timer timer = new Timer();
        //Set timer to run once every second
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                Platform.runLater(()-> {
                    //If timer is enabled
                    if (timerOn) {
                        //If it is time to save
                        if(timeUntilSave==0) {
                            //When timer expires:
                            //Save canvas
                            //Convert current state of canvas to a writable image
                            for(int i = 0; i<40; i++) {
                                if(tabArrays.stackCanvas[i] != null && tabArrays.path[i] != null) {
                                    WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[i].canvas);
                                    saveToFile(writableImage, tabArrays.path[i], false);
                                }
                            }
                            //Reset time until next save
                            timeUntilSave = delay;
                        } else{
                            //Subtract one second from time untl save
                            timeUntilSave--;
                        }
                        System.out.println(timeUntilSave);
                        autosaveTimer.setText("Autosave in " + (timeUntilSave) + " Seconds");
                    }
                });
            }
        }, 1000,1000); //Call TimerTask every second
    }
}
