package com.example.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class FileMenuFunctions {
    FileChooser fileChooser;

    FileMenuFunctions(Stage stage, MenuItem openfile, MenuItem openFileST, MenuItem save, MenuItem saveas, MenuItem exit, TabPane tabPane, TabArrays tabArrays) {
        this.fileChooser = new FileChooser();
        //Create a string array to store the path to the opened image
        final int nextTab[] = {0};
        final int maxTabs[] = {tabArrays.maxTabs};

        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                //Make close button only appear on selected tab
                if(oldValue.intValue() >= 0) {
                    tabArrays.tab[oldValue.intValue()].setGraphic(null);
                    tabArrays.tab[newValue.intValue()].setGraphic(tabArrays.close[newValue.intValue()]);
                }
            }
        });

        //Open File Menu Function
        openfile.setOnAction(e -> {
            if (nextTab[0] < maxTabs[0]) { //if have not reached max tabs
                //Set up file chooser to default to all images and only open images
                fileChooser.getExtensionFilters().setAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("PNG", "*.png"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("BMP",".bmp")
                );
                fileChooser.setTitle("Open Image File");
                //Create new file at path given by fileChooser
                File file = fileChooser.showOpenDialog(null);
                //Set path variable equal to the opened file
                if (file != null) {
                    //Initialize tab array items for new tab
                    tabArrays.close[nextTab[0]] = new Button("X");
                    tabArrays.path[nextTab[0]] = file.getPath();
                    tabArrays.tab[nextTab[0]] = new Tab(file.getName());
                    tabArrays.stackCanvas[nextTab[0]] = new StackCanvas();
                    tabArrays.saveWarning[nextTab[0]] = false;
                    //If this is the first tab show the close button
                    if(nextTab[0] == 0) {
                        tabArrays.tab[nextTab[0]].setGraphic(tabArrays.close[nextTab[0]]);
                    }
                    //Set up close events
                    tabArrays.close[nextTab[0]].setOnAction(actionEvent -> {
                        //Open new save warning window if needed
                        if(tabArrays.saveWarning[tabPane.getSelectionModel().getSelectedIndex()]) {
                            TabClosePopup tabClosePopup = new TabClosePopup(nextTab[0], tabPane, tabArrays);
                            tabClosePopup.yes.setOnAction(actionEvent1 -> {
                                nextTab[0] = tabClosePopup.YesFunction(tabClosePopup.stage, tabPane, tabArrays, nextTab[0], tabPane.getSelectionModel().getSelectedIndex());
                            });
                            tabClosePopup.yesAndSave.setOnAction(actionEvent1 -> {
                                nextTab[0] = tabClosePopup.YesAndSaveFunction(tabClosePopup.stage, tabPane, tabArrays, nextTab[0], tabPane.getSelectionModel().getSelectedIndex());
                            });
                            tabClosePopup.no.setOnAction(actionEvent1 -> {
                                tabClosePopup.NoFunction(tabClosePopup.stage);
                            });
                        } else {
                            nextTab[0] = OnTabClose(tabPane, tabArrays.maxTabs, tabArrays, nextTab[0], tabPane.getSelectionModel().getSelectedIndex());
                            tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
                        }
                    });
                    GraphicsContext gc = tabArrays.stackCanvas[nextTab[0]].canvas.getGraphicsContext2D();
                    tabArrays.tab[nextTab[0]].setContent(tabArrays.stackCanvas[nextTab[0]].scrollPane);
                    tabPane.getTabs().add(tabArrays.tab[nextTab[0]]);
                    //Create new Image of the selected image
                    Image image = new Image(file.toURI().toString());
                    //Set canvas height and width equal to the image
                    tabArrays.stackCanvas[nextTab[0]].canvas.setHeight(image.getHeight());
                    tabArrays.stackCanvas[nextTab[0]].canvas.setWidth(image.getWidth());
                    System.out.println(tabPane.getSelectionModel().getSelectedIndex());
                    //draw the image on the canvas
                    gc.drawImage(image, 0, 0);
                    nextTab[0]++;
                }
            } else {
                System.out.println("Max tabs reached");
            }
        });

        openFileST.setOnAction(e -> {
            //Open the file in the active tab
            if (tabPane.getSelectionModel().getSelectedIndex() >= 0) {
                fileChooser.setTitle("Open Image File");
                GraphicsContext gc = tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.getGraphicsContext2D();
                File file = fileChooser.showOpenDialog(null);
                tabArrays.path[tabPane.getSelectionModel().getSelectedIndex()] = file.getPath();
                if (file != null) {
                    Image image = new Image(file.toURI().toString());
                    tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.setHeight(image.getHeight());
                    tabArrays.stackCanvas[tabPane.getSelectionModel().getSelectedIndex()].canvas.setWidth(image.getWidth());
                    gc.drawImage(image, 0, 0);
                }
            }
        });

        //Save Menu Function
        save.setOnAction(e -> {
            int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
            //Convert current state of canvas to a writable image
            WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[selectedTab].canvas);
            //Save the writable image to the same location as the file that was opened
            saveToFile(writableImage, tabArrays.path[tabPane.getSelectionModel().getSelectedIndex()]);
            tabArrays.saveWarning[selectedTab] = false;
        });

        //Save As Menu Function
        saveas.setOnAction((ActionEvent event) -> {
            int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
            //Save as either jpg or png with png as default
            fileChooser.getExtensionFilters().setAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("BMP", ".bmp"),
                    new FileChooser.ExtensionFilter("Other Format", "*.*")
            );
            //Create file at the path given by fileChooser
            File file = fileChooser.showSaveDialog(null);
            //Get the save location as a string
            String saveLocation = file.toURI().toString();
            String[] saveloc = saveLocation.split(":", 2);
            //Convert current state of canvas to writable image
            WritableImage writableImage = canvasToWritableImage(tabArrays.stackCanvas[selectedTab].canvas);
            //save that writable image at location saveLoc[1]
            saveToFile(writableImage, saveloc[1]);
            tabArrays.saveWarning[selectedTab] = false;
        });

        //Exit Menu Function
        exit.setOnAction(e -> {
            CloseAppPopup closeAppPopup = new CloseAppPopup(stage, tabPane, tabArrays);
        });


    }

    public static void saveToFile(WritableImage image, String location) {
        File outputFile = new File(location);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", outputFile);
        } catch (IOException ex) {
        }
    }

    public static WritableImage canvasToWritableImage(Canvas canvas) {
        //Make a new Writable Image of the canvas width and height
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        //Set the writable image equal to a snapshot of the current canvas
        writableImage = canvas.snapshot(null, writableImage);
        //return the writable image
        return writableImage;
    }

    public static int OnTabClose(TabPane tabPane, int maxTabs, TabArrays tabArrays, int nextTab, int closeIndex) {
        //If closing a border tab set the close graphic to the new selected tab
            if (closeIndex == 0 && tabArrays.tab[closeIndex+1] != null) {
                tabArrays.tab[closeIndex + 1].setGraphic(tabArrays.close[closeIndex + 1]);
            } else if (closeIndex == nextTab) {
                tabArrays.tab[nextTab - 2].setGraphic(tabArrays.close[nextTab - 2]);
            }
        //Shift all tab array items to the left
        for (int i = closeIndex; i < maxTabs-1; i++) {
            tabArrays.stackCanvas[i] = tabArrays.stackCanvas[i + 1];
            tabArrays.tab[i] = tabArrays.tab[i + 1];
            tabArrays.path[i] = tabArrays.path[i + 1];
            tabArrays.close[i] = tabArrays.close[i + 1];
        }
        nextTab--;
        return nextTab;
    }
}
