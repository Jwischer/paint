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
    File log; //Log file
    int logRate; //Rate of logging in ms
    int x; //Test integer
    DateTimeFormatter dtf; //Format to put the date and time in the log
    LocalDateTime currTime; //Current local time
    FileWriter logger; //File Writer for the log

    Logger() throws IOException {
        this.log = new File("src/main/resources/log.txt"); //Assign a file path to log to
        this.logger = new FileWriter(log); //Assign the logger to it
        this.logRate = 1000; //Set the log rate to one a second
        this.x = 0; //Initialize test variable
        dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"); //Set date and time format
        log.createNewFile(); //Create the log file
        logger.write("---Start of Log---\n"); //Write to log

        //Write to the log every so often
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