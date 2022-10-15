package com.example.paint;

import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Logger {
    File log;
    int logRate; //Rate of logging in ms
    int x;
    DateTimeFormatter dtf;
    LocalDateTime currTime;

    FileWriter logger;

    Logger() throws IOException {
        this.log = new File("src/main/resources/log.txt");
        this.logger = new FileWriter(log);
        this.logRate = 1000;
        this.x = 0;
        dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        log.createNewFile();
        logger.write("---Start of Log---\n");

        Timer timer = new Timer();
        //Set timer to run once every second
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                Platform.runLater(()-> {
                    try {
                        //What to log
                        currTime = LocalDateTime.now(); //Update time
                        logger.write(dtf.format(currTime) + " " + x + "\n"); //Print time to log
                        x++;
                    } catch (IOException e) {

                    }
                });
            }
        }, logRate,logRate); //Call TimerTask every lograte milliseconds
    }
}