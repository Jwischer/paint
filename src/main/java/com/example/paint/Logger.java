package com.example.paint;

import javafx.application.Platform;
import javafx.scene.control.ToggleButton;

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
    int logNum; //Number of the current log: goes up by one every log cycle
    DateTimeFormatter dtf; //Format to put the date and time in the log
    LocalDateTime currTime; //Current local time
    FileWriter logger; //File Writer for the log

    Logger(ToggleButton pencil,ToggleButton line,ToggleButton dashedLine,ToggleButton rectangle,ToggleButton square,ToggleButton ellipse,ToggleButton circle,ToggleButton eraser,ToggleButton triangle,ToggleButton polygon) throws IOException {
        this.log = new File("src/main/resources/log.txt"); //Assign a file path to log to
        this.logger = new FileWriter(log); //Assign the logger to it
        this.logRate = 1000; //Set the log rate to one a second
        this.logNum = 0; //Initialize test variable
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
                        logger.write(dtf.format(currTime) + " " + logNum + ": ");
                        if(pencil.isSelected()){
                            logger.write("Pencil tool selected\n");
                        } else if(line.isSelected()){
                            logger.write("Line tool selected\n");
                        } else if(dashedLine.isSelected()){
                            logger.write("Dashed line tool selected\n");
                        } else if(rectangle.isSelected()){
                            logger.write("Rectangle tool selected\n");
                        } else if(square.isSelected()){
                            logger.write("Square tool selected\n");
                        } else if(ellipse.isSelected()){
                            logger.write("Ellipse tool selected\n");
                        } else if(circle.isSelected()){
                            logger.write("Circle tool selected\n");
                        } else if(eraser.isSelected()){
                            logger.write("Eraser tool selected\n");
                        } else if(triangle.isSelected()){
                            logger.write("Triangle tool selected\n");
                        } else if(polygon.isSelected()){
                            logger.write("Polygon tool selected\n");
                        } else{
                            logger.write("No tool selected\n");
                        } logNum++;
                    } catch (IOException e) {}
                });
            }
        }, logRate,logRate); //Call TimerTask every lograte milliseconds
    }
}