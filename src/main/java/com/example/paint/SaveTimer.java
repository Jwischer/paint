package com.example.paint;

import java.util.Timer;
import java.util.TimerTask;

public class SaveTimer {
    int timerDuration;
    int maxTimer;
    TimerTask task = new TimerTask() {
        public void run() {
            if(timerDuration == 0){
                System.out.println("Run");
                timerDuration = maxTimer;
            }
            timerDuration--;
        }
    };
    SaveTimer() {
        this.timerDuration = 1000;
        this.maxTimer = 1000; //Time to wait between force saves in milliseconds
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, timerDuration, 1);
    }
}
